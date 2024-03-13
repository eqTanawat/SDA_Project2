import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// AdminGUI class extending BaseGUI
public class AdminGUI extends BaseGUI {
    public AdminGUI(User user) {
        super(user, "Admin Dashboard");
    }

    @Override
    protected void createRoleSpecificGUI() {
        // Specific behavior for Admin GUI
        // You can add components or customize the layout here

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));

        JButton appendNewBookButton = new JButton("Append New Book");
        JButton deleteBookButton = new JButton("Delete Book");
        JButton addBookQuantityButton = new JButton("Add Book Quantity");
        JButton subtractBookQuantityButton = new JButton("Subtract Book Quantity");

        appendNewBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle action for appending a new book
                // Example: Open a dialog for adding a new book
                JOptionPane.showMessageDialog(null, "Append New Book button clicked");
            }
        });

        deleteBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle action for deleting a book
                // Example: Open a dialog for deleting a book
                JOptionPane.showMessageDialog(null, "Delete Book button clicked");
            }
        });

        addBookQuantityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle action for adding book quantity
                // Example: Open a dialog for adding book quantity
                JOptionPane.showMessageDialog(null, "Add Book Quantity button clicked");
            }
        });

        subtractBookQuantityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle action for subtracting book quantity
                // Example: Open a dialog for subtracting book quantity
                JOptionPane.showMessageDialog(null, "Subtract Book Quantity button clicked");
            }
        });

        buttonPanel.add(appendNewBookButton);
        buttonPanel.add(deleteBookButton);
        buttonPanel.add(addBookQuantityButton);
        buttonPanel.add(subtractBookQuantityButton);

        getContentPane().add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    protected String getRoleDescription() {
        // Return the role description as a string
        return "You are logged in as an Admin.";
    }

    // Display the role description as a popup when AdminGUI is created
    @Override
    protected void createGUI() {
        // Role-specific behavior provided by subclasses
        JOptionPane.showMessageDialog(null, getRoleDescription());
        createRoleSpecificGUI();

    }
}
