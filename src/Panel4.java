import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Panel4 extends BasePanel{
    private JPanel panel;
    private JLabel l1;
    private JLabel l2;
    private JButton b1;
    private JButton b2;
    private JTextField textField1;

    Panel4(JFrame frame) {
        super(frame);

        add(panel);

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(1100,660);
                switchToPanel(new Panel7(frame));
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
