import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

//create chat
public class Panel3 extends BasePanel {
    private JPanel panel;
    private JLabel l1;
    private JLabel l2;
    private JLabel l3;
    private JTextField tf1;
    private JPasswordField pf1;
    private JButton b2;
    private JButton b1;
    DbFunctions dbFunctions = new DbFunctions();

    Panel3(JFrame frame) {
        super(frame);

        add(panel);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] passwordArray = pf1.getPassword();
                String password = new String(passwordArray);
                System.out.println(password);
                try {
                    dbFunctions.createRoom(tf1.getText(),password);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


                frame.setSize(1100,450);
                try {
                    switchToPanel(new Panel7(frame));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });



        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(1100,450);
                try {
                    switchToPanel(new Panel7(frame));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }

}
