// CustomerGUI class extending BaseGUI
public class CustomerGUI extends BaseGUI {
    public CustomerGUI(User user) {
        super(user, "Customer Dashboard");
        createGUI(); // Call the template method
    }

    @Override
    protected void createRoleSpecificGUI() {
        // Specific behavior for Customer GUI
        // You can add components or customize the layout here
    }

    @Override
    protected String getRoleDescription() {
        return "You are logged in as a Customer.";
    }
}
