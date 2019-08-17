package Service;

import org.h2.jdbcx.JdbcDataSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;


public class DBService implements AutoCloseable{

    private static final String DB_URL = "jdbc:h2:./h2db";
    private static final String DB_USER = "user";
    private static final String DB_PASS = "password";
    private static final String DATA_BASE_SCHEMA = "schema.sql";
    private Connection connection;

    public DBService() {
        connectToDB();
        initializeDB();
    }

    public Connection getConnection() {
        return connection;
    }

    private Connection connectToDB(){
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
            printConnectionInfo();
        } else {
            System.out.println("Failed to make connection to database");
        }

        return connection;
    }

    @Override
    public void close() {
        if (connection != null){
            try{
                connection.close();
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

    public void printConnectionInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initializeDB(){
        System.out.println("DataBase schema initialization start.");
        try {
            Set<String> sqlStatements = readSqlStatements();
            executeSqlStatements(sqlStatements);
            System.out.println("Database schema initialized.");
        } catch (Exception e) {
            throw new RuntimeException("Database schema initialization error", e);
        }
    }

    private Set<String> readSqlStatements() throws Exception {
        StringBuilder statementsBuilder = new StringBuilder();
        Set<String> sqlStatements = new HashSet<>();

        System.out.println("reading file start");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(DATA_BASE_SCHEMA),"UTF-8"))) {
            reader.lines().forEach(
                    line -> {
                        if (line.isEmpty() && statementsBuilder.length() > 0) {
                            String sqlStatement = buildSqlStatement(statementsBuilder);
                            sqlStatements.add(sqlStatement);
                            statementsBuilder.setLength(0);
                            return;
                        }

                        statementsBuilder.append(line);
                        statementsBuilder.append(" ");
                    }
            );
        }

        if (statementsBuilder.length() > 0) {
            String sqlStatement = buildSqlStatement(statementsBuilder);
            sqlStatements.add(sqlStatement);
        }

        System.out.println("reading file %s success");

        return sqlStatements;
    }

    private String buildSqlStatement(StringBuilder statementsBuilder) {
        String statement = statementsBuilder.toString().trim();

        if (statement.endsWith(";")) {
            statement = statement.substring(0, statement.length() - 1);
        }

        return statement;
    }

    private void executeSqlStatements(Set<String> sqlStatements) throws Exception {

        try (Connection connection = this.getConnection(); Statement statement = connection.createStatement()) {
            try {
                connection.setAutoCommit(false);
                for (String sqlStatement : sqlStatements) {
                    statement.execute(sqlStatement);
                }
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
        }
    }

    public void cleanUp() {
        try {
            Statement stmt = connection.createStatement();
            stmt.execute("drop schema H2DB ");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
