package HW2;

import java.util.ArrayList;

import HW2.DataObjects.*;
import HW2.Utils.*;

import HW2.DataObjects.Order.OrderStatus;
import HW2.DeliveryDataBase.CodedType;
import HW2.Utils.InputManager.NumberSign;

public class MenuManager {
	// deliveryDataBase
	public final DeliveryDataBase deliveryDataBase;
	
	// stop values for int & String
	private final int BACK_INT = InputManager.BACK_INT;
	private final String BACK_STR = InputManager.BACK_STR;

	
	public MenuManager(DeliveryDataBase deliveryDataBase) {
		this.deliveryDataBase = deliveryDataBase;
		DataSelector.setDeliveryDataBase(deliveryDataBase);
		InputManager.setDeliveryDataBase(deliveryDataBase);
	}

	// the admin menu
	private void adminMenu() {
		System.out.println("Admin menu");
		
		String userName = InputManager.inputString("Enter user name", true);
		if(userName.equalsIgnoreCase(BACK_STR)) return;
		int password = InputManager.inputInt("Enter the password");
		if(password == BACK_INT) return;
		while(!deliveryDataBase.logIntoAdmin(userName, password)) {
			System.out.println("No matching admin found");
			userName = InputManager.inputString("Enter user name", true);
			if(userName.equalsIgnoreCase(BACK_STR)) return;
			password = InputManager.inputInt("Enter the password");
			if(password == BACK_INT) return;
		}

		while (true) {
			switch (InputManager.inputInt(
					"Enter command \n"
					+ "1-add cunsumer, \n"
					+ "2-add restaurant manager, \n"
					+ "3-add restaurant, \n"
					+ "4-add rider, \n"
					+ "5-assign a manager to a restaurant, \n"
					+ "6-assign a rider to a delivery \n"
					+ "7-show all Orders \n"
					+ "8-show customer with most orders \n"
					+ "9-show rider with most deliverd orders \n"
					+ "10-update restaurant status \n"
					+ "else-exit \n"
					+ ": ", false)) {
			case 1:
				int code = deliveryDataBase.generateCode(CodedType.Customer);
				if(deliveryDataBase.addCustomer(InputManager.creatCustomer(code)))
					System.out.println("The customer code is " + code);
				break;

			case 2:
				int admCode = deliveryDataBase.generateCode(CodedType.RestAdmin);
				if( deliveryDataBase.addRestAdmine(InputManager.createRestAdmin(admCode)))
					System.out.println("The restaurant manager code is " + admCode);
				break;

			case 3:
				int restCode = deliveryDataBase.generateCode(CodedType.Restaurant);
				switch (InputManager.inputInt(
						"Enter command: \n"
						+ "1-add restaurant, \n"
						+ "2-add fast food restaurant, \n"
						+ "3-add premium restaurant, \n"
						+ "else-go back \n"
						+ ": ", false)) {
				case 1:
					if( deliveryDataBase.addRestaurant(InputManager.createRestaurant(restCode)))
						System.out.println("The restorant code is " + restCode);
					break;
				case 2:
					if( deliveryDataBase.addRestaurant(InputManager.createFastFoodRestaurant(restCode)))
						System.out.println("The restorant code is " + restCode);
					break;
				case 3:
					if( deliveryDataBase.addRestaurant(InputManager.createPremiumRestaurant(restCode)))
						System.out.println("The restorant code is " + restCode);
					break;

				default:
					break;
				}
				break;

			case 4:
				deliveryDataBase.addRiders(InputManager.createRider());
				break;

			case 5:
				DataOutput.showCoded(deliveryDataBase.getRestAdmins());
				RestAdmin restAdmin = DataSelector.selectRestAdmin();
				if(restAdmin == null) break;

				DataOutput.showCoded(deliveryDataBase.getRestaurants());
				Restaurant restaurant = DataSelector.selectRestaurant();
				if(restaurant == null) break; 
				deliveryDataBase.addRestToAdmin(restAdmin.getCode(), restaurant.getCode());
				break;

			case 6:
				if(!DataOutput.showAvailableRiders(deliveryDataBase.getRiders()))
				{
					System.out.println("No aveilable riders");
					break;
				}
				Rider rider = DataSelector.selectAveilableRider();
				if(rider == null) break;
				
				DataOutput.showCoded(deliveryDataBase.getOrders());
				Order order = DataSelector.selectCreatedOrder();
				if(order == null) break;

				deliveryDataBase.addOrderToRider(rider.getId(), order.getCode());
				break;
				
			case 7:
				if(!DataOutput.showCoded(deliveryDataBase.getOrders()))
					System.out.println("No orders found");
				break;
				
			case 8:
				Customer customer = deliveryDataBase.getCustomerWithMostOrders();
				if(customer != null)
					System.out.println("The customer with most orders is: " + customer.getName() + " " + customer.getLastName() + 
							" with code: " + customer.getCode() + " and number of orders: " + deliveryDataBase.getNumberOfOrdersForCustomer(customer.getCode()));
				
				else System.out.println("No customers found");
				break;
				
				
			case 9:
				Rider riderWithMostOrders = deliveryDataBase.getRiderWithMostDeliverdOrders();
				if(riderWithMostOrders != null)
					System.out.println("The rider with most deliverd orders is: " + riderWithMostOrders.getName() + " " + riderWithMostOrders.getLastName() + 
							" with id: " + riderWithMostOrders.getId() + " and number of deliverd orders: " + riderWithMostOrders.getDeliverdOrders().size());
				else System.out.println("No riders found");

				break;
				
			case 10:
				DataOutput.showCoded(deliveryDataBase.getRestaurants());
				Restaurant rest = DataSelector.selectRestaurant();
				
				if(rest != null) {
					if (InputManager.inputBool("The restaurant is currently " +
							(rest.isOpen() ? "open" : "closed") + ". Do you want to change its status?", false)) {
						if(deliveryDataBase.changeRestaurantStatus(rest.getCode()))
							System.out.println("Restaurant " + rest.getName() + " is now " + (rest.isOpen() ? "open" : "closed"));
					}
				}
				break;

			default: return;
			}
		}
	}
	
	// the restaurant admin menu
	private void resAdminMenu() {
		System.out.println("ResAdmin menu");
		
		RestAdmin restAdmin;
		String userName =InputManager.inputString("Enter user name", true);
		if(userName.equalsIgnoreCase(BACK_STR)) return;
		int password = InputManager.inputInt("Enter the password");
		if(password== BACK_INT) return;
		while ((restAdmin = deliveryDataBase.tryGetRestAdmin(userName, password)) == null) {
			userName =InputManager.inputString("Enter user name", true);
			if(userName.equalsIgnoreCase(BACK_STR)) return;
			password = InputManager.inputInt("Enter the password");
			if(password == BACK_INT) return;
		}
		
		while (true) {
			switch (InputManager.inputInt(
					"Enter command \n"
					+ "1-add cunsumer, \n"
					+ "2-add order, \n"
					+ "3-add rider, \n"
					+ "4-assign a rider to a delivery, \n"
					+ "5-show all orders of restaurant, \n"
					+ "6-show all open restaurants by kitchen type, \n"
					+ "else-exit \n"
					+ ": ", false)) {
			case 1:
				int code = deliveryDataBase.generateCode(CodedType.Customer);
				if(deliveryDataBase.addCustomer(InputManager.creatCustomer(code)))
					System.out.println("The customer code is " + code);
				break;

			case 2:
				if(!DataOutput.showCoded(restAdmin.getOpenRestaurants())) {
					System.out.println("The current RestAdmin has no open restaurants");
					break;
				}
				
				Restaurant restaurant = DataSelector.selectOpenRestaurant();
				if(restaurant == null) break;
				while(!restAdmin.containsRestaurant(restaurant.getCode())) {
					System.out.println("The current restaurant admin isnt the manager of the selected restaurant");
					restaurant = DataSelector.selectOpenRestaurant();
					if(restaurant == null) break;
				}
				if(restaurant == null) break;
				
				DataOutput.showCoded(deliveryDataBase.getCustomers());
				Customer customer = DataSelector.selectCustomer();
				if(customer == null) break;

				Double basePrice = InputManager.inputDouble("Enter base price (not negative)", NumberSign.NOT_NEGATIVE);
				if(basePrice == BACK_INT) break;

				int orderCode = deliveryDataBase.addOrder(restaurant.getCode(), customer.getCode(), basePrice, InputManager.createDate());
				if(orderCode != -1)
					System.out.println("The order code is " + orderCode);
				else System.out.println("Customer cant afford the order");
				break;

			case 3:
				deliveryDataBase.addRiders(InputManager.createRider());
				break;

			case 4:
				ArrayList<Order> orders = deliveryDataBase.filterOrdersBuyStatus(deliveryDataBase.getOrdersOfRestAdmin(restAdmin.getCode()), OrderStatus.Created);
				
				if(orders.isEmpty()) {
					System.out.println("No orders found");
					break;
				}
				
				if(!DataOutput.showAvailableRiders(deliveryDataBase.getRiders())) {
					System.out.println("No aveilable riders");
					break;
				}
				
				Rider rider = DataSelector.selectAveilableRider();
				if(rider == null) break;
				
				DataOutput.showCoded(orders);
				Order order = DataSelector.selectCreatedOrder();
				if(order == null) break;
				while(!restAdmin.containsRestaurant(order.getRestaurantCode())) {
					System.out.println("The selected order isnt from a restorant that controlled by the current manager");
					order = DataSelector.selectCreatedOrder();
					if(order == null) break;
				}
				if(order == null) break;
				
				deliveryDataBase.addOrderToRider(rider.getId(), order.getCode(), restAdmin.getCode());
				break;
				
			case 5:
				if(!DataOutput.showCoded(restAdmin.getRestaurants())) {
					System.out.println("The current RestAdmin has no restaurants");
					break;
				}
				
				Restaurant rest = DataSelector.selectRestaurant();
				if(rest == null) break;
				while(!restAdmin.containsRestaurant(rest.getCode())) {
					System.out.println("The current restaurant admin isnt the manager of the selected restaurant");
					rest = DataSelector.selectRestaurant();
					if(rest == null) break;
				}
				if(rest == null) break;
				
				if(! DataOutput.showCoded(deliveryDataBase.getOrdersByuRestaurant(rest.getCode())))
					System.out.println("The selected restaurant has no orders");

				break;
			case 6:
				if(restAdmin.getRestaurants().isEmpty()) {
					System.out.println("The current RestAdmin has no restaurants");
					break;
				}
				String kitchenType = InputManager.inputString("Enter kitchen type", false);
			    if (kitchenType.equalsIgnoreCase(BACK_STR)) break;
				
			    ArrayList<Restaurant> restaurants = deliveryDataBase.getOpenRestaurants(kitchenType);
			    ArrayList<Restaurant> filtered = new ArrayList<>();
			    for (Restaurant r : restaurants) 
			        if (restAdmin.containsRestaurant(r.getCode())) 
			        	filtered.add(r);
			    
				
				if (!DataOutput.showCoded(filtered))
					System.out.println("No matching restaurants found");
				
				break;

			default: return;
			}
		}

	}

	// the rider menu
	private void riderMenu() {
		System.out.println("Rider menu");
		Rider rider = DataSelector.selectRider();
		if(rider == null) return;

		while (true) {
			switch (InputManager.inputInt("Enter command \n"
					+ "1-update dilivery status, \n"
					+ "2-show all active orders, \n"
					+ "3-show all orders, \n"
					+ "else-exit \n"
					+ ": ", false)) {
			case 1:
				if(rider.isAvailable()) {
					System.out.println("No order found");
					break;
				}

				Order order = rider.getCurrentOrder();
				
				System.out.println("The status of the current order is " + order.getOrderStatus());
				boolean isUpdate = InputManager.inputBool("Do you want to update the status of the order?(created->on the way->delivered)", false);
				if(!isUpdate) break;
				
				Date date = order.getOrderStatus() == OrderStatus.Created ? null : InputManager.createDateAfterDate(order.getOrderingDate());
				deliveryDataBase.updateDeliveryStatus(rider.getId(), date);
				break;

			case 2:
				if(!DataOutput.showCoded(rider.getCurrentOrder()))
					System.out.println("No oredr found");
				break;
				
			case 3:
				if(!DataOutput.showCoded(rider.getDeliverdOrders()) && !DataOutput.showCoded(rider.getCurrentOrder())) 
					System.out.println("No order found");
				break;

			default: return;
			}

		}
	}


	// the customer menu
	private void customerMenu() {
		System.out.println("Customer menu");
		Customer customer = DataSelector.selectCustomer();
		if(customer == null) return;
		
		while (true) {
			switch (InputManager.inputInt(
					"Enter command \n"
					+ "1-Make an order, \n"
					+ "2-show all orders, \n"
					+ "3-update personal information, \n"
					+ "4-show all restaurants that was orderd from, \n"
					+ "5-show all premium restaurants that was orderd from, \n"
					+ "6-show balance, \n"
					+ "7-add mony to balance, \n"
					+ "8-remove money from balance, \n"
					+ "9-cancel order, \n"
					+ "else-exit \n"
					+ ": ", false)) {
			case 1:
				if(customer.getCreditBalance() < 0) {
					System.out.println("You cant buy anything");
					break;
				}
				if (!DataOutput.showCoded(deliveryDataBase.getOpenRestaurants())) {
					System.out.println("No open restaurants found");
					break;
				}
				
				Restaurant restaurant = DataSelector.selectOpenRestaurant();
				if(restaurant == null) break;

				double basePrice = InputManager.inputDouble("Enter the base fee (not negative)", NumberSign.NOT_NEGATIVE);
				if(basePrice == BACK_INT) break;

				int orderCode = deliveryDataBase.addOrder(restaurant.getCode(), customer.getCode(), basePrice, InputManager.createDate());
				if(orderCode != -1) 
					System.out.println("The order code is " + orderCode);
				else System.out.println("You cant afford the order");
				break;

			case 2:
				if(!DataOutput.showCoded(deliveryDataBase.getOrdersOfCustomer(customer)))
					System.out.println("No orders found");

				break;

			case 3:
				if(InputManager.inputBool("Do you want to change your adress?", false)){
					if(InputManager.inputBool("Do you want to chage the town?")) {
						String townString = InputManager.inputString("Enter new town", true);
						if(!townString.equalsIgnoreCase(BACK_STR))
							customer.setTown(townString);
					}

					if(InputManager.inputBool("Do you want to chage the street?", false)) {
						String streetString = InputManager.inputString("Enter new street", false);
						if(!streetString.equalsIgnoreCase(BACK_STR))
							customer.setStreet(streetString);
					}

					if(InputManager.inputBool("Do you want to chage the ZIP code?", false)) {
						String zip;
					    while(!DataChecker.isValidZipCode(zip = InputManager.inputString("Enter new ZIP code", false)) && !zip.equalsIgnoreCase(BACK_STR))
					    	System.out.println("Zip code must be not newgative 5-7 digits");
					    if(!zip.equalsIgnoreCase(BACK_STR))
					    	customer.setZipCode(zip);
					}
				}
				if(InputManager.inputBool("Do you want to chage your phone number?", false)) {
					String phone;
					while(!DataChecker.isValidPhoneNumber((phone = InputManager.inputString("Enter the new phone number (IL)", false))) &&
							!phone.equalsIgnoreCase(BACK_STR))
						System.out.println("Not valid phone number");
					if(!phone.equalsIgnoreCase(BACK_STR))
						customer.setPhoneNumber(phone);
				}
				break;
				
			case 4:
				if (!DataOutput.showCoded(deliveryDataBase.getRestaurantasBuyCustomer(customer.getCode())))
					System.out.println("No orders found");
				
				break;
				
			case 5:
				if (!DataOutput.showCoded(deliveryDataBase.getPremiumRestaurantsByCustomer(customer)))
					System.out.println("No orders found");
				
				break;
				
			case 6:
				System.out.println("Your balance is " + customer.getCreditBalance());
				break;
				
			case 7:
				double add = InputManager.inputDouble("Enter money (not negative)", NumberSign.NOT_NEGATIVE);
				if(add == BACK_INT) break;
				double newAdd = customer.getCreditBalance() + add;
				if(!deliveryDataBase.setBalanceToCustomer(customer.getCode(), newAdd))
					System.out.println("Not valide final balance " + newAdd);
				break;
				
			case 8:
				double sub = InputManager.inputDouble("Enter money (not negative)", NumberSign.NOT_NEGATIVE);
				if(sub == BACK_INT) break;
				double newSub = customer.getCreditBalance() - sub;
				if(!deliveryDataBase.setBalanceToCustomer(customer.getCode(), newSub))
					System.out.println("Not valide final balance " + newSub);
				break;
				
			case 9:
				if(!DataOutput.showCoded(deliveryDataBase.getOrdersOfCustomer(customer))) {
					System.out.println("No orders found");
					break;
				}
				
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
				
				deliveryDataBase.removeOrder(order.getCode());
				
				break;

			default: return;
			}

		}

	}

	
	// the main menu
	public void mainMenu() {
		while (true) {
			System.out.println("Main menu");
			switch (InputManager.inputInt(
					"Choose user type \n"
					+ "1-Admin, \n"
					+ "2-RestAdmin, \n"
					+ "3-Rider, \n"
					+ "4-Customer, \n"
					+ "else-exit \n"
					+ ": ", false)) {

			case 1:
				adminMenu();
				break;

			case 2:
				resAdminMenu();
				break;

			case 3:
				riderMenu();
				break;

			case 4:
				customerMenu();
				break;

			default: return;
			}
		}
	}
	
}
