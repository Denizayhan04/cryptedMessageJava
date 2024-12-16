import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Panel2 extends BasePanel {
    private JPanel panel;
    private JLabel l1;
    private JLabel l2;
    private JLabel l3;
    private JTextField tf1;
    private JPasswordField pf1;
    private JButton b2;
    private JButton b1;

    Panel2(JFrame frame) {
        super(frame);

        add(panel);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(1100,660);
                switchToPanel(new Panel7(frame));
            }
        });
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