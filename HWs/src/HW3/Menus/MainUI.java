package HW3.Menus;

import HW3.DeliveryDataBase;
import HW3.Exceptions.OrderNotFoundException;
import HW3.Utils.DataSelector;
import HW3.Utils.InputManager;
import HW3.Utils.MenuManager;
import HW3.Utils.UIHelper;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainUI extends UIBase {
	
	private AdminUI adminUI;
	private CustomerUI customerUI;
	private RestAdminUI restAdminUI;
	private RiderUI riderUI;
	


	public MainUI(Stage stage) throws OrderNotFoundException {
		super(new DeliveryDataBase());
		
		DataSelector.setDeliveryDataBase(deliveryDataBase);
		InputManager.setDeliveryDataBase(deliveryDataBase);
		
        stage.setTitle("Delivery System");
        adminUI = new AdminUI(deliveryDataBase);
        customerUI = new CustomerUI(deliveryDataBase);
        restAdminUI = new RestAdminUI(deliveryDataBase);
        riderUI = new RiderUI(deliveryDataBase);
    }
	

	@Override
	protected void Main() {
		VBox root = UIHelper.createVRoot();

        Label title = new Label("Delivery System");

        Button adminButton = UIHelper.createButton(
                "System Administrator Login", adminUI::Auth
        );

        Button userButton = UIHelper.createButton(
                "User Login",
                e -> showUserTypeScreen()
        );

        Button exitButton = UIHelper.createButton(
                "Exit",
                MenuManager::goBack
        );

        root.getChildren().addAll(
                title,
                adminButton,
                userButton,
                exitButton
        );

        MenuManager.goTo(new Scene(root, 500, 400));
	}
    
    private void showUserTypeScreen() {

        VBox root = UIHelper.createVRoot();

        Label title = new Label("Select User Type");

        Button customerButton = UIHelper.createButton(
                "Customer", customerUI::Auth
        );

        Button riderButton = UIHelper.createButton(
                "Rider", riderUI::Auth
        );

        Button restaurantAdminButton = UIHelper.createButton(
                "Restaurant Administrator", restAdminUI::Auth
        );

        Button backButton = UIHelper.createButton(
                "Back", this::Auth
        );

        root.getChildren().addAll(
                title,
                customerButton,
                riderButton,
                restaurantAdminButton,
                backButton
        );

        MenuManager.goTo(new Scene(root, 500, 500));
    }
}
