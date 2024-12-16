import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Panel7 extends BasePanel{
    private JPanel panel;
    private JButton b11;
    private JButton b12;
    private JButton b13;
    private JButton b14;
    private JTextField message;
    private JButton b4;
    private JButton b3;
    private JButton b2;
    private JButton b1;
    private JLabel l35;
    private JLabel l34;
    private JLabel l33;
    private JLabel l32;
    private JLabel l31;
    private JLabel bos;

    Panel7(JFrame frame) {
        super(frame);

        add(panel);

        b11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(330,440);
                switchToPanel(new Panel3(frame));
            }
        });

        b12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(330,440);
                switchToPanel(new Panel4(frame));
            }
        });

        b13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(330,440);
                switchToPanel(new Panel5(frame));
            }
        });

        b14.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(330,440);
                switchToPanel(new Panel6(frame));
            }
        });
    }
}
