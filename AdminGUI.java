// AdminGUI class
import javax.swing.*;
import java.awt.*;

// GUI Admin page
public class AdminGUI extends JFrame {
    private User user;

    public AdminGUI(User user) {
        this.user = user;

        setTitle("Admin Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel greetingLabel = new JLabel("Welcome, " + user.getUsername() + "! You are logged in as an Admin.");
        greetingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(greetingLabel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }
}
