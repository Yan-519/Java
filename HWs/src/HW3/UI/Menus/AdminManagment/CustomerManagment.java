package HW3.UI.Menus.AdminManagment;

import java.util.ArrayList;

import HW3.DeliveryDataBase;
import HW3.DataObjects.Customer;
import HW3.DataObjects.Order;
import HW3.DataObjects.Order.OrderStatus;
import HW3.DeliveryDataBase.CodedType;
import HW3.Exceptions.CodedNotFoundException;
import HW3.Exceptions.RiderNotFoundException;
import HW3.Exceptions.TargetObjectAlreadyExistException;
import HW3.Exceptions.TargetObjectDoesntExistException;
import HW3.UI.MessageBox;
import HW3.UI.UIHelper;
import HW3.UI.Menus.UIBase;
import HW3.Utils.DataChecker;
import HW3.Utils.DataSelector;
import HW3.Utils.InputManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerManagment extends UIBase {

	public CustomerManagment(Stage stage, DeliveryDataBase deliveryDataBase, Runnable backF) {
		super(stage, deliveryDataBase, backF);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Init() {
		Main();

	}

	@Override
	protected void Main() {
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
                () -> UIHelper.showList(stage, deliveryDataBase.getCustomers(), this::Main)
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
                	    Main();
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
                		Main();
                	
                	else UIHelper.showList(stage, deliveryDataBase.getOrdersOfCustomer(customer), this::Main);
                }
        );

        Button cancelOrder = UIHelper.createButton(
                "Cancel Order",
                this::cancelOrder
        );

        Button showRestaurants = UIHelper.createButton(
                "Show Ordered Restaurants",
                e -> {
                	Customer customer = DataSelector.selectCustomer();
                	if(customer == null)
                		Main();
                	
                	else UIHelper.showList(stage, deliveryDataBase.getRestaurantasBuyCustomer(customer.getCode()), this::Main);
                }
        );

        Button showPremiumRestaurants = UIHelper.createButton(
                "Show Ordered Premium Restaurants",
                e -> {
                	Customer customer = DataSelector.selectCustomer();
                	if(customer == null)
                		Main();
                	
                	else UIHelper.showList(stage, deliveryDataBase.getPremiumRestaurantsByCustomer(customer), this::Main);
                }

        );

        Button logout = UIHelper.createButton(
                "Back",
                backF
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
    		Main();
    		return;
    	}
    	
    	ArrayList<Order> orders = deliveryDataBase.getOrdersOfCustomer(customer);
    	if(orders.isEmpty())
    	{
    		MessageBox.Info("selected customer has no orders");
    		Main();
    		return;
    	}
    	
    	UIHelper.showList(stage, orders, this::Main);
    	
    	Order order = DataSelector.dataFilter(DataSelector::selectOrder, o ->
    		o.getClientCode() == customer.getCode() && o.getOrderStatus() != OrderStatus.Delivered
    		, "Select your order that hasn't been delivrd");
		if(order == null) return;
		
		try {
			deliveryDataBase.removeOrder(order.getCode());
		} catch (CodedNotFoundException | RiderNotFoundException | TargetObjectDoesntExistException e) {
			MessageBox.error(e);
		}
    }


}
