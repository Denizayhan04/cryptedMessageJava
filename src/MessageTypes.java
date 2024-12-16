import java.sql.Timestamp;

public abstract class MessageTypes {
    protected int id;
    protected int user_id;
    protected String message;
    protected Timestamp timestamp;

    // Abstract methods to enforce implementation in child classes
    public abstract void displayMessage();

    public abstract int getChatRoomId();

    // Common methods for all messages
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
