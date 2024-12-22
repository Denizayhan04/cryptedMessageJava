import javax.swing.*;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        //1. panele göster
        JFrame frame = new JFrame("Messaging Application");
        frame.setSize(330, 440);
        frame.setContentPane(new Panel1(frame));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
