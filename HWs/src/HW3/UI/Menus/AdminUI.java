package HW3.UI.Menus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import HW3.DeliveryDataBase;
import HW3.DeliveryDataBase.CodedType;
import HW3.DataObjects.Customer;
import HW3.DataObjects.Order;
import HW3.DataObjects.Order.OrderStatus;
import HW3.Exceptions.*;
import HW3.UI.MessageBox;
import HW3.UI.UIHelper;
import HW3.Utils.DataSelector;
import HW3.Utils.DataChecker;
import HW3.Utils.InputManager;
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

        Button customersButton = UIHelper.createButton("Customer Management", this::showCustomerManagement);

        Button restaurantsButton = UIHelper.createButton("Restaurant Management", this::showRestaurantManagement);

        Button ordersButton = UIHelper.createButton("Order Management", this::showOrderManagement);

        Button ridersButton = UIHelper.createButton("Rider Management", this::showRiderManagement);

        Button restaurantAdminsButton = UIHelper.createButton("Restaurant Administrator Management", this::showRestaurantAdminManagement);

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
                () -> UIHelper.showList(stage, deliveryDataBase.getCustomers(), this::showCustomerManagement)
        );

        Button searchCustomer = UIHelper.createButton(
                "Search Customer by Code",
                () -> MessageBox.Info(DataSelector.selectCustomer())
        );

        Button addCustomer = UIHelper.createButton(
                "Add New Customer",
                e -> {
                	int code = deliveryDataBase.generateCode(CodedType.Customer);

                	InputManager.createCustomer(stage, code, customer -> {
                	    if (customer != null) {
                	    	try {
	                	        if (deliveryDataBase.add(customer)) 
	                	            MessageBox.Info("The customer code is " + code);
	                	    } catch (TargetObjectAlreadyExistException ex) {
	                	        MessageBox.error(ex);
	                	    }
                	    }
                	    showCustomerManagement();
                	});
                }
        );

        Button updateCustomer = UIHelper.createButton(
                "Update Customer",
                this::updateCustomer
        );

        Button showOrders = UIHelper.createButton(
                "Show Customer Orders",
                e -> {
                	Customer customer = DataSelector.selectCustomer();
                	if(customer == null)
                		showCustomerManagement();
                	
                	else UIHelper.showList(stage, deliveryDataBase.getOrdersOfCustomer(customer), this::showCustomerManagement);
                }
        );

        Button cancelOrder = UIHelper.createButton(
                "Cancel Order",
                this::cancelOrder
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


    private void updateCustomer() {
    	Customer customer = DataSelector.selectCustomer();
    	
		if(MessageBox.inputBOOL("Do you want to change your adress?")){
			if(MessageBox.inputBOOL("Do you want to chage the town?")) {
				String townString = MessageBox.inputSTR(null, "Enter new town", true);
				if(townString != null)
					customer.setTown(townString);
			}
	
			if(MessageBox.inputBOOL("Do you want to chage the street?")) {
				String streetString = MessageBox.inputSTR(null, "Enter new street", false);
				if(streetString != null)
					customer.setStreet(streetString);
			}
	
			if(MessageBox.inputBOOL("Do you want to chage the ZIP code?")) {
				String zip = DataSelector.dataFilter(() -> MessageBox.inputSTR(null, "Enter new ZIP code", ""), DataChecker::isValidZipCode, "Zip code must be not newgative 5-7 digits");
			    if(zip != null)
			    	customer.setZipCode(zip);
			}
		}
		if(MessageBox.inputBOOL("Do you want to chage your phone number?")) {
			String phone = DataSelector.dataFilter(() -> MessageBox.inputSTR(null, "Enter the new phone number (IL)", null), DataChecker::isValidPhoneNumber, "Not valid phone number");
			if(phone != null)
				customer.setPhoneNumber(phone);
		}

    }

    private void cancelOrder() {
    	
    	Customer customer = DataSelector.selectCustomer();
    	if(customer == null) {
    		showCustomerManagement();
    		return;
    	}
    	
    	ArrayList<Order> orders = deliveryDataBase.getOrdersOfCustomer(customer);
    	if(orders.isEmpty())
    	{
    		MessageBox.Info("selected customer has no orders");
    		showCustomerManagement();
    		return;
    	}
    	
    	UIHelper.showList(stage, orders, this::showCustomerManagement);
	
		Order order = DataSelector.selectOrder();
		if(order == null) break;
		while(order.getClientCode() != customer.getCode() || order.getOrderStatus() == OrderStatus.Delivered) {
			if(order.getClientCode() != customer.getCode())
					System.out.println("The selected order must me your");
			else if(order.getOrderStatus() == OrderStatus.Delivered)
				System.out.println("You can't cencel an order that has beed deliverd");
			order = DataSelector.selectOrder();
			if(order == null) break;
		}
		if(order == null) break;
		
		try {
			deliveryDataBase.removeOrder(order.getCode());
		} catch (CodedNotFoundException | RiderNotFoundException | TargetObjectDoesntExistException e) {
			MessageBox.error(e);
		}

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
