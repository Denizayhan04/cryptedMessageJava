import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Panel4 extends BasePanel{
    private JPanel panel;
    private JLabel l1;
    private JLabel l2;
    private JButton b1;
    private JButton b2;
    private JTextField tf1;
    private JPasswordField pw1;
    DbFunctions dbFunctions = new DbFunctions();

    Panel4(JFrame frame) {
        super(frame);

        add(panel);
        //Sohbet odasına katıl
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] passwordArray = pw1.getPassword();
                String password = new String(passwordArray);
                try {
                    dbFunctions.joinRoom(tf1.getText(),password);
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

        //Geri dön
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
