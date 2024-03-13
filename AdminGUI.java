import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


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
    buttonPanel.setLayout(new GridLayout(3, 2));

    JButton appendNewBookButton = new JButton("Append New Book");
    JButton deleteBookButton = new JButton("Delete Book");
    JButton addBookQuantityButton = new JButton("Add Book Quantity");
    JButton subtractBookQuantityButton = new JButton("Subtract Book Quantity");
    JButton changePriceButton = new JButton("Change Price");
    JButton viewAllBooksButton = new JButton("View All Books");

    appendNewBookButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Open a dialog to get input for the new book
            String title = JOptionPane.showInputDialog("Enter book title:");
            if (title == null || title.isEmpty()) {
                // If the user cancels or inputs an empty title, return without further action
                return;
            }
    
            // Check if the book already exists in the database
            List<Book> existingBooks = BookDatabase.getInstance().getBooks();
            for (Book existingBook : existingBooks) {
                if (existingBook.getTitle().equals(title)) {
                    // If the book already exists, show a message and exit
                    JOptionPane.showMessageDialog(null, "Error: Book with the same title already exists.");
                    return;
                }
            }
    
            // Continue with obtaining other book details
            String author = JOptionPane.showInputDialog("Enter book author:");
            int quantity = Integer.parseInt(JOptionPane.showInputDialog("Enter quantity:"));
            int price = Integer.parseInt(JOptionPane.showInputDialog("Enter price:"));
    
            // Create a new book object
            Book newBook = new Book(title, author, quantity, price);
    
            // Append the new book to the database
            BookDatabase.getInstance().appendNewBook(newBook);
        
            String bookDetails = "New Book Details:\n"
                + "Title: " + newBook.getTitle() + "\n"
                + "Author: " + newBook.getAuthor() + "\n"
                + "Quantity: " + newBook.getQuantity() + "\n"
                + "Price: " + newBook.getPrice();
            JOptionPane.showMessageDialog(null, bookDetails, "New Book Added", JOptionPane.INFORMATION_MESSAGE);
        }
    });
    
    deleteBookButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Open a dialog to get input for the book title to delete
            String titleToDelete = JOptionPane.showInputDialog("Enter the title of the book to delete:");
            if (titleToDelete == null || titleToDelete.isEmpty()) {
                // If the user cancels or inputs an empty title, return without further action
                return;
            }
    
            // Get the list of books from the database
            List<Book> existingBooks = BookDatabase.getInstance().getBooks();
            boolean bookFound = false;
    
            // Search for the book in the database
            for (Book book : existingBooks) {
                if (book.getTitle().equals(titleToDelete)) {
                    // If the book is found, delete it from the database
                    BookDatabase.getInstance().deleteBook(titleToDelete);
                    bookFound = true;
                    break;
                }
            }
    
            // Display appropriate message based on whether the book was found and deleted
            if (bookFound) {
                JOptionPane.showMessageDialog(null, "Book '" + titleToDelete + "' deleted successfully.", "Book Deleted", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error: Book '" + titleToDelete + "' not found.", "Book Not Found", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
    
    addBookQuantityButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Open dialogs to get input for the book title and quantity to add
            String titleToAddQuantity = JOptionPane.showInputDialog("Enter the title of the book to add quantity to:");
            if (titleToAddQuantity == null || titleToAddQuantity.isEmpty()) {
                // If the user cancels or inputs an empty title, return without further action
                return;
            }
    
            // Get the list of books from the database
            List<Book> existingBooks = BookDatabase.getInstance().getBooks();
            boolean bookFound = false;
    
            // Search for the book in the database
            for (Book book : existingBooks) {
                if (book.getTitle().equals(titleToAddQuantity)) {
                    bookFound = true;
                    // If the book is found, prompt the user for the quantity to add
                    String quantityToAddStr = JOptionPane.showInputDialog("Enter the quantity to add:");
                    if (quantityToAddStr == null || quantityToAddStr.isEmpty()) {
                        // If the user cancels or inputs an empty quantity, return without further action
                        return;
                    }
                    try {
                        int quantityToAdd = Integer.parseInt(quantityToAddStr);
                        // Add the specified quantity to the existing quantity of the book
                        book.setQuantity(book.getQuantity() + quantityToAdd);
                        BookDatabase.getInstance().saveBooksToCSV(); // Save changes to the database
                        JOptionPane.showMessageDialog(null, "Quantity added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException ex) {
                        // If the user inputs a non-integer quantity, display an error message
                        JOptionPane.showMessageDialog(null, "Error: Please enter a valid integer quantity.", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
            }
    
            // If the book is not found, display an error message
            if (!bookFound) {
                JOptionPane.showMessageDialog(null, "Error: Book '" + titleToAddQuantity + "' not found.", "Book Not Found", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
    
    subtractBookQuantityButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Open dialogs to get input for the book title and quantity to subtract
            String titleToSubtractQuantity = JOptionPane.showInputDialog("Enter the title of the book to subtract quantity from:");
            if (titleToSubtractQuantity == null || titleToSubtractQuantity.isEmpty()) {
                // If the user cancels or inputs an empty title, return without further action
                return;
            }
    
            // Get the list of books from the database
            List<Book> existingBooks = BookDatabase.getInstance().getBooks();
            boolean bookFound = false;
    
            // Search for the book in the database
            for (Book book : existingBooks) {
                if (book.getTitle().equals(titleToSubtractQuantity)) {
                    bookFound = true;
                    // If the book is found, prompt the user for the quantity to subtract
                    String quantityToSubtractStr = JOptionPane.showInputDialog("Enter the quantity to subtract:");
                    if (quantityToSubtractStr == null || quantityToSubtractStr.isEmpty()) {
                        // If the user cancels or inputs an empty quantity, return without further action
                        return;
                    }
                    try {
                        int quantityToSubtract = Integer.parseInt(quantityToSubtractStr);
                        // Subtract the specified quantity from the existing quantity of the book
                        int newQuantity = book.getQuantity() - quantityToSubtract;
                        if (newQuantity >= 0) {
                            book.setQuantity(newQuantity);
                            BookDatabase.getInstance().saveBooksToCSV(); // Save changes to the database
                            JOptionPane.showMessageDialog(null, "Quantity subtracted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error: Quantity to subtract exceeds available quantity.", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        // If the user inputs a non-integer quantity, display an error message
                        JOptionPane.showMessageDialog(null, "Error: Please enter a valid integer quantity.", "Invalid Quantity", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
            }
    
            // If the book is not found, display an error message
            if (!bookFound) {
                JOptionPane.showMessageDialog(null, "Error: Book '" + titleToSubtractQuantity + "' not found.", "Book Not Found", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
   
    changePriceButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Open dialogs to get input for the book title and new price
            String titleToChangePrice = JOptionPane.showInputDialog("Enter the title of the book to change the price for:");
            if (titleToChangePrice == null || titleToChangePrice.isEmpty()) {
                // If the user cancels or inputs an empty title, return without further action
                return;
            }
    
            // Get the list of books from the database
            List<Book> existingBooks = BookDatabase.getInstance().getBooks();
            boolean bookFound = false;
    
            // Search for the book in the database
            for (Book book : existingBooks) {
                if (book.getTitle().equals(titleToChangePrice)) {
                    bookFound = true;
                    // If the book is found, prompt the user for the new price
                    String newPriceStr = JOptionPane.showInputDialog("Enter the new price:");
                    if (newPriceStr == null || newPriceStr.isEmpty()) {
                        // If the user cancels or inputs an empty price, return without further action
                        return;
                    }
                    try {
                        int newPrice = Integer.parseInt(newPriceStr);
                        // Set the new price for the book
                        book.setPrice(newPrice);
                        BookDatabase.getInstance().saveBooksToCSV(); // Save changes to the database

                        // Show a message dialog with the updated book details
                        String message = "Price for '" + book.getTitle() + "' changed to " + newPrice;
                        JOptionPane.showMessageDialog(null, message, "Price Changed", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException ex) {
                        // If the user inputs a non-integer price, display an error message
                        JOptionPane.showMessageDialog(null, "Error: Please enter a valid integer price.", "Invalid Price", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
            }
    
            // If the book is not found, display an error message
            if (!bookFound) {
                JOptionPane.showMessageDialog(null, "Error: Book '" + titleToChangePrice + "' not found.", "Book Not Found", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
    

    viewAllBooksButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Retrieve all books from the database
            List<Book> allBooks = BookDatabase.getInstance().getBooks();
            
            // Create a two-dimensional array to hold book data for the table
            Object[][] data = new Object[allBooks.size()][4];
            int row = 0;
            for (Book book : allBooks) {
                data[row][0] = book.getTitle();
                data[row][1] = book.getAuthor();
                data[row][2] = book.getQuantity();
                data[row][3] = book.getPrice();
                row++;
            }
    
            // Column names for the table
            String[] columns = {"Title", "Author", "Quantity", "Price"};
    
            // Create a table with the book data and column names
            JTable table = new JTable(data, columns);
            
            // Put the table in a scroll pane to enable scrolling if necessary
            JScrollPane scrollPane = new JScrollPane(table);
    
            // Display the table in a dialog
            JOptionPane.showMessageDialog(null, scrollPane, "All Books", JOptionPane.PLAIN_MESSAGE);
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
