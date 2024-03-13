import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminGUI extends BaseGUI {
    private DefaultListModel<String> userListModel;
    private JList<String> userList;

    public AdminGUI(User user) {
        super(user, "Admin Dashboard 1");
    }

    @Override
    protected void createRoleSpecificGUI() {
        // Create user list
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        JScrollPane scrollPane = new JScrollPane(userList);

        // Load user data into the list
        loadUserList();

        // Create delete button
        JButton deleteButton = new JButton("Delete User");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        // Add components to the panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(deleteButton, BorderLayout.SOUTH);

        // Add panel to the frame
        add(panel);
    }

    @Override
    protected String getRoleDescription() {
        return "You are logged in as an Admin.";
    }

    private void loadUserList() {
        // Clear existing user list
        userListModel.clear();

        // Populate user list from UserDatabase
        UserDatabase database = UserDatabase.getInstance();
        for (String[] userData : database.getAllUsersData()) {
            String username = userData[0];
            userListModel.addElement(username);
        }
    }

    private void deleteUser() {
        // Get the selected username from the JList
        String selectedUsername = userList.getSelectedValue();
        if (selectedUsername != null) {
            // Confirm deletion
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete user '" + selectedUsername + "'?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                // Delete user from database
                UserDatabase database = UserDatabase.getInstance();
                boolean deleted = database.deleteUser(selectedUsername);
                if (deleted) {
                    JOptionPane.showMessageDialog(this, "User '" + selectedUsername + "' deleted successfully.");
                    loadUserList(); // Refresh the user list
                } else {
                    JOptionPane.showMessageDialog(this, "Error deleting user.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
        }
    }
}
