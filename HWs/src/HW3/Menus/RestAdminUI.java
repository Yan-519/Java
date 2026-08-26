package HW3.Menus;

import HW3.DeliveryDataBase;
import HW3.DataObjects.RestAdmin;
import HW3.Exceptions.RestAdminNotFoundException;
import HW3.Utils.MessageBox;
import HW3.Utils.UIHelper;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.stream.Collectors;
import HW3.DataObjects.Customer;
import HW3.DataObjects.Order;
import HW3.DataObjects.Order.OrderStatus;
import HW3.DataObjects.Restaurant;
import HW3.DataObjects.Rider;
import HW3.DeliveryDataBase.CodedType;
import HW3.Exceptions.TargetObjectAlreadyExistException;
import HW3.Exceptions.TargetObjectDoesntExistException;
import HW3.Utils.DataSelector;
import HW3.Utils.InputManager;
import HW3.Utils.MessageBox.NumberSign;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class RestAdminUI extends UIBase  {
	
	private RestAdmin restAdmin;

	public RestAdminUI(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
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
        	try {
        		restAdmin = deliveryDataBase.getRestAdmin(username.getText(), password.getText());
	            Main();
			} catch (RestAdminNotFoundException e) {
				MessageBox.error(e);
			}
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

	    VBox header = UIHelper.createVRoot("Restaurant Administrator Portal");
	    root.setTop(header);

	    GridPane grid = new GridPane();
	    grid.setHgap(20);
	    grid.setVgap(20);
	    grid.setPadding(new Insets(30));
	    grid.setAlignment(Pos.CENTER);

	    Button viewRestaurantsButton = UIHelper.createButton("View My Restaurants", 
	    		() -> Tables.restaurant(stage, restAdmin.getRestaurants(), this::Main));
	    Button addCustomerButton = UIHelper.createButton("Add New Customer", this::addNewCustomer);

	    Button addOrderButton = UIHelper.createButton("Add New Order", this::addNewOrder);
	    Button assignRiderButton = UIHelper.createButton("Assign Rider to Order", this::assignRiderToOrder);

	    Button viewOrdersButton = UIHelper.createButton("View Restaurant Orders", this::viewRestaurantOrders);
	    Button filterByKitchenButton = UIHelper.createButton("Open Restaurants by Kitchen", this::showOpenRestaurantsByKitchen);

	    Button updateRatingButton = UIHelper.createButton("Update Restaurant Rating", this::updateRestaurantRating);
	    Button toggleStatusButton = UIHelper.createButton("Open/Close Restaurant", this::toggleRestaurantStatus);

	    Button viewReportsButton = UIHelper.createButton("View Basic Reports", this::viewBasicReports);
	    Button backButton = UIHelper.createButton("Back", backF);

	    grid.add(viewRestaurantsButton, 0, 0);
	    grid.add(addCustomerButton, 1, 0);

	    grid.add(addOrderButton, 0, 1);
	    grid.add(assignRiderButton, 1, 1);

	    grid.add(viewOrdersButton, 0, 2);
	    grid.add(filterByKitchenButton, 1, 2);

	    grid.add(updateRatingButton, 0, 3);
	    grid.add(toggleStatusButton, 1, 3);

	    grid.add(viewReportsButton, 0, 4);
	    grid.add(backButton, 1, 4);

	    root.setCenter(grid);

	    stage.setScene(new Scene(root, 750, 650));
	}

	private void addNewCustomer() {
	    int code;
		try {
			code = deliveryDataBase.generateCode(CodedType.Customer);
		} catch (TargetObjectDoesntExistException e) {
			MessageBox.error(e);
			return;
		}
	    InputManager.createCustomer(stage, code, customer -> {
	        if (customer != null) {
	            try {
	                deliveryDataBase.add(customer);
	                MessageBox.Info("Success", "Customer added with code: " + code);
	            } catch (TargetObjectAlreadyExistException ex) {
	                MessageBox.error(ex);
	            }
	        }
	        Main();
	    });
	}

	private void addNewOrder() {
	    if (restAdmin.getOpenRestaurants().isEmpty()) {
	        MessageBox.Info("Notice", "You have no open restaurants to take orders.");
	        return;
	    }

	    Restaurant restaurant = DataSelector.dataFilter(DataSelector::selectRestaurant, 
	            r -> restAdmin.containsRestaurant(r.getCode()) && r.getIsOpen(), 
	            "Please select an open restaurant under your management.");
	    if (restaurant == null) return;

	    Customer customer = DataSelector.selectCustomer();
	    if (customer == null) return;

	    Double basePrice = MessageBox.inputDOUB(null, "Enter the base fee (not negative)", NumberSign.NOT_NEGATIVE);
	    if (basePrice == null) return;

	    InputManager.createDate(stage, date -> {
	        if (date != null) {
	            try {
	                int orderCode = deliveryDataBase.addOrder(restaurant.getCode(), customer.getCode(), basePrice, date);
	                MessageBox.Info("Order Created", "New order code: " + orderCode);
	            } catch (Exception e) {
	                MessageBox.error(e);
	            }
	        }
	        Main();
	    });
	}

	private void assignRiderToOrder() {
	    Rider rider = DataSelector.dataFilter(DataSelector::selectRider, Rider::getIsAvailable, "Please select an available rider.");
	    if (rider == null) return;

	    Order order = DataSelector.dataFilter(DataSelector::selectOrder, 
	            o -> o.getStatus() == OrderStatus.Created && restAdmin.containsRestaurant(o.getRestaurantCode()), 
	            "Please select a 'Created' order from one of your restaurants.");
	    if (order == null) return;

	    try {
	        deliveryDataBase.addOrderToRider(rider.getId(), order.getCode(), restAdmin.getCode());
	        MessageBox.Info("Success", "Order assigned to rider successfully.");
	    } catch (Exception e) {
	        MessageBox.error(e);
	    }
	}

	private void viewRestaurantOrders() {
	    if (restAdmin.getRestaurants().isEmpty()) {
	        MessageBox.Info("Notice", "You have no restaurants.");
	        return;
	    }

	    Restaurant rest = DataSelector.dataFilter(DataSelector::selectRestaurant, 
	            r -> restAdmin.containsRestaurant(r.getCode()), 
	            "Please select a restaurant under your management.");
	    if (rest == null) return;

	    Tables.order(stage, deliveryDataBase.getOrdersByuRestaurant(rest.getCode()), this::Main);
	}

	private void showOpenRestaurantsByKitchen() {
	    String kitchenType = MessageBox.inputSTR(null, "Enter kitchen type", null);
	    if (kitchenType == null) return;

	    ArrayList<Restaurant> filtered = deliveryDataBase.getOpenRestaurants(kitchenType).stream()
	            .filter(r -> restAdmin.containsRestaurant(r.getCode()))
	            .collect(Collectors.toCollection(ArrayList::new));

	    if (filtered.isEmpty()) {
	        MessageBox.Info("Notice", "No open matching restaurants found under your management.");
	    } else {
	    	Tables.restaurant(stage, filtered, this::Main);
	    }
	}

	private void updateRestaurantRating() {
	    Restaurant restaurant = DataSelector.dataFilter(DataSelector::selectRestaurant, 
	            r -> restAdmin.containsRestaurant(r.getCode()), 
	            "Please select a restaurant under your management.");
	    if (restaurant == null) return;

	    Double rating = MessageBox.inputDOUB(null, "Enter new rating (0-5)", 5);
	    if (rating == null) return;

	    try {
	        deliveryDataBase.updateRestaurantRaiting(restaurant.getCode(), rating);
	        MessageBox.Info("Success", "Rating updated successfully.");
	    } catch (Exception e) {
	        MessageBox.error(e);
	    }
	}

	private void toggleRestaurantStatus() {
	    Restaurant restaurant = DataSelector.dataFilter(DataSelector::selectRestaurant, 
	            r -> restAdmin.containsRestaurant(r.getCode()), 
	            "Please select a restaurant under your management.");
	    if (restaurant == null) return;

	    boolean currentStatus = restaurant.getIsOpen();
	    boolean change = MessageBox.inputBOOL(null, "Restaurant is currently " + (currentStatus ? "OPEN" : "CLOSED") + ". Toggle status?", null, false);
	    
	    if (change) {
	        try {
	            deliveryDataBase.changeRestaurantStatus(restaurant.getCode());
	            MessageBox.Info("Success", "Restaurant status changed to " + (restaurant.getIsOpen() ? "OPEN" : "CLOSED"));
	        } catch (Exception e) {
	            MessageBox.error(e);
	        }
	    }
	}

	private void viewBasicReports() {
		// TODO
	    // Shows a simple summary report for the admin's managed restaurants
	    int totalRestaurants = restAdmin.getRestaurants().size();
	    long openCount = restAdmin.getOpenRestaurants().size();
	    
	    StringBuilder report = new StringBuilder();
	    report.append("Total Restaurants: ").append(totalRestaurants).append("\n");
	    report.append("Currently Open: ").append(openCount).append("\n");
	    report.append("Currently Closed: ").append(totalRestaurants - openCount).append("\n");
	    
	    MessageBox.Info("Basic Report", report.toString());
	}

}
