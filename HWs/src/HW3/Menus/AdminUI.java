package HW3.Menus;

import java.util.ArrayList;

import HW3.DeliveryDataBase;
import HW3.DataObjects.*;
import HW3.Menus.AdminManagment.*;
import HW3.Utils.DataManager;
import HW3.Utils.MessageBox;
import HW3.Utils.UIHelper;
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
	
	private ReportsUI reportsUI;
	

	public AdminUI(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
		super(stage, deliveryDataBase, backF);
		
		customerManagment = new CustomerManagment(stage, deliveryDataBase, this::Main);
		orderManagment = new OrderManagment(stage, deliveryDataBase, this::Main);
		restAdminManagment = new RestAdminManagment(stage, deliveryDataBase, this::Main);
		restaurantManagment = new RestaurantManagment(stage, deliveryDataBase, this::Main);
		riderManagment = new RiderManagment(stage, deliveryDataBase, this::Main);
		
		reportsUI = new ReportsUI(stage, deliveryDataBase, this::Main);
	}

	@Override
	public void Auth() {
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

        Button customersButton = UIHelper.createButton("Customer Management", customerManagment::Auth);

        Button restaurantsButton = UIHelper.createButton("Restaurant Management", restaurantManagment::Auth);

        Button ordersButton = UIHelper.createButton("Order Management", orderManagment::Auth);

        Button ridersButton = UIHelper.createButton("Rider Management", riderManagment::Auth);

        Button restaurantAdminsButton = UIHelper.createButton("Restaurant Administrator Management", restAdminManagment::Auth);

        Button reportsButton = UIHelper.createButton("Reports and Sorting", reportsUI::Auth);

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
    
	// save action
    private void saveData() {
    	if(!MessageBox.inputBOOL("Save", "Are you shure you want to save", null, false))
    		return;
    	
    	try {
            DataManager.save(deliveryDataBase.getRestAdmins(), RestAdmin.class);
            DataManager.save(deliveryDataBase.getRestaurants(), Restaurant.class);
            DataManager.save(deliveryDataBase.getCustomers(), Customer.class);
            DataManager.save(deliveryDataBase.getRiders(), Rider.class);
            DataManager.save(deliveryDataBase.getOrders(), Order.class);
            MessageBox.Info("Save Data", "Data saved successfully.");
        } catch (Exception e) {
            MessageBox.error("Save Error", e);
        }
    }
    
    // load action
    private void loadData() {
    	if(!MessageBox.inputBOOL("Load", "Are you shure you want to load", null, false))
    		return;
        try {
            deliveryDataBase.setRestaurants(new ArrayList<>(DataManager.load(Restaurant.class).stream().map(c -> c.output).toList()));
            deliveryDataBase.setCustomers(new ArrayList<>(DataManager.load(Customer.class).stream().map(c -> c.output).toList()));
            
            // needs Customers
            deliveryDataBase.setOrders(new ArrayList<>(DataManager.load(Order.class).stream().map(c -> c.output).toList()));
            
            deliveryDataBase.loadRestAdmins(DataManager.load(RestAdmin.class)); // needs Restaurants
            deliveryDataBase.loadRiders(DataManager.load(Rider.class)); // needs Orders
            MessageBox.Info("Load Data", "Data loaded successfully.");
        } catch (Exception e) {
            MessageBox.error("Load Error", e);
        }
    }
}
