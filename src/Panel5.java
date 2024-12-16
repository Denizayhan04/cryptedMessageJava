import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Panel5 extends BasePanel {
    private JPanel pnl;
    private JButton button1; // Geri dönüş butonu
    private JPanel textpanel; // Mesajları göstermek için panel

    Panel5(JFrame frame) {
        super(frame);

        add(pnl);

        if (pnl == null) {
            System.out.println("Panel5: pnl değişkeni null!");
        }

        // textpanel içine mesajları gösterecek bir JTextArea ekleyin
        JTextArea textArea = new JTextArea();
        textArea.setSize(330,400);
        textArea.setEditable(false); // Kullanıcı düzenleyemesin
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.LIGHT_GRAY); // İsteğe bağlı stil
        JScrollPane scrollPane = new JScrollPane(textArea);
        textpanel.setLayout(new BorderLayout());
        textpanel.add(scrollPane, BorderLayout.CENTER);

        // Dosyadaki mesajları oku ve JTextArea'ya aktar
        List<String> starredMessages = readStarredMessagesFromFile();
        for (String message : starredMessages) {
            textArea.append(message + "\n"); // Her mesajı yeni satıra ekle
        }

        // button1'in geri dönüş işlevi
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(1100, 450);
                try {
                    switchToPanel(new Panel7(frame));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Panel7 yüklenirken hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public JPanel getPanel() {
        return pnl;
    }

    // Yıldızlı mesajları dosyadan okuma
    private List<String> readStarredMessagesFromFile() {
        List<String> messages = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("staredMessages.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                messages.add(line); // Mesajı listeye ekle
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
