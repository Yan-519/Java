package HW3.UI.Menus;

import java.util.List;
import java.util.stream.Collectors;

import HW3.DeliveryDataBase;
import HW3.DeliveryDataBase.CodedType;
import HW3.UI.MessageBox;
import HW3.UI.UIHelper;
import HW3.DataObjects.Customer;
import HW3.Utils.DataSelector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminUI extends UIBase {

	public AdminUI(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
		super(stage, deliveryDataBase, backF);
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

        Button loginButton = UIHelper.createButton("Login");
        Button backButton = UIHelper.createButton("Back");

        loginButton.setOnAction(e -> {
        	
            if (deliveryDataBase.logIntoAdmin(username.getText(), password.getText())) 
                Main();
            
            else MessageBox.error("Login Error", "Incorrect username or password.");
        });

        backButton.setOnAction(e -> backF.run());

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

        Button customersButton = UIHelper.createButton("Customer Management", e -> showCustomerManagement());

        Button restaurantsButton = UIHelper.createButton("Restaurant Management", e -> showRestaurantManagement());

        Button ordersButton = UIHelper.createButton("Order Management", e -> showOrderManagement());

        Button ridersButton = UIHelper.createButton("Rider Management", e -> showRiderManagement());

        Button restaurantAdminsButton = UIHelper.createButton("Restaurant Administrator Management", e -> showRestaurantAdminManagement());

        Button reportsButton = UIHelper.createButton("Reports and Sorting", e -> showReports());

        Button saveButton = UIHelper.createButton("Save Data", e -> saveData());

        Button loadButton = UIHelper.createButton("Load Data", e -> loadData());

        Button exitButton = UIHelper.createButton("Exit", e -> backF.run());

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



    private void showCustomerManagement() {
        BorderPane root = new BorderPane();
        
        VBox header = UIHelper.createVRoot("Customer");
        root.setTop(header);

        GridPane grid = new GridPane();

        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(30));
        grid.setAlignment(Pos.CENTER);

        Button showCustomers = UIHelper.createButton(
                "Show All Customers",
                e -> showAllCustomers()
        );

        Button searchCustomer = UIHelper.createButton(
                "Search Customer by Code",
                e -> MessageBox.Info(DataSelector.selectCustomer())
        );

        Button addCustomer = UIHelper.createButton(
                "Add New Customer",
                e -> addCustomer()
        );

        Button updateCustomer = UIHelper.createButton(
                "Update Customer",
                e -> updateCustomer()
        );

        Button showOrders = UIHelper.createButton(
                "Show Customer Orders",
                e -> showCustomerOrders()
        );

        Button cancelOrder = UIHelper.createButton(
                "Cancel Order",
                e -> cancelOrder()
        );

        Button showRestaurants = UIHelper.createButton(
                "Show Ordered Restaurants",
                e -> showOrderedRestaurants()
        );

        Button showPremiumRestaurants = UIHelper.createButton(
                "Show Ordered Premium Restaurants",
                e -> showOrderedPremiumRestaurants()
        );

        Button logout = UIHelper.createButton(
                "Logout",
                e -> Auth()
        );

        grid.add(showCustomers, 0, 0);
        grid.add(searchCustomer, 1, 0);

        grid.add(addCustomer, 0, 1);
        grid.add(updateCustomer, 1, 1);

        grid.add(showOrders, 0, 2);
        grid.add(cancelOrder, 1, 2);

        grid.add(showRestaurants, 0, 3);
        grid.add(showPremiumRestaurants, 1, 3);

        grid.add(logout, 0, 4, 2, 1);

        root.setCenter(grid);

        UIHelper.setScene(stage, root, 800, 650);
    }
    
    private void showAllCustomers() {
    	
    	List<String> customers = deliveryDataBase.getCustomers().stream()
    	        .map(Object::toString)
    	        .collect(Collectors.toList());

    	ObservableList<String> observableCustomers = FXCollections.observableArrayList(customers);

    	ListView<String> listView = new ListView<>(observableCustomers);
    	
    	
    	Button backButton = UIHelper.createButton("Back", e -> showCustomerManagement());
    	
    	VBox root = UIHelper.createVRoot();
    	
    	root.getChildren().addAll(listView, backButton);
    	
    	UIHelper.setScene(stage, root, 300, 250);
    }

    private void addCustomer() {
    	
//		int code = deliveryDataBase.generateCode(CodedType.Customer);
//		if(deliveryDataBase.addCustomer(InputManager.creatCustomer(code)))
//			System.out.println("The customer code is " + code);
    }

    private void updateCustomer() {
    }

    private void showCustomerOrders() {
    }

    private void cancelOrder() {
    }

    private void showOrderedRestaurants() {
    }

    private void showOrderedPremiumRestaurants() {
    }

    
    
    

    private void showRestaurantManagement() {
    }

    private void showOrderManagement() {
    }

    private void showRiderManagement() {
    }

    private void showRestaurantAdminManagement() {
    }

    private void showReports() {
    }

    
    
    
    
    private void saveData() {
    }

    private void loadData() {
    }

    
    
	
}
