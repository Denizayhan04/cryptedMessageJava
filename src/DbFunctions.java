import java.sql.*;
import java.util.Scanner;

public class DbUser extends DatabaseConnector {

    private Scanner scanner = new Scanner(System.in);

    private void createUser(Connection connection) throws SQLException {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Şifre: ");
        String password = scanner.nextLine();

        String query = "INSERT INTO users (email, password) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.executeUpdate();
            System.out.println("Kullanıcı başarıyla oluşturuldu!");
        }
    }

    private void loginUser(Connection connection) throws SQLException {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Şifre: ");
        String password = scanner.nextLine();

        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    System.out.println("Giriş başarılı! Hoş geldiniz, " + email);
                } else {
                    System.out.println("Email veya şifre yanlış.");
                }
            }
        }
    }
}
