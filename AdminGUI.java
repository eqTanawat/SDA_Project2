import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

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
                // Prompt for book name
                String bookName = JOptionPane.showInputDialog(null, "Enter Book Name:");

                // Check if book name is not empty
                if (bookName != null && !bookName.trim().isEmpty()) {
                    // Prompt for quantity
                    String quantityStr = JOptionPane.showInputDialog(null, "Enter Book Quantity:");

                    // Check if quantity is not empty
                    if (quantityStr != null && !quantityStr.trim().isEmpty()) {
                        try {
                            int quantity = Integer.parseInt(quantityStr);

                            // Write book name and quantity to CSV file
                            writeBookToCSV(bookName, quantity);

                            // Perform action with book name and quantity (e.g., add the book to the inventory)
                            // Example:
                            // bookInventory.addNewBook(bookName, quantity);
                            JOptionPane.showMessageDialog(null, "Book \"" + bookName + "\" with quantity " + quantity + " appended successfully.");
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid quantity entered. Please enter a valid number.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid quantity.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid book name.");
                }
            }
        });

        // ActionListener implementation for other buttons (deleteBookButton, addBookQuantityButton, subtractBookQuantityButton)...

        buttonPanel.add(appendNewBookButton);
        buttonPanel.add(deleteBookButton);
        buttonPanel.add(addBookQuantityButton);
        buttonPanel.add(subtractBookQuantityButton);

        getContentPane().add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    protected String getRoleDescription() {
        return "You are logged in as an Admin.";
    }

    // Method to write book name and quantity to CSV file
    // Method to write book name and quantity to CSV file
private void writeBookToCSV(String bookName, int quantity) {
    String csvFilePath = "books.csv";
    String tempCsvFilePath = "temp_books.csv";

    try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
         BufferedWriter writer = new BufferedWriter(new FileWriter(tempCsvFilePath))) {
        String line;
        boolean found = false;

        // Read each line from the original CSV file
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            // Check if the book already exists in the CSV file
            if (parts.length >= 2 && parts[0].equals(bookName)) {
                int existingQuantity = Integer.parseInt(parts[1].trim());
                // Update the quantity by adding the new quantity to the existing one
                existingQuantity += quantity;
                line = bookName + "," + existingQuantity;
                found = true;
            }
            writer.write(line);
            writer.newLine();
        }

        // If the book was not found in the CSV file, add a new entry
        if (!found) {
            writer.write(bookName + "," + quantity);
            writer.newLine();
        }
    } catch (IOException ex) {
        ex.printStackTrace();
    }

    // Replace the original CSV file with the temporary file
    File originalFile = new File(csvFilePath);
    File tempFile = new File(tempCsvFilePath);
    if (tempFile.renameTo(originalFile)) {
        System.out.println("CSV file updated successfully.");
    } else {
        System.out.println("Failed to update CSV file.");
    }
}
}
