import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionManager {
    private static SessionManager instance;
    private Integer userId;
    private String email;
    private String username;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    //login yapılınca sessionmanagerin doldurulması
    public void login(Integer userId, String email) {
        this.userId = userId;
        this.email = email;
        try (Connection connection = DatabaseConnector.getConnection()) {
            this.username = fetchUsernameFromDatabase(userId, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //logout methodu
    public void logout() {
        this.userId = null;
        this.email = null;
        this.username = null;
    }
    //giriş yapıldı mı
    public boolean isLoggedIn() {
        return this.userId != null;
    }
    //userid getir
    public Integer getUserId() {
        return this.userId;
    }
    //username getir
    public String getUsername() {
        return this.username;
    }

    // veritabanından username getir
    private String fetchUsernameFromDatabase(Integer userId, Connection connection) {
        String sql = "SELECT username FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
