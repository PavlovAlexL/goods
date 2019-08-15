import org.h2.jdbcx.JdbcDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService implements AutoCloseable{

    private static final String DB_URL = "jdbc:mysql://<host>:<port>/<database>";
    private static final String DB_USER = "user";
    private static final String DB_PASS = "password";
    private static final String DATA_BASE_SCHEMA = "schema.sql";

    private Connection connection;

    public Connection connectToDB() throws SQLException{
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        return connection;
    }

    @Override
    public void close() throws Exception {
        if (connection != null){
            try{
                connection.close();
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }

    }
}
