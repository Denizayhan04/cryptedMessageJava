import javax.swing.*;

public class BasePanel extends JPanel {
    protected JFrame frame;

    public BasePanel(JFrame frame) {
        this.frame=frame;
    }
    //GUİ panel değiştirme methodu
    public void switchToPanel(JPanel panel) {
        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }
}
