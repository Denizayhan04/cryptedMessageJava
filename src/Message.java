import java.sql.Timestamp;

public class Message extends MessageTypes {
    private int chat_room_id; // mesajın odası

    // Constructor
    public Message(int id, int chat_room_id, int user_id, String message, Timestamp timestamp) {
        this.id = id;
        this.chat_room_id = chat_room_id;
        this.user_id = user_id;
        this.message = message;
        this.timestamp = timestamp;
    }

}

