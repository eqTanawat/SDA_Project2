import javax.swing.*;
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

        createMenu(); // Create the menu bar

        createGUI(); // Call the template method
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

    // Create the menu bar with logout menu item
    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Quit");
    
        JMenuItem logoutMenuItem = new JMenuItem("Logout");
        logoutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        fileMenu.add(logoutMenuItem);
    
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
        fileMenu.add(exitMenuItem);
    
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
    
    private void exit() {
        System.exit(0); // Exit the application
    }
    

    private void logout() {
        dispose(); // Close the current window
        new LoginGUI(); // Open the LoginGUI window
    }
}
