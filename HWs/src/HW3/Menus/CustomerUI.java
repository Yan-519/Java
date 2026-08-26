package HW3.Menus;

import HW3.DeliveryDataBase;
import HW3.DataObjects.Customer;
import HW3.DataObjects.Restaurant;
import HW3.Exceptions.CodedNotFoundException;
import HW3.Exceptions.TargetObjectDoesntExistException;
import HW3.Utils.DataChecker;
import HW3.Utils.DataSelector;
import HW3.Utils.InputManager;
import HW3.Utils.MessageBox;
import HW3.Utils.MessageBox.NumberSign;
import HW3.Utils.UIHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerUI extends UIBase  {
	
	private Customer customer;

	public CustomerUI(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
		super(stage, deliveryDataBase, backF);
	}

	@Override
	public void Auth() {
        VBox root = UIHelper.createVRoot();

        Label title = new Label("Customer Login");

        TextField code = new TextField();
        code.setPromptText("Customer Code");

        Button loginButton = UIHelper.createButton("Login", e -> {
        	try {
        		customer = deliveryDataBase.getCustomer(Integer.valueOf(code.getText()));
				Main();
			} catch ( CodedNotFoundException | TargetObjectDoesntExistException e1) {
				MessageBox.error(e1);
			}catch (NumberFormatException ex) {
				MessageBox.error("Code must be a number", ex);
			}
        });
        Button backButton = UIHelper.createButton("Back", e -> backF.run());

        backButton.setOnAction( e -> backF.run());

        root.getChildren().addAll(
                title,
                code,
                loginButton,
                backButton
        );

    	stage.setScene(new Scene(root, 500, 400));
	}

	@Override
	protected void Main() {
	    BorderPane root = new BorderPane();

	    VBox header = UIHelper.createVRoot("Customer Management");
	    root.setTop(header);

	    GridPane grid = new GridPane();
	    grid.setHgap(20);
	    grid.setVgap(20);
	    grid.setPadding(new Insets(30));
	    grid.setAlignment(Pos.CENTER);

	    Button viewProfileButton = UIHelper.createButton("View Personal Details", () -> MessageBox.Info(customer));
	    Button newOrderButton = UIHelper.createButton("Place New Order", this::placeNewOrder);

	    Button viewOrdersButton = UIHelper.createButton("View Order History", 
	    		() -> Tables.order(stage, deliveryDataBase.getOrdersOfCustomer(customer), this::Main));
	    Button updateDetailsButton = UIHelper.createButton("Update Profile Info", this::updateProfileInfo);

	    Button depositMoneyButton = UIHelper.createButton("Deposit Funds", () -> {
				Double add = MessageBox.inputDOUB(null, "Enter money (not negative)", NumberSign.POSITIVE);
				if(add == null) return;
				try {
					deliveryDataBase.setBalanceToCustomer(customer.getCode(), customer.getBalance() + add);
				} catch (Exception e) {
					MessageBox.error(e);
				}

	    		});
	    Button withdrawMoneyButton = UIHelper.createButton("Withdraw Funds", () ->{
			Double sub = MessageBox.inputDOUB(null, "Enter money (not negative)", NumberSign.POSITIVE);
			if(sub == null) return;
			try {
				deliveryDataBase.setBalanceToCustomer(customer.getCode(), customer.getBalance() - sub);
			} catch (Exception e) {
				MessageBox.error(e);
			}
	    });

	    Button viewBalanceButton = UIHelper.createButton("Show Current Balance", 
	    		() -> MessageBox.Info("Current balance: " + customer.getBalance()));
	    Button visitedRestaurantsButton = UIHelper.createButton("Show Ordered Restaurants", 
	    		() -> Tables.restaurant(stage, deliveryDataBase.getRestaurantasBuyCustomer(customer.getCode()), this::Main));

	    Button luxuryRestaurantsButton = UIHelper.createButton("Show Ordered Luxury Restaurants", 
	    		() -> Tables.restaurant(stage, deliveryDataBase.getPremiumRestaurantsByCustomer(customer), this::Main));
	    Button searchRestaurantButton = UIHelper.createButton("Search Restaurant by Code", 
	    		() -> MessageBox.Info(DataSelector.selectRestaurant()));

	    Button backButton = UIHelper.createButton("Back", backF);

	    grid.add(viewProfileButton, 0, 0);
	    grid.add(newOrderButton, 1, 0);

	    grid.add(viewOrdersButton, 0, 1);
	    grid.add(updateDetailsButton, 1, 1);

	    grid.add(depositMoneyButton, 0, 2);
	    grid.add(withdrawMoneyButton, 1, 2);

	    grid.add(viewBalanceButton, 0, 3);
	    grid.add(visitedRestaurantsButton, 1, 3);

	    grid.add(luxuryRestaurantsButton, 0, 4);
	    grid.add(searchRestaurantButton, 1, 4);

	    grid.add(backButton, 0, 5, 2, 1);

	    root.setCenter(grid);

	    stage.setScene(new Scene(root, 700, 600));
	}


	private void placeNewOrder() {
		if (deliveryDataBase.getOpenRestaurants().isEmpty()) {
			MessageBox.Info("No open restaurants found");
			return;
		}
		
		Restaurant restaurant = DataSelector.dataFilter(DataSelector::selectRestaurant, r -> r.getIsOpen(), "The selected restaurant in colse");
	
		Double basePrice = MessageBox.inputDOUB(null, "Enter the base fee (not negative)", NumberSign.NOT_NEGATIVE);
		if(basePrice == null) return;
		
		InputManager.createDate(stage, d ->{
			if(d != null)
				try {
					MessageBox.Info("New code", deliveryDataBase.addOrder(restaurant.getCode(), customer.getCode(), basePrice, d));
				} catch (Exception e) {
					MessageBox.error(e);
				}
			
			Main();
		});

	}

	private void updateProfileInfo() {
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

}
