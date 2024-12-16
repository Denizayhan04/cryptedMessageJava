import java.sql.Timestamp;

public class Message extends MessageTypes {
    private int chat_room_id; // Specific to this derived class

    // Constructors
    public Message(int id, int chat_room_id, int user_id, String message, Timestamp timestamp) {
        this.id = id;
        this.chat_room_id = chat_room_id;
        this.user_id = user_id;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Message() {
    }

    public Message(int currentUserId, int chatRoomId, String message) {
        this.user_id = currentUserId;
        this.chat_room_id = chatRoomId;
        this.message = message;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }

    // Implement abstract methods
    @Override
    public void displayMessage() {
        System.out.println("[" + timestamp + "] User " + user_id + ": " + message);
    }

    @Override
    public int getChatRoomId() {
        return chat_room_id;
    }

    public void setChatRoomId(int chat_room_id) {
        this.chat_room_id = chat_room_id;
    }
}

