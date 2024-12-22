public class Room {
    public int id;
    public String name;
    public int createdBy;
    public String password;
    //oda constructoru
    public Room(int id, String name, int createdBy, String password) {
        this.id = id;
        this.name = name;
        this.createdBy = createdBy;
        this.password = password;
    }

}
