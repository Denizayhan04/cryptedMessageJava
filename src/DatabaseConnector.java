import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    //Veritabanına bağlanma methodu
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DbConfig.getUrl(),
                DbConfig.getUser(),
                DbConfig.getPassword()
        );
    }
}
