import javax.swing.*;
import java.awt.*;

// GUI Library page
public class BookStoreGUI extends JFrame {
    private User user;

    public BookStoreGUI(User user) {
        this.user = user;

        setTitle("Book Store");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel greetingLabel = new JLabel("Welcome, " + user.getUsername() + "!");
        greetingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(greetingLabel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }
}
