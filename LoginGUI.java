import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// GUI Login page
public class LoginGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginGUI() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");

        ActionListener loginAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        };

        ActionListener registerAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterDialog();
            }
        };

        loginButton.addActionListener(loginAction);
        registerButton.addActionListener(registerAction);
        usernameField.addActionListener(loginAction);
        passwordField.addActionListener(loginAction);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Validate user credentials
        UserDatabase database = UserDatabase.getInstance();
        if (database.validateUser(username, password)) {
            User user = database.getUserByUsername(username);
            if (user != null) {
                GUIFactory factory;
                if ("Admin".equals(user.getRole())) {
                    factory = new AdminGUIFactory();
                } else {
                    factory = new CustomerGUIFactory();
                }

                // Create GUI instance using the factory
                JFrame gui = factory.createGUI(user);
                gui.setVisible(true);

                // Close the LoginGUI window
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "User not found!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Username or Password!");
        }
    }

    private void openBookStoreGUI(User user) {
            if (user.getRole().equals("Admin")) {
                new AdminGUI(user);
            } else if (user.getRole().equals("Customer")) {
                new CustomerGUI(user);
            }
    }

    private void showRegisterDialog() {
        // Create a dialog to get the registration details
        JFrame registerFrame = new JFrame("Register");
        registerFrame.setSize(300, 200);
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerFrame.setLocationRelativeTo(null);
    
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
    
        JLabel usernameLabel = new JLabel("Username:");
        JTextField regUsernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField regPasswordField = new JPasswordField();
        JLabel roleLabel = new JLabel("Role:");
        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"Admin", "Customer"});
        JButton registerBtn = new JButton("Register");
    
        ActionListener registerAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String regUsername = regUsernameField.getText();
                String regPassword = new String(regPasswordField.getPassword());
                String selectedRole = (String) roleComboBox.getSelectedItem();
    
                // Register new user
                UserDatabase database = UserDatabase.getInstance();
                if (database.registerUser(regUsername, regPassword, selectedRole)) {
                    JOptionPane.showMessageDialog(null, "Registration Successful!");
                    registerFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Error registering user!");
                }
            }
        };
    
        registerBtn.addActionListener(registerAction);
    
        panel.add(usernameLabel);
        panel.add(regUsernameField);
        panel.add(passwordLabel);
        panel.add(regPasswordField);
        panel.add(roleLabel);
        panel.add(roleComboBox);
        panel.add(new JLabel()); // Empty label for alignment
        panel.add(registerBtn);
    
        registerFrame.add(panel);
        registerFrame.setVisible(true);
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginGUI();
            }
        });
    }
}
