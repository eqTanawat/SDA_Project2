// CustomerGUI class extending BaseGUI

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CustomerGUI extends BaseGUI {
    
    private JTable bookTable;
    private JButton addToCartButton;
    private JButton viewCartButton;
    private JButton removeBookButton;
    private Cart cart;

    public CustomerGUI(User user) {
        super(user, "Customer Dashboard");
    }

    @Override
    protected void createRoleSpecificGUI() {
        // Specific behavior for Customer GUI
        bookTable = new JTable();
        addToCartButton = new JButton("Add to Cart");
        viewCartButton = new JButton("View Cart");
        removeBookButton = new JButton("Remove Book");
        cart = new Cart();

        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setPreferredSize(new Dimension(580, getHeight() - 150));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addToCartButton);
        buttonPanel.add(viewCartButton);
        buttonPanel.add(removeBookButton);

        add(scrollPane, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        setupBookTable();

        // Add action listener to "Add to Cart" button
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });

        // Add action listener to "View Cart" button
        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCart();
            }
        });

        // Add action listener to "Remove Book" button
        removeBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeBookFromCart();
            }
        });
    }

    private void setupBookTable() {
        // Get all books from the database
        BookDatabase bookDatabase = BookDatabase.getInstance();
        List<Book> books = bookDatabase.getBooks();

        // Make the table uneditable
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Set up table model
        model.addColumn("Title");
        model.addColumn("Author");
        model.addColumn("Quantity");
        model.addColumn("Price");

        for (Book book : books) {
            model.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getQuantity(), book.getPrice()});
        }

        bookTable.setModel(model);
    }

    private void addToCart() {
        int selectedRowIndex = bookTable.getSelectedRow();
        if (selectedRowIndex != -1) {
            // Get book title and quantity from the selected row
            String title = (String) bookTable.getValueAt(selectedRowIndex, 0);
            int availableQuantity = (int) bookTable.getValueAt(selectedRowIndex, 2); // Quantity column

            // Ask the user how many books they want to buy
            String quantityInput = JOptionPane.showInputDialog(this, "How many books do you want to buy?", "Quantity", JOptionPane.QUESTION_MESSAGE);
            if (quantityInput != null && !quantityInput.isEmpty()) {
                try {
                    int requestedQuantity = Integer.parseInt(quantityInput);

                    if (requestedQuantity <= 0) {
                        JOptionPane.showMessageDialog(this, "Please enter a valid quantity.", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
                    } else if (requestedQuantity > availableQuantity) {
                        JOptionPane.showMessageDialog(this, "Sorry, there are only " + availableQuantity + " copies available for \"" + title + "\".", "Insufficient Quantity", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Get the book from the database
                        BookDatabase bookDatabase = BookDatabase.getInstance();
                        List<Book> books = bookDatabase.getBooks();
                        Book selectedBook = null;
                        for (Book book : books) {
                            if (book.getTitle().equals(title)) {
                                selectedBook = book;
                                break;
                            }
                        }

                        // Add the item to the cart
                        cart.addItem(selectedBook, requestedQuantity);

                        // Display confirmation message
                        JOptionPane.showMessageDialog(this, "You have added " + requestedQuantity + " copies of \"" + title + "\" to your cart.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a book to add to your cart.", "No Book Selected", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewCart() {

        // Check if the cart is empty
    if (cart.getItems().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Cart is empty.", "Empty Cart", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

        // Create a dialog to display the cart contents
        StringBuilder cartContents = new StringBuilder();
        cartContents.append("Cart Contents:\n");
        for (Map.Entry<Book, Integer> entry : cart.getItems().entrySet()) {
            Book book = entry.getKey();
            int quantity = entry.getValue();
            cartContents.append("- ").append(book.getTitle()).append(": ").append(quantity).append(" copies\n");
        }
        cartContents.append("\nTotal Cost(Thai Baht): ").append(cart.getTotalCost());
    
        // Add purchase button
        Object[] options = {"Purchase", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this, cartContents.toString(), "Cart", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    
        // Handle purchase
        if (choice == JOptionPane.YES_OPTION) {
            performPurchase();
        }
    }
    
    private void performPurchase() {
        // Update books.csv file based on the purchased items in the cart
        BookDatabase bookDatabase = BookDatabase.getInstance();
        Map<Book, Integer> items = cart.getItems();
        for (Map.Entry<Book, Integer> entry : items.entrySet()) {
            Book book = entry.getKey();
            int quantity = entry.getValue();
            bookDatabase.subtractBookQuantity(book.getTitle(), quantity);
        }
    
        // Update the table to reflect the changes
        setupBookTable();
    
        // Clear the cart after purchase
        cart = new Cart();
    
        JOptionPane.showMessageDialog(this, "Purchase successful! The books have been updated.", "Purchase Confirmation", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void removeBookFromCart() {
    // Check if the cart is empty
    if (cart.getItems().isEmpty()) {
        JOptionPane.showMessageDialog(this, "The cart is empty.", "Empty Cart", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    // Get the list of books currently in the cart
    Map<Book, Integer> items = cart.getItems();
    List<String> bookTitles = new ArrayList<>();
    for (Book book : items.keySet()) {
        bookTitles.add(book.getTitle());
    }

    // Check if there are books in the cart to remove
    if (bookTitles.isEmpty()) {
        JOptionPane.showMessageDialog(this, "There are no books in the cart to remove.", "Empty Cart", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    // Ask the user to select the book to remove
    String selectedTitle = (String) JOptionPane.showInputDialog(this, "Select a book to remove from the cart:", "Remove Book", JOptionPane.PLAIN_MESSAGE, null, bookTitles.toArray(new String[0]), bookTitles.get(0));

    // Find the corresponding book object based on the selected title
    Book selectedBook = null;
    for (Book book : items.keySet()) {
        if (book.getTitle().equals(selectedTitle)) {
            selectedBook = book;
            break;
        }
    }

    // Remove the selected book from the cart
    if (selectedBook != null) {
        cart.removeItem(selectedBook);
        JOptionPane.showMessageDialog(this, "The book \"" + selectedBook.getTitle() + "\" has been removed from the cart.", "Book Removed", JOptionPane.INFORMATION_MESSAGE);
    }
}

    

    @Override
    protected String getRoleDescription() {
        return "You are logged in as a Customer.";
    }

    // Display the role description as a popup when AdminGUI is created
    @Override
    protected void createGUI() {
        // Role-specific behavior provided by subclasses
        JOptionPane.showMessageDialog(null, getRoleDescription());
        createRoleSpecificGUI();

    }

}
