import javax.swing.SwingUtilities;

    public class BookStoreDemo {
        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new LoginGUI();
                }
            });
        }
}
