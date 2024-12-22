import java.sql.*;
import java.util.List;

public interface DbFunctionsInterface {
    void createUser(String email, String plainPassword,String username) throws SQLException;
    boolean loginUser(String email, String passwordToCheck) throws SQLException;
    void sendMessage(int chatRoomId,String message) throws SQLException;
    void createRoom(String roomName, String password) throws SQLException;
    void joinRoom(String roomName, String password) throws SQLException;
    void changePassword(String oldPassword, String newPassword) throws SQLException;
    String hashPassword(String plainPassword);
    boolean checkPassword(String plainPassword, String hashedPassword);
    List<Message> getMessages(int chatRoomId) throws SQLException;
    List<Room> getChats() throws SQLException;
    String getUsername(int userId) throws SQLException;
}
