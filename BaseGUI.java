import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Abstract class representing a base GUI
public abstract class BaseGUI extends JFrame {
    protected User user;

    public BaseGUI(User user, String title) {
        this.user = user;

        setTitle(title);
        setSize(1200, 720);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JButton logoutButton = new JButton("Logout");
        logoutButton.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        topPanel.add(logoutButton, BorderLayout.EAST); // Logout button aligned to the right
        panel.add(topPanel, BorderLayout.NORTH);

        add(panel);
        setVisible(true);
    }

    // Template method defining the common structure
    protected void createGUI() {
        // Role-specific behavior provided by subclasses
        createRoleSpecificGUI();
    }

    // Abstract method to be overridden by subclasses for role-specific behavior
    protected abstract void createRoleSpecificGUI();

    // Abstract method to be overridden by subclasses to provide role-specific description
    protected abstract String getRoleDescription();

    private void logout() {
        dispose(); // Close the current window
        new LoginGUI(); // Open the LoginGUI window
    }
}
