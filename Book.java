// Book class representing a book entity
public class Book {
    private String title;
    private String author;
    private int quantity;
    private int price;

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public Book(String title, String author, int quantity, int price) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    // Getters and setters for book attributes
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", quantity=" + quantity + '\'' + 
                ", price=" + price  +
                '}';
    }

    public String toCSV() {
        return String.format("%s,%s,%d,%d", title, author, quantity, price);
    }
}
