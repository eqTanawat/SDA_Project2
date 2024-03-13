import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookDatabase {
    private static final String FILENAME = "books.csv";
    private static BookDatabase instance;
    private List<Book> books;

    private BookDatabase() {
        books = loadBooksFromCSV(FILENAME);
    }

    public static BookDatabase getInstance() {
        if (instance == null) {
            instance = new BookDatabase();
        }
        return instance;
    }

    public List<Book> getBooks() {
        return books;
    }

    private List<Book> loadBooksFromCSV(String filename) {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String title = parts[0];
                String author = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                int price = Integer.parseInt(parts[3]);
                books.add(new Book(title, author, quantity, price));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void saveBooksToCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            writer.println("Title,Author,Quantity,Price"); // Write header
            for (Book book : books) {
                writer.println(book.toCSV()); // Write book details
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendNewBook(Book book) {
        // Check if the book already exists in the database
        for (Book existingBook : books) {
            if (existingBook.getTitle().equals(book.getTitle())) {
                System.out.println("Error: Book with the same title already exists.");
                return; // Exit the method without adding the book
            }
        }
    
        // If the book does not exist, add it to the database and save to CSV
        books.add(book);
        saveBooksToCSV();
    }

    public void deleteBook(String title) {
        books.removeIf(book -> book.getTitle().equals(title));
        saveBooksToCSV();
    }

    public void addBookQuantity(String title, int quantityToAdd) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                book.setQuantity(book.getQuantity() + quantityToAdd);
                saveBooksToCSV();
                return;
            }
        }
    }

    public void subtractBookQuantity(String title, int quantityToSubtract) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                int newQuantity = book.getQuantity() - quantityToSubtract;
                if (newQuantity > 0) {
                    book.setQuantity(newQuantity);
                    saveBooksToCSV();
                } else if (newQuantity == 0) {
                    deleteBook(book.getTitle());
                } else {
                    System.out.println("Error: Cannot subtract quantity. Quantity would become negative.");
                }
                return;
            }
        }
        System.out.println("Error: Book not found.");
    }

    public void changeBookPrice(String title, int newPrice) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                book.setPrice(newPrice);
                saveBooksToCSV();
                return;
            }
        }
        System.out.println("Error: Book not found.");
    }

    public List<Book> getAllBooks() {
        return books;
    }

}

