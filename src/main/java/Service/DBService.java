package Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Сервис, обслуживающий подключение к БД.
 */
public class DBService implements AutoCloseable{

    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASS;
    private final String DATA_BASE_SCHEMA;
    private Connection connection;

    public DBService() {
        this.DB_URL = "jdbc:h2:./goods";
        this.DB_USER = "user";
        this.DB_PASS = "1234";
        this.DATA_BASE_SCHEMA = "schema.sql";
        connectToDB();
        initializeDB();
    }

    public DBService(String db_url, String db_user, String db_pass, String schems) {
        this.DB_URL = db_url;
        this.DB_USER = db_user;
        this.DB_PASS = db_pass;
        this.DATA_BASE_SCHEMA = schems;
        connectToDB();
        initializeDB();
    }

    /**
     * Вызов метода соединения с БД.
     * @return Соединение с БД.
     */
    public Connection getConnection() {
        if(connection != null) {
            return connection;
        }
        return connectToDB();
    }

    /**
     * Соединение с БД.
     * @return Соединение.
     */
    private Connection connectToDB(){
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
        return connection;
    }

    /**
     * Закрыть соединение с БД.
     */
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

    /**
     * Инициализация схемы БД.
     */
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

    /**
     * Чтение схемы БД.
     * @return Запросы для создания БД.
     */
    private Set<String> readSqlStatements() {
        StringBuilder statementsBuilder = new StringBuilder();
        Set<String> sqlStatements = new LinkedHashSet<>();

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
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения схемы БД", e);
        }

        if (statementsBuilder.length() > 0) {
            String sqlStatement = buildSqlStatement(statementsBuilder);
            sqlStatements.add(sqlStatement);
        }

        System.out.println("reading file success");

        return sqlStatements;
    }

    /**
     * Создание строки запроса к БД.
     * @param statementsBuilder Объект, который содержит строку запроса.
     * @return Строка запроса в формате String.
     */
    private String buildSqlStatement(StringBuilder statementsBuilder) {
        String statement = statementsBuilder.toString().trim();

        if (statement.endsWith(";")) {
            statement = statement.substring(0, statement.length() - 1);
        }

        return statement;
    }

    /**
     * Выполнение запроса на создание схемы в БД.
     * @param sqlStatements Множество, содержащее запросы к БД.
     * @throws SQLException Если по каким либо причинам схема не создалась, то программа не пригодна для работы.
     */
    private void executeSqlStatements(Set<String> sqlStatements) throws SQLException {

        try (Statement statement = connection.createStatement()) {
            try {
                for (String sqlStatement : sqlStatements) {
                    statement.execute(sqlStatement);
                }
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
        }
        connection.commit();
    }

   ///**
   // * Очистка данных всех таблиц.
   // */
   //public void dropAllData() {
   //    try {
   //        List<String> list = Arrays.asList("Sales", "Storage", "Product");
   //        Statement statement = connection.createStatement();
   //        statement.executeUpdate("DELETE FROM Storage WHERE id > 0");
   //        statement.executeUpdate("DELETE FROM Sales WHERE id > 0");
   //        statement.executeUpdate("DELETE FROM Product WHERE id > 0");
   //        connection.commit();
   //    } catch (SQLException e) {
   //    }

   //}

}
