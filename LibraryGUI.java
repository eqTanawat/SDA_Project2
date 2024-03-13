import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// GUI using Swing
public class LibraryGUI {
    private JFrame frame;
    private Library library;
    private JTextArea notificationArea;

    public LibraryGUI() {
        frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JButton addBookButton = new JButton("Add New Book");
        addBookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewBook();
            }
        });
        buttonPanel.add(addBookButton);

        JButton addMagazineButton = new JButton("Add New Magazine");
        addMagazineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewMagazine();
            }
        });
        buttonPanel.add(addMagazineButton);

        JButton registerUserButton = new JButton("Register User");
        registerUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        buttonPanel.add(registerUserButton);

        JButton unregisterUserButton = new JButton("Unregister User");
        unregisterUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                unregisterUser();
            }
        });
        buttonPanel.add(unregisterUserButton);

        panel.add(buttonPanel, BorderLayout.NORTH);

        notificationArea = new JTextArea(10, 30);
        notificationArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(notificationArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

        library = Library.getInstance();
    }

    private void addNewBook() {
        LibraryItemFactory bookFactory = new BookFactory();
        LibraryItem book = bookFactory.createItem();
        library.addItem(book);
    }

    private void addNewMagazine() {
        LibraryItemFactory magazineFactory = new MagazineFactory();
        LibraryItem magazine = magazineFactory.createItem();
        library.addItem(magazine);
    }

    private void registerUser() {
        String name = JOptionPane.showInputDialog(frame, "Enter user name:");
        if (name != null && !name.isEmpty()) {
            library.registerObserver(new User(name));
            displayNotification("User '" + name + "' registered.");
        }
    }

    private void unregisterUser() {
        String name = JOptionPane.showInputDialog(frame, "Enter user name to unregister:");
        if (name != null && !name.isEmpty()) {
            library.removeObserver(new User(name));
            displayNotification("User '" + name + "' unregistered.");
        }
    }

    private void displayNotification(String message) {
        notificationArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LibraryGUI();
            }
        });
    }
}
