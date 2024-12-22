import java.sql.Timestamp;

public abstract class MessageTypes {
    protected int id;
    protected int user_id;
    protected String message;
    protected Timestamp timestamp;

    // Mesajlar için getter setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
