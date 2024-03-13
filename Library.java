import java.util.ArrayList;
import java.util.List;

// Singleton Pattern
public class Library {
    private static Library instance;
    private List<LibraryItem> items;
    private List<LibraryObserver> observers;

    private Library() {
        items = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    public void addItem(LibraryItem item) {
        items.add(item);
        notifyObservers(item);
    }

    public void registerObserver(LibraryObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(LibraryObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(LibraryItem item) {
        for (LibraryObserver observer : observers) {
            observer.update(item);
        }
    }
}
