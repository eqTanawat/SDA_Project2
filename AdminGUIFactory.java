// AdminGUIFactory class

import javax.swing.JFrame;

public class AdminGUIFactory implements GUIFactory {
    @Override
    public JFrame createGUI(User user) {
        return new AdminGUI(user);
    }
}
