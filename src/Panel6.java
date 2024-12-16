import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Panel6 extends BasePanel {
    private JButton b1;
    private JPasswordField pf1;
    private JTextField tf1;
    private JButton b3;
    private JButton b4;
    private JButton b2;
    private JPanel panel;
    private JLabel l3;
    private JLabel l4;
    private JLabel l5;
    private JLabel l2;
    private JLabel l1;

    Panel6(JFrame frame) {
        super(frame);

        add(panel);

        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(1100,660);
                frame.setContentPane(new Panel7(frame));
            }
        });

        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame,"You have successfully logged out!");
                frame.setSize(330,440);
                switchToPanel(new Panel1(frame));
            }
        });

        l4.setVisible(false);
        l5.setVisible(false);
        tf1.setVisible(false);
        pf1.setVisible(false);
        b2.setVisible(false);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l4.setVisible(true);
                l5.setVisible(true);
                tf1.setVisible(true);
                pf1.setVisible(true);
                b2.setVisible(true);
            }
        });

    }

    public JPanel getPanel() {
        return panel;
    }
}
