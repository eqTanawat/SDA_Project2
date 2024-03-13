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
                            JOptionPane.showMessageDialog(null, quantity + "of " + bookName + " added");
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

        deleteBookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display book data from CSV file and handle book deletion
                deleteBook();
            }
        });

        addBookQuantityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display book data from CSV file and handle adding book quantity
                addBookQuantity();
            }
        });

        subtractBookQuantityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Subtract book quantity functionality
                subtractBookQuantity();
            }
        });

        // ActionListener implementation for subtractBookQuantityButton...

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

    private void deleteBook() {
        // Read books data from CSV file
        String csvFilePath = "books.csv";
        String tempCsvFilePath = "temp_books.csv";
        String line;
        DefaultListModel<String> bookListModel = new DefaultListModel<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            // Read each line from the CSV file and add book names to the list model
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    bookListModel.addElement(parts[0] + ", Quantity: " + parts[1]);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Create a list to display book names
        JList<String> bookList = new JList<>(bookListModel);
        bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookList.setLayoutOrientation(JList.VERTICAL);

        // Prompt user to select a book to delete
        int result = JOptionPane.showConfirmDialog(null, new JScrollPane(bookList), "Select a book to delete", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Get the selected book name
            String selectedBook = bookList.getSelectedValue();
            if (selectedBook != null) {
                // Prompt for the quantity of books to delete
                String quantityStr = JOptionPane.showInputDialog(null, "Enter Quantity to Delete for " + selectedBook + ":");
                if (quantityStr != null && !quantityStr.trim().isEmpty()) {
                    try {
                        int quantityToDelete = Integer.parseInt(quantityStr);

                        // Update the CSV file with the new quantity
                        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
                             BufferedWriter writer = new BufferedWriter(new FileWriter(tempCsvFilePath))) {
                            while ((line = reader.readLine()) != null) {
                                String[] parts = line.split(",");
                                if (parts.length >= 2 && parts[0].equals(selectedBook.split(",")[0])) {
                                    int currentQuantity = Integer.parseInt(parts[1].trim());
                                    int updatedQuantity = currentQuantity - quantityToDelete;
                                    if (updatedQuantity > 0) {
                                        line = parts[0] + "," + updatedQuantity;
                                    } else {
                                        continue; // Skip writing this line if quantity becomes zero or negative
                                    }
                                }
                                writer.write(line);
                                writer.newLine();
                            }
                        }

                        // Replace the original CSV file with the temporary file
                        File originalFile = new File(csvFilePath);
                        File tempFile = new File(tempCsvFilePath);
                        if (tempFile.renameTo(originalFile)) {
                            JOptionPane.showMessageDialog(null, "Book \"" + selectedBook + "\" deleted successfully.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to delete book.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid quantity entered. Please enter a valid number.");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Failed to delete book.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Quantity to delete cannot be empty.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No book selected. Please select a book to delete.");
            }
        }
    }

    private void addBookQuantity() {
        // Read books data from CSV file
        String csvFilePath = "books.csv";
        String tempCsvFilePath = "temp_books.csv";
        String line;
        DefaultListModel<String> bookListModel = new DefaultListModel<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            // Read each line from the CSV file and add book names to the list model
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    bookListModel.addElement(parts[0] + ", Quantity: " + parts[1]);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        // Create a list to display book names
        JList<String> bookList = new JList<>(bookListModel);
        bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookList.setLayoutOrientation(JList.VERTICAL);

        // Prompt user to select a book to add quantity
        int result = JOptionPane.showConfirmDialog(null, new JScrollPane(bookList), "Select a book to add quantity", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Get the selected book name
            String selectedBook = bookList.getSelectedValue();
            if (selectedBook != null) {
                // Prompt for the quantity of books to add
                String quantityStr = JOptionPane.showInputDialog(null, "Enter Quantity to Add for " + selectedBook + ":");
                if (quantityStr != null && !quantityStr.trim().isEmpty()) {
                    try {
                        int quantityToAdd = Integer.parseInt(quantityStr);

                        // Update the CSV file with the new quantity
                        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
                             BufferedWriter writer = new BufferedWriter(new FileWriter(tempCsvFilePath))) {
                            while ((line = reader.readLine()) != null) {
                                String[] parts = line.split(",");
                                if (parts.length >= 2 && parts[0].equals(selectedBook.split(",")[0])) {
                                    int currentQuantity = Integer.parseInt(parts[1].trim());
                                    int updatedQuantity = currentQuantity + quantityToAdd;
                                    line = parts[0] + "," + updatedQuantity;
                                }
                                writer.write(line);
                                writer.newLine();
                            }
                        }

                        // Replace the original CSV file with the temporary file
                        File originalFile = new File(csvFilePath);
                        File tempFile = new File(tempCsvFilePath);
                        if (tempFile.renameTo(originalFile)) {
                            JOptionPane.showMessageDialog(null, "Quantity for book \"" + selectedBook + "\" added successfully.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to add quantity for book.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid quantity entered. Please enter a valid number.");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Failed to add quantity for book.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Quantity to add cannot be empty.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No book selected. Please select a book to add quantity.");
            }
        }
    }

    private void subtractBookQuantity() {
        // Read books data from CSV file
        String csvFilePath = "books.csv";
        String tempCsvFilePath = "temp_books.csv";
        String line;
        DefaultListModel<String> bookListModel = new DefaultListModel<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            // Read each line from the CSV file and add book names to the list model
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    bookListModel.addElement(parts[0] + ", Quantity: " + parts[1]);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    
        // Create a list to display book names
        JList<String> bookList = new JList<>(bookListModel);
        bookList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookList.setLayoutOrientation(JList.VERTICAL);
    
        // Prompt user to select a book to subtract quantity from
        int result = JOptionPane.showConfirmDialog(null, new JScrollPane(bookList), "Select a book to subtract quantity from", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
        if (result == JOptionPane.OK_OPTION) {
            // Get the selected book name
            String selectedBook = bookList.getSelectedValue();
            if (selectedBook != null) {
                // Prompt for the quantity of books to subtract
                String quantityStr = JOptionPane.showInputDialog(null, "Enter Quantity to Subtract for " + selectedBook + ":");
                if (quantityStr != null && !quantityStr.trim().isEmpty()) {
                    try {
                        int quantityToSubtract = Integer.parseInt(quantityStr);
    
                        // Update the CSV file with the new quantity
                        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
                             BufferedWriter writer = new BufferedWriter(new FileWriter(tempCsvFilePath))) {
                            while ((line = reader.readLine()) != null) {
                                String[] parts = line.split(",");
                                if (parts.length >= 2 && parts[0].equals(selectedBook.split(",")[0])) {
                                    int currentQuantity = Integer.parseInt(parts[1].trim());
                                    int updatedQuantity = currentQuantity - quantityToSubtract;
                                    if (updatedQuantity > 0) {
                                        line = parts[0] + "," + updatedQuantity;
                                    } else {
                                        continue; // Skip writing this line if quantity becomes zero or negative
                                    }
                                }
                                writer.write(line);
                                writer.newLine();
                            }
                        }
    
                        // Replace the original CSV file with the temporary file
                        File originalFile = new File(csvFilePath);
                        File tempFile = new File(tempCsvFilePath);
                        if (tempFile.renameTo(originalFile)) {
                            JOptionPane.showMessageDialog(null, "Quantity subtracted successfully for " + selectedBook);
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to subtract quantity.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid quantity entered. Please enter a valid number.");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Failed to subtract quantity.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Quantity to subtract cannot be empty.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No book selected. Please select a book to subtract quantity from.");
            }
        }
    }
    
}
