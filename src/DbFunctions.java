import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbFunctions extends DatabaseConnector implements DbFunctionsInterface {
    //aktif kullanıcı sessionManager değişkenine atanır
    private final SessionManager sessionManager = SessionManager.getInstance();

    private Room activeRoom; // Aktif oda bilgisi

    //aktif odayı ayarla
    private void setActiveRoom(Room room) {
        System.out.println("activeroom");
        this.activeRoom = room;
    }


    //kullanıcı oluştur. email username ve parola al. parolayı hashle . users'e ekle. giriş yap
    @Override
    public void createUser(String email, String plainPassword, String username) throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String hashedPassword = hashPassword(plainPassword);
            System.out.println();
            String query = "INSERT INTO users (email, password, username) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, hashedPassword);
                statement.setString(3, username);
                statement.executeUpdate();
                System.out.println("Kullanıcı başarıyla oluşturuldu!");
                loginUser(email, plainPassword);  // Giriş yaptırtma
            }
        }
    }

    //email al , parolayı al. şifreyi hashle. veritabanındaki hashli şifreyle karşılaştır. aynıysa true değilse false dön
    @Override
    public boolean loginUser(String email, String passwordToCheck) throws SQLException {
        try (Connection connection = DatabaseConnector.getConnection()) {
            String query = "SELECT id, password FROM users WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String hashedPassword = resultSet.getString("password");
                        int userId = resultSet.getInt("id");

                        // Şifreyi kontrol et
                        if (checkPassword(passwordToCheck, hashedPassword)) {
                            sessionManager.login(userId, email);
                            System.out.println("Giriş başarılı! Kullanıcı ID: " + userId);
                            return true; // Başarılı giriş
                        } else {
                            System.out.println("Şifre yanlış.");
                        }
                    } else {
                        System.out.println("Kullanıcı bulunamadı.");
                    }
                }
            }
        }
        return false; // Giriş başarısız
    }

    // Mesajı ve charroomid al . giriş yapılmadıysa hata ver .messages tablosuna mesajı ekle
    @Override
    public void sendMessage(int chatRoomId, String message) throws SQLException {
        if (!sessionManager.isLoggedIn()) {
            System.out.println("Lütfen önce giriş yapın.");
            return;
        }

        String query = "INSERT INTO messages (chat_room, user_id, message, timestamp) VALUES (?, ?, ?, NOW())";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, chatRoomId);
            statement.setInt(2, sessionManager.getUserId());
            statement.setString(3, message);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Mesaj başarıyla gönderildi!");
            } else {
                System.out.println("Mesaj gönderilemedi.");
            }
        }
    }

    //Oda adı ve parolasını al.giriş yapılmadıysa hata ver. odayı oluştur
    @Override
    public void createRoom(String roomName, String password) throws SQLException {

        if (!sessionManager.isLoggedIn()) {
            System.out.println("Lütfen önce giriş yapın.");
            return;
        }

        String query = "INSERT INTO chat_rooms (name, createdBy,password) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, roomName);
            statement.setInt(2, sessionManager.getUserId());
            statement.setString(3, hashPassword(password));

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Oda Oluşturuldu!");
                joinRoom(roomName, password);
            } else {
                System.out.println("Oda Oluşturulamadı.");
            }
        }


    }
    //giriş yapılmadıysa hata ver.Oda adını ve şifresini al. başarılıysa kullanıcıyı  chat_users tablosuna ekle
    @Override
    public void joinRoom(String roomName, String password) throws SQLException {
        if (!sessionManager.isLoggedIn()) {
            System.out.println("Lütfen önce giriş yapın.");
            return;
        }

        // Odanın id'sini almak için chat_rooms tablosunu sorgula
        String query = "SELECT id, name, createdBy, password FROM chat_rooms WHERE name = ?";
        Room room = null;

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, roomName);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int createdBy = resultSet.getInt("createdBy");
                    String storedPassword = resultSet.getString("password");

                    // Şifre kontrolü
                    if (checkPassword(password, storedPassword)) {
                        room = new Room(id, name, createdBy, storedPassword);
                    } else {
                        System.out.println("Şifre yanlış.");
                        return;
                    }
                } else {
                    System.out.println("Geçerli oda bulunamadı.");
                    return;
                }
            }
        }

        // Eğer geçerli bir oda bulunmuşsa, oda kullanıcıya eklenebilir
        if (room != null) {
            String insertQuery = "INSERT INTO chat_users (chat_id, user_id) VALUES (?, ?)";

            try (Connection connection = DatabaseConnector.getConnection();
                 PreparedStatement statement = connection.prepareStatement(insertQuery)) {

                statement.setInt(1, room.id);  // Geçerli oda id'si
                statement.setInt(2, sessionManager.getUserId());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Odaya katıldınız!");
                    setActiveRoom(room); // Aktif oda bilgisi güncelleniyor
                    System.out.println("Aktif Oda: " + room.name);
                } else {
                    System.out.println("Odaya katılamadınız.");
                }
            }
        }
    }

    //giriş yapılmadıysa hata ver. eski şifre ve veritabanındaki eski şifreyi karşılaştır. aynıysa yeni şifreyi hashleyip veritabanına at
    @Override
    public void changePassword(String oldPassword, String newPassword) throws SQLException {
        if (!sessionManager.isLoggedIn()) {
            throw new SQLException("Lütfen önce giriş yapın.");
        }

        // Kullanıcıyı al
        String query = "SELECT password FROM users WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, sessionManager.getUserId());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");

                    // Eski şifreyi kontrol et
                    if (checkPassword(oldPassword, storedPassword)) {
                        // Yeni şifreyi hashle
                        String hashedNewPassword = hashPassword(newPassword);
                        String updateQuery = "UPDATE users SET password = ? WHERE id = ?";

                        try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                            updateStmt.setString(1, hashedNewPassword);
                            updateStmt.setInt(2, sessionManager.getUserId());

                            int rowsAffected = updateStmt.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Şifreniz başarıyla değiştirildi!");
                            } else {
                                throw new SQLException("Şifre değiştirilemedi.");
                            }
                        }
                    } else {
                        throw new SQLException("Eski şifre yanlış.");
                    }
                } else {
                    throw new SQLException("Kullanıcı bulunamadı.");
                }
            }
        }
    }
    //userid ye göre usernameyi getir
    public String getUsername(int userId) throws SQLException {
        String query = "SELECT username FROM users WHERE id = ?";
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("username");
                }
            }
        }
        return "Unknown"; // Kullanıcı bulunamazsa varsayılan isim
    }

    //bcrypt ile şifreyi hashle
    @Override
    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));  // 12 iyi bir güç seviyesi
    }
    //bcrypt ile şifreleri karşılaştır
    @Override
    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
    //chatRoomid ye göre mesajları listeleyip getir
    @Override
    public List<Message> getMessages(int chatRoomId) throws SQLException {
        List<Message> messages = new ArrayList<>();
        String query = "SELECT * FROM messages WHERE chat_room = ? ORDER BY timestamp ASC";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, chatRoomId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int userId = resultSet.getInt("user_id");
                    String messageText = resultSet.getString("message");
                    Timestamp timestamp = resultSet.getTimestamp("timestamp");

                    Message message = new Message(id, chatRoomId, userId, messageText, timestamp);
                    messages.add(message);
                }
            }
        }
        return messages;
    }

    // kullanıcının dahil olduğu sohbetleri getir
    public List<Room> getChats() throws SQLException {
        int userId = sessionManager.getUserId();
        List<Room> rooms = new ArrayList<>();

        // Kullanıcının katıldığı odaları sorgulayan SQL
        String query = "SELECT c.id, c.name, c.createdBy, c.password " +
                "FROM chat_rooms c " +
                "JOIN chat_users cu ON c.id = cu.chat_id " +
                "WHERE cu.user_id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Kullanıcı ID'sini yerleştir
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int createdBy = resultSet.getInt("createdBy");
                    String password = resultSet.getString("password");

                    Room room = new Room(id, name, createdBy, password);
                    rooms.add(room);  // Odayı listeye ekle
                }
            }
        }

        return rooms;
    }

    public int getChatCount() throws SQLException {
        int userId = sessionManager.getUserId();
        int chatCount = 0;

        // Kullanıcının katıldığı odaların sayısını sorgulayan SQL
        String query = "SELECT COUNT(*) AS chat_count " +
                "FROM chat_rooms c " +
                "JOIN chat_users cu ON c.id = cu.chat_id " +
                "WHERE cu.user_id = ?";

        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Kullanıcı ID'sini yerleştir
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    chatCount = resultSet.getInt("chat_count");
                }
            }
        }

        return chatCount;
    }



}
