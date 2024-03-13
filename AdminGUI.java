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
    buttonPanel.setLayout(new GridLayout(6, 1));

    JButton appendNewBookButton = new JButton("Append New Book");
    JButton deleteBookButton = new JButton("Delete Book");
    JButton addBookQuantityButton = new JButton("Add Book Quantity");
    JButton subtractBookQuantityButton = new JButton("Subtract Book Quantity");
    JButton changePriceButton = new JButton("Change Price");
    JButton viewAllBooksButton = new JButton("View All Books");

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

    changePriceButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle action for changing book price
            // Example: Open a dialog for changing book price
            JOptionPane.showMessageDialog(null, "Change Price button clicked");
        }
    });

    viewAllBooksButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle action for viewing all books
            // Example: Display all books in a new window or dialog
            JOptionPane.showMessageDialog(null, "View All Books button clicked");
        }
    });

    buttonPanel.add(appendNewBookButton);
    buttonPanel.add(deleteBookButton);
    buttonPanel.add(addBookQuantityButton);
    buttonPanel.add(subtractBookQuantityButton);
    buttonPanel.add(changePriceButton);
    buttonPanel.add(viewAllBooksButton);

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
