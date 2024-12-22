import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Panel6 extends BasePanel {
    private JButton b1;  // Şifreyi Değiştir Button
    private JPasswordField pf1; // Yeni şifre
    private JPasswordField pf2; // Eski şifre
    private JButton b3; // Sonraki button
    private JButton b4; // Çıkış button
    private JButton b2; // Şifre değişikliğini onayla
    private JPanel panel;
    private JLabel l3;
    private JLabel l4; // Yeni şifre label
    private JLabel l5; // Eski şifre label
    private JLabel l2;
    private JLabel l1;
    private JLabel usernamearea;
    private JLabel chatcount;

    Panel6(JFrame frame) throws SQLException {
        super(frame);

        add(panel);
        DbFunctions db = new DbFunctions();
        String currentUserName = SessionManager.getInstance().getUsername();
        //usernameye kullanının usernamesini yazdır
        usernamearea.setText(currentUserName);
        //chatcounta toplam sohbet sayını yazdır
        chatcount.setText(String.valueOf(db.getChatCount()));
        // Sonraki butonuna tıklandığında Panel7'ye geçiş yapılacak
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(1100, 450);
                try {
                    frame.setContentPane(new Panel7(frame));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Çıkış butonuna tıklandığında çıkış yapılacak ve panel1e geçilecek
        b4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Başarıyla çıkış yapıldı!");
                frame.setSize(330, 440);
                switchToPanel(new Panel1(frame));
            }
        });

        // Başlangıçta şifre değişiklik alanları gizli
        l4.setVisible(false);
        l5.setVisible(false);
        pf2.setVisible(false);
        pf1.setVisible(false);
        b2.setVisible(false);
        // Şifre değiştirme butonuna tıklandığında şifre alanlarını gösterilsin
        b1.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                l4.setVisible(true);
                l5.setVisible(true);
                pf2.setVisible(true);
                pf1.setVisible(true);
                b2.setVisible(true);
            }
        });

        //Şifre değiştirme
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String oldPassword = new String(pf2.getPassword());
                String newPassword = new String(pf1.getPassword());

                // Eğer eski ya da yeni şifre boşsa uyarı göster
                if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Lütfen eski ve yeni şifrenizi girin.");
                    return;
                }

                try {
                    // DbFunctions ile şifre değişikliği işlemini yapıyoruz
                    DbFunctions dbFunctions = new DbFunctions();
                    dbFunctions.changePassword(oldPassword, newPassword);

                    // Başarılı şifre değişikliği sonrası şifre alanlarını gizle
                    l4.setVisible(false);
                    l5.setVisible(false);
                    pf2.setVisible(false);
                    pf1.setVisible(false);
                    b2.setVisible(false);

                    // Başarılı mesajı göster
                    JOptionPane.showMessageDialog(frame, "Şifreniz başarıyla değiştirildi!");
                } catch (SQLException ex) {
                    // Hata durumunda kullanıcıyı bilgilendir
                    JOptionPane.showMessageDialog(frame, "Şifre değişikliği başarısız: " + ex.getMessage());
                }
            }
        });

    }


    public JPanel getPanel() {
        return panel;
    }
}
