import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;

//kayıt ol
public class Panel2 extends BasePanel {
    private JPanel panel;
    private JLabel l1;
    private JLabel l2;
    private JLabel l3;
    private JTextField tf1; //username
    private JButton b2;
    private JButton b1;
    private JPasswordField pw1;
    private JTextField tf2;

    Panel2(JFrame frame) {
        super(frame);

        add(panel);

        DbFunctions dbFunctions = new DbFunctions();
        //Kullancıyı yarat
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    char[] passwordArray = pw1.getPassword();
                    String password = new String(passwordArray);

                    dbFunctions.createUser(tf1.getText(),password , tf2.getText());
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

        //Panel1 e geri dön
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(330,440);
                switchToPanel(new Panel1(frame));
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
