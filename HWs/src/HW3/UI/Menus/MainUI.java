package HW3.UI.Menus;

import HW3.DeliveryDataBase;
import HW3.UI.UIHelper;
import HW3.Utils.DataInitializer;
import HW3.Utils.DataSelector;
import HW3.Utils.InputManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainUI extends UIBase {
	
	private AdminUI adminUI;
	private CustomerUI customerUI;
	private RestAdminUI restAdminUI;
	private RiderUI riderUI;
	

    private MainUI(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
		super(stage, deliveryDataBase, backF);
	}

	public MainUI(Stage stage) {
		super(stage, DataInitializer.initialDataBase(), () -> {});
		
		DataSelector.setDeliveryDataBase(deliveryDataBase);
		InputManager.setDeliveryDataBase(deliveryDataBase);
		
        stage.setTitle("Delivery System");
        adminUI = new AdminUI(stage, deliveryDataBase, this::Init);
        customerUI = new CustomerUI(stage, deliveryDataBase, this::showUserTypeScreen);
        restAdminUI = new RestAdminUI(stage, deliveryDataBase, this::showUserTypeScreen);
        riderUI = new RiderUI(stage, deliveryDataBase, this::showUserTypeScreen);
    }
	

	@Override
	public void Init() {
        Main();
	}
	

	@Override
	protected void Main() {
		VBox root = UIHelper.createVRoot();

        Label title = new Label("Delivery System");

        Button adminButton = UIHelper.createButton(
                "System Administrator Login", adminUI::Init
        );

        Button userButton = UIHelper.createButton(
                "User Login",
                e -> showUserTypeScreen()
        );

        Button exitButton = UIHelper.createButton(
                "Exit",
                e -> stage.close()
        );

        root.getChildren().addAll(
                title,
                adminButton,
                userButton,
                exitButton
        );

        UIHelper.setScene(stage, root, 500, 400);
	}
    
    private void showUserTypeScreen() {

        VBox root = UIHelper.createVRoot();

        Label title = new Label("Select User Type");

        Button customerButton = UIHelper.createButton(
                "Customer", customerUI::Init
        );

        Button riderButton = UIHelper.createButton(
                "Rider", riderUI::Init
        );

        Button restaurantAdminButton = UIHelper.createButton(
                "Restaurant Administrator", restAdminUI::Init
        );

        Button backButton = UIHelper.createButton(
                "Back", this::Init
        );

        root.getChildren().addAll(
                title,
                customerButton,
                riderButton,
                restaurantAdminButton,
                backButton
        );

        UIHelper.setScene(stage, root, 500, 500);
    }
}
