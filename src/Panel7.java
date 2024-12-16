import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.SwingUtilities;



public class Panel7 extends BasePanel {
    private JPanel panel;
    private JButton b11;
    private JButton b12;
    private JButton b13;
    private JButton b14;
    private JPanel sohbetpanel;  // Sohbetler için panel
    private JPanel sohbetalani;  // Mesajların gösterileceği alan
    private JTextField textField1;
    private JButton gonder;
    private JLabel l35;
    private Room currentChatRoom;  // Current chat room tutulacak
    private DbFunctions dbFunctions = new DbFunctions();
    private Timer messageTimer;  // Timer nesnesi
    private static final int CHECK_INTERVAL = 2000;  // 2 saniye

    Panel7(JFrame frame) throws SQLException {
        super(frame);
        add(panel);

        // Kullanıcı ID'sini SessionManager üzerinden alıyoruz
        int currentUserId = SessionManager.getInstance().getUserId();  // Kullanıcı ID'si alınır

        // Sohbet odalarını veritabanından al
        List<Room> rooms = getRoomsFromDatabase();

        // Sohbet panelini dinamik olarak oluştur
        addDynamicButtons(rooms);

        // Timer'ı başlat
        startMessageTimer();

        // Diğer butonlar için aksiyonlar
        b11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(330, 440);
                switchToPanel(new Panel3(frame));
            }
        });

        gonder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mesaj kutusundaki metni al
                String mesaj = textField1.getText().trim();

                // Mesaj boşsa işlem yapma
                if (mesaj.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Mesaj boş olamaz.", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Aktif bir sohbet odası seçilip seçilmediğini kontrol et
                if (currentChatRoom == null) {
                    JOptionPane.showMessageDialog(frame, "Lütfen bir sohbet odası seçin.", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Mesajı gönder
                try {
                    dbFunctions.sendMessage(currentChatRoom.id, mesaj);

                    // Mesaj gönderildikten sonra mesaj alanını temizle
                    textField1.setText("");

                    // Sohbet panelini güncelle (yeni mesajı göstermek için)
                    updateChatMessages(currentChatRoom.id);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Mesaj gönderilirken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        b12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(330, 440);
                switchToPanel(new Panel4(frame));
            }
        });

        b13.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(330, 440);
                switchToPanel(new Panel5(frame));
            }
        });

        b14.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setSize(330, 440);
                try {
                    switchToPanel(new Panel6(frame));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    // Veritabanından veya başka bir kaynaktan sohbet odalarını alacak metot
    private List<Room> getRoomsFromDatabase() throws SQLException {
        return dbFunctions.getChats();  // DbFunctions'tan sohbet odalarını al
    }

    // Sohbet odaları için butonları dinamik olarak ekleme
    private void addDynamicButtons(List<Room> rooms) {
        sohbetpanel.removeAll();
        sohbetpanel.setLayout(new BoxLayout(sohbetpanel, BoxLayout.Y_AXIS));  // Dikey yerleşim

        for (Room room : rooms) {
            JButton roomButton = new JButton(room.name);  // Room sınıfındaki name kullanılıyor

            // Buton boyutunu ayarla
            roomButton.setPreferredSize(new Dimension(200, 50)); // Genişlik 200, yükseklik 50
            roomButton.setMaximumSize(new Dimension(200, 50));   // Maksimum boyutu sınırlayın
            roomButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Ortalansın

            // Butona tıklandığında, odanın ID'si ile işlemi yapacak
            roomButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Odayı aktif hale getir ve currentChatRoom'u güncelle
                    setCurrentChatRoom(room);

                    // Sohbeti güncelle (mesajları göster)
                    updateChatMessages(room.id);
                }
            });

            sohbetpanel.add(Box.createVerticalStrut(10)); // Butonlar arasında boşluk
            sohbetpanel.add(roomButton);
        }

        sohbetpanel.revalidate();
        sohbetpanel.repaint();
    }

    // Aktif sohbet odasını belirleyen metot
    private void setCurrentChatRoom(Room room) {
        this.currentChatRoom = room;
        l35.setText("Aktif Sohbet: " + room.name);
    }

    private void updateChatMessages(int chatRoomId) {
        try {
            int currentUserId = SessionManager.getInstance().getUserId();
            List<Message> messages = dbFunctions.getMessages(chatRoomId);

            // Mesajları tarihe göre sıralama
            messages.sort((msg1, msg2) -> msg1.timestamp.compareTo(msg2.timestamp));

            SwingUtilities.invokeLater(() -> {
                sohbetalani.removeAll();
                sohbetalani.setLayout(new BoxLayout(sohbetalani, BoxLayout.Y_AXIS));

                for (Message msg : messages) {
                    JPanel messagePanel = new JPanel();
                    messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.X_AXIS));

                    JLabel messageLabel = new JLabel(msg.message);
                    messageLabel.setOpaque(true);
                    messageLabel.setBackground(msg.user_id == currentUserId ? Color.CYAN : Color.LIGHT_GRAY);
                    messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                    // Çift tıklama özelliği ekleme
                    messageLabel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (e.getClickCount() == 2) { // Çift tıklama kontrolü
                                saveMessageToStarredFile(msg);
                            }
                        }
                    });

                    if (msg.user_id == currentUserId) {
                        // Sağ tarafa sadece mesaj içeriği
                        messagePanel.add(Box.createHorizontalGlue());
                        messagePanel.add(messageLabel);
                    } else {
                        // Sol tarafa kullanıcı adı ve mesaj
                        JLabel usernameLabel = null;
                        try {
                            usernameLabel = new JLabel(dbFunctions.getUsername(msg.user_id) + ": ");
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        usernameLabel.setFont(new Font("Arial", Font.BOLD, 12));
                        messagePanel.add(usernameLabel);
                        messagePanel.add(messageLabel);
                        messagePanel.add(Box.createHorizontalGlue());
                    }

                    sohbetalani.add(messagePanel);
                }

                sohbetalani.revalidate();
                sohbetalani.repaint();
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveMessageToStarredFile(Message msg) {
        String starredMessage = String.format("%s:%s:%d", msg.message, msg.timestamp, msg.user_id);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("staredMessages.txt", true))) {
            writer.write(starredMessage);
            writer.newLine();
            JOptionPane.showMessageDialog(null, "Mesaj yıldızlı mesajlara eklendi!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Mesaj kaydedilirken bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }






    // 2 saniyede bir mesajları kontrol etme
    private void startMessageTimer() {
        messageTimer = new Timer();
        messageTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (currentChatRoom != null) {
                    updateChatMessages(currentChatRoom.id);
                }
            }
        }, 0, CHECK_INTERVAL);
    }
}
