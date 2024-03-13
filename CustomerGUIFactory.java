// CustomerGUIFactory class

import javax.swing.JFrame;

public class CustomerGUIFactory implements GUIFactory {
    @Override
    public JFrame createGUI(User user) {
        return new CustomerGUI(user);
    }
}
