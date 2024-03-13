import java.util.ArrayList;
import java.util.List;

public class BookDatabaseDemo {
    public static void main(String[] args) {
        // Get the singleton instance of BookDatabase
        BookDatabase bookDatabase = BookDatabase.getInstance();

        // Display the initial book list
        System.out.println("Initial book list:");
        displayBooks(bookDatabase.getBooks());

        // Example: Append a new book
        Book newBook = new Book("New Book", "New Author", 5, 15);
        bookDatabase.appendNewBook(newBook);

        // Display the updated book list
        System.out.println("\nBook list after appending a new book:");
        displayBooks(bookDatabase.getBooks());

        // Example: Delete a book
        bookDatabase.deleteBook("Book 2");

        // Display the updated book list
        System.out.println("\nBook list after deleting a book:");
        displayBooks(bookDatabase.getBooks());

        // Example: Add book quantity
        bookDatabase.addBookQuantity("Book 1", 5);

        // Display the updated book list
        System.out.println("\nBook list after adding book quantity:");
        displayBooks(bookDatabase.getBooks());

        // Example: Subtract book quantity
        bookDatabase.subtractBookQuantity("Book 3", 0);

        // Display the updated book list
        System.out.println("\nBook list after subtracting book quantity:");
        displayBooks(bookDatabase.getBooks());

        System.out.println("\nBook list after changing book price:");
        bookDatabase.changeBookPrice("Book 1", 40);

    }

    // Helper method to display a list of books
    private static void displayBooks(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }
}
