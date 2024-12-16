import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Panel5 extends BasePanel {
    private JPanel panel;
    private JLabel name1;
    private JPanel message1;
    private JLabel name2;
    private JLabel message2;
    private JLabel name3;
    private JLabel message3;
    private JButton b;

    Panel5(JFrame frame) {
        super(frame);

        add(panel);

        b.addActionListener(new ActionListener() {
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
