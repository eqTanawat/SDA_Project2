// AdminGUI class extending BaseGUI
public class AdminGUI extends BaseGUI {
    public AdminGUI(User user) {
        super(user, "Admin Dashboard");
        createGUI(); // Call the template method
    }

    @Override
    protected void createRoleSpecificGUI() {
        // Specific behavior for Admin GUI
        // You can add components or customize the layout here
    }

    @Override
    protected String getRoleDescription() {
        return "You are logged in as an Admin.";
    }
}
