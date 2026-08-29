package HW3.Menus.AdminManagment;

import HW3.DeliveryDataBase;
import HW3.DataObjects.Customer;
import HW3.DataObjects.Order;
import HW3.DataObjects.Order.OrderStatus;
import HW3.DeliveryDataBase.CodedType;
import HW3.Exceptions.TargetObjectAlreadyExistException;
import HW3.Exceptions.TargetObjectDoesntExistException;
import HW3.Menus.Tables;
import HW3.Menus.UIBase;
import HW3.Utils.DataChecker;
import HW3.Utils.DataSelector;
import HW3.Utils.InputManager;
import HW3.Utils.MenuManager;
import HW3.Utils.MessageBox;
import HW3.Utils.UIHelper;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class CustomerManagment extends UIBase {
	
	public CustomerManagment(DeliveryDataBase deliveryDataBase) {
		super(deliveryDataBase);
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
                () -> Tables.customer(deliveryDataBase.getCustomers())
        );

        Button searchCustomer = UIHelper.createButton(
                "Search Customer by Code",
                () -> MessageBox.Info(DataSelector.selectCustomer())
        );

        Button addCustomer = UIHelper.createButton("Add New Customer", e -> {
                	int code;
					try {
						code = deliveryDataBase.generateCode(CodedType.Customer);
					} catch (TargetObjectDoesntExistException e1) {
						MessageBox.error(e1);
						return;
					}

                	InputManager.createCustomer(code, customer -> {
                	    if (customer != null) {
                	    	try {
	                	        deliveryDataBase.add(customer); 
	                	        MessageBox.Info("The customer code is " + code);
	                	    } catch (TargetObjectAlreadyExistException ex) {
	                	        MessageBox.error(ex);
	                	    }
                	    }
                	    MenuManager.goBack();
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
                	if(customer != null)
	                	Tables.order(deliveryDataBase.getOrdersOfCustomer(customer));
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
                	if(customer != null)
                		Tables.restaurant(deliveryDataBase.getRestaurantasBuyCustomer(customer.getCode()));
                }
        );

        Button showPremiumRestaurants = UIHelper.createButton(
                "Show Ordered Premium Restaurants",
                e -> {
                	Customer customer = DataSelector.selectCustomer();
                	if(customer != null)
                		Tables.restaurant(deliveryDataBase.getPremiumRestaurantsByCustomer(customer));
                }

        );

        Button logout = UIHelper.createButton(
                "Back",
                MenuManager::goBack
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
        
        MenuManager.goTo(new Scene(root, 500, 400));
    }


    private void updateCustomer() {
    	Customer customer = DataSelector.selectCustomer();
    	if(customer == null) return;
    	
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
    	if(!deliveryDataBase.getOrders().stream().anyMatch(o -> o.getStatus() != OrderStatus.Delivered)) {
    		MessageBox.Info(null, "No cancelable orders found");
    		MenuManager.goBack();
    		return;
    	}
    	
    	Order order = DataSelector.dataFilter(DataSelector::selectOrder, o -> o.getStatus() != OrderStatus.Delivered
    		, "Select order that hasn't been delivrd");
		if(order == null) return;
		
		try {
			deliveryDataBase.removeOrder(order.getCode());
		} catch (Exception e) {
			MessageBox.error(e);
		}
    }


}
