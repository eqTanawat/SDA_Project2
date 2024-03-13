import javax.swing.*;
import java.awt.*;

// GUI for Customer
public class CustomerGUI extends JFrame {
    private User user;

    public CustomerGUI(User user) {
        this.user = user;

        setTitle("Customer Interface");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel greetingLabel = new JLabel("Welcome, " + user.getUsername() + "!");
        greetingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(greetingLabel, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea("This is the customer interface.\nYou can view and purchase books here.");
        textArea.setEditable(false);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }
}
