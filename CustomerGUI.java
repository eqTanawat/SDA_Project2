// CustomerGUI class
import javax.swing.*;
import java.awt.*;

// GUI Customer page
public class CustomerGUI extends JFrame {
    private User user;

    public CustomerGUI(User user) {
        this.user = user;

        setTitle("Customer Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel greetingLabel = new JLabel("Welcome, " + user.getUsername() + "! You are logged in as a Customer.");
        greetingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(greetingLabel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }
}
