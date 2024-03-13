import java.io.*;

// Singleton class for managing user data
public class UserDatabase {
    private static UserDatabase instance;
    private String[][] userData;

    private UserDatabase() {
        // Load user data from CSV file
        try {
            BufferedReader br = new BufferedReader(new FileReader("users.csv"));
            String line;
            int rowCount = 0;
            // Initialize userData array
            userData = new String[getNumLines("users.csv") - 1][2];
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (rowCount == 0) {
                    // Skip header row
                    rowCount++;
                    continue;
                }
                userData[rowCount - 1] = values;
                rowCount++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get the number of lines in a file
    private int getNumLines(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        int lines = 0;
        while (reader.readLine() != null) lines++;
        reader.close();
        return lines;
    }

    public static UserDatabase getInstance() {
        if (instance == null) {
            instance = new UserDatabase();
        }
        return instance;
    }

    // Method to validate user credentials
    public boolean validateUser(String username, String password) {
        for (String[] user : userData) {
            if (user[0].equals(username) && user[1].equals(password)) {
                return true;
            }
        }
        return false;
    }

    // Method to register a new user
    public boolean registerUser(String username, String password) {
        // Check if username already exists
        for (String[] user : userData) {
            if (user[0].equals(username)) {
                return false; // Username already exists
            }
        }
        try {
            // Append new user data to the CSV file
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv", true));
            writer.write(username + "," + password);
            writer.newLine();
            writer.close();
            // Update userData array
            String[][] newData = new String[userData.length + 1][2];
            System.arraycopy(userData, 0, newData, 0, userData.length);
            newData[userData.length] = new String[]{username, password};
            userData = newData;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
