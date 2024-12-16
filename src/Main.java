import javax.swing.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        JFrame frame = new JFrame("Messaging Application");
        frame.setSize(330, 440);
        frame.setContentPane(new Panel1(frame));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);









    }
}
