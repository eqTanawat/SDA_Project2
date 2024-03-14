import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Book, Integer> items;

    public Cart() {
        items = new HashMap<>();
    }

    public void addItem(Book book, int quantity) {
        items.put(book, quantity);
    }

    public void removeItem(Book book) {
        items.remove(book);
    }

    public Map<Book, Integer> getItems() {
        return items;
    }

    public int getTotalCost() {
        int totalCost = 0;
        for (Map.Entry<Book, Integer> entry : items.entrySet()) {
            totalCost += entry.getKey().getPrice() * entry.getValue();
        }
        return totalCost;
    }
}
