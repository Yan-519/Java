package HW3.UI.Menus;

import HW3.DeliveryDataBase;
import HW3.UI.MessageBox;
import HW3.UI.UIHelper;
import HW3.UI.Menus.AdminManagment.CustomerManagment;
import HW3.UI.Menus.AdminManagment.OrderManagment;
import HW3.UI.Menus.AdminManagment.RestAdminManagment;
import HW3.UI.Menus.AdminManagment.RestaurantManagment;
import HW3.UI.Menus.AdminManagment.RiderManagment;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminUI extends UIBase {
	
	private CustomerManagment customerManagment;
	private OrderManagment orderManagment;
	private RestAdminManagment restAdminManagment;
	private RestaurantManagment restaurantManagment;
	private RiderManagment riderManagment;
	

	public AdminUI(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
		super(stage, deliveryDataBase, backF);
		
		customerManagment = new CustomerManagment(stage, deliveryDataBase, this::Main);
		orderManagment = new OrderManagment(stage, deliveryDataBase, this::Main);
		restAdminManagment = new RestAdminManagment(stage, deliveryDataBase, this::Main);
		restaurantManagment = new RestaurantManagment(stage, deliveryDataBase, this::Main);
		riderManagment = new RiderManagment(stage, deliveryDataBase, this::Main);
	}

	@Override
	public void Init() {
		
        VBox root = UIHelper.createVRoot();

        Label title = new Label("System Administrator Login");

        TextField username = new TextField();
        username.setPromptText("Username");
        username.setMaxWidth(300);

        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setMaxWidth(300);

        Button loginButton = UIHelper.createButton("Login", () -> {
        	if (deliveryDataBase.logIntoAdmin(username.getText(), password.getText())) 
                Main();
            
            else MessageBox.error("Login Error", "Incorrect username or password.");
        });
        
        Button backButton = UIHelper.createButton("Back", backF);
        
        root.getChildren().addAll(
                title,
                username,
                password,
                loginButton,
                backButton
        );

        stage.setScene(new Scene(root, 500, 400));
	}
	

	@Override
	protected void Main() {
        BorderPane root = new BorderPane();

        VBox header = UIHelper.createVRoot("System Administrator");

        root.setTop(header);

        GridPane grid = new GridPane();

        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(30));
        grid.setAlignment(Pos.CENTER);

        Button customersButton = UIHelper.createButton("Customer Management", customerManagment::Init);

        Button restaurantsButton = UIHelper.createButton("Restaurant Management", restaurantManagment::Init);

        Button ordersButton = UIHelper.createButton("Order Management", orderManagment::Init);

        Button ridersButton = UIHelper.createButton("Rider Management", riderManagment::Init);

        Button restaurantAdminsButton = UIHelper.createButton("Restaurant Administrator Management", restAdminManagment::Init);

        Button reportsButton = UIHelper.createButton("Reports and Sorting", this::showReports);

        Button saveButton = UIHelper.createButton("Save Data", this::saveData);

        Button loadButton = UIHelper.createButton("Load Data", this::loadData);

        Button exitButton = UIHelper.createButton("Exit", backF);

        grid.add(customersButton, 0, 0);
        grid.add(restaurantsButton, 1, 0);

        grid.add(ordersButton, 0, 1);
        grid.add(ridersButton, 1, 1);

        grid.add(restaurantAdminsButton, 0, 2);
        grid.add(reportsButton, 1, 2);

        grid.add(saveButton, 0, 3);
        grid.add(loadButton, 1, 3);

        grid.add(exitButton, 0, 4, 2, 1);

        root.setCenter(grid);

        stage.setScene(new Scene(root, 700, 600));

    }

    private void showReports() {
    }
    
    private void saveData() {
    }

    private void loadData() {
    }
}
