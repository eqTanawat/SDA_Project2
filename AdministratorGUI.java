import javax.swing.*;
import java.awt.*;

// GUI for Administrator
public class AdministratorGUI extends JFrame {
    private User user;

    public AdministratorGUI(User user) {
        this.user = user;

        setTitle("Administrator Interface");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel greetingLabel = new JLabel("Welcome, Administrator " + user.getUsername() + "!");
        greetingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(greetingLabel, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea("This is the administrator interface.\nYou can manage the bookstore here.");
        textArea.setEditable(false);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        add(panel);
        setVisible(true);
    }
}
