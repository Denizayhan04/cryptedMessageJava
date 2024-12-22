import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

//giriş yap
public class Panel1 extends BasePanel{
    private JPanel panel;
    private JLabel l1;
    private JLabel l2;
    private JLabel l3;
    private JTextField tf1;
    private JButton b1;
    private JButton b2;
    private JPasswordField pf1;

    Panel1(JFrame frame) {
        super(frame);
        add(panel);

        DbFunctions dbFunctions = new DbFunctions();

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Şifreyi String'e çevirme
                    char[] passwordArray = pf1.getPassword();
                    String password = new String(passwordArray);

                    // Kullanıcıyı giriş yaptırma ve başarılı giriş kontrolü
                    boolean loginSuccess = dbFunctions.loginUser(tf1.getText(), password);

                    if (loginSuccess) {
                        // Başarılı giriş sonrası panel değiştirme
                        frame.setSize(1100, 450);
                        switchToPanel(new Panel7(frame));
                        System.out.println("Giriş başarılı!");
                    } else {
                        // Giriş başarısızsa kullanıcıyı uyarma
                        JOptionPane.showMessageDialog(frame, "Şifre yanlış veya kullanıcı bulunamadı.", "Giriş Hatası", JOptionPane.ERROR_MESSAGE);
                        System.out.println("Giriş başarısız.");
                    }
                } catch (SQLException ex) {
                    // Hata durumunda kullanıcıya uyarı gösterme
                    JOptionPane.showMessageDialog(frame, "Giriş işlemi sırasında bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Hata: " + ex.getMessage());
                }
            }
        });

        //Kayıt olma ekranına geçme
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchToPanel(new Panel2(frame));
                System.out.println("a");
            }
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
