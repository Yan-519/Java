package HW2;

import java.util.ArrayList;

import HW2.DeliveryDataBase.CodedType;
import HW2.InputManager.NumberSign;
import HW2.Order.DeliveryStatus;

public class MenuManager {
	// deliveryDataBase
	public final DeliveryDataBase deliveryDataBase;
	
	// stop values for int & String
	private final int BACK_INT = InputManager.BACK_INT;
	private final String BACK_STR = InputManager.BACK_STR;

	
	public MenuManager(DeliveryDataBase deliveryDataBase) {
		this.deliveryDataBase = deliveryDataBase;
		DataSelector.setDeliveryDataBase(deliveryDataBase);
	}

	public DeliveryDataBase getDeliveryDataBase() {
		return deliveryDataBase;
	}

	// show Coded objects
	private <T extends Coded> void showCoded(ArrayList<T> codeds) {
		if (codeds.isEmpty())
	        return;

	    System.out.println(codeds.getFirst().getClass().getSimpleName() + " info:");

	    for (Coded coded : codeds) {
	        if (coded instanceof RestAdmin restAdmin) {
	            System.out.println(restAdmin.getUserName() + ": " + restAdmin.getCode());

	        } else if (coded instanceof Restaurant restaurant) {
	            System.out.println(restaurant.getName() + ": " + restaurant.getCode());
	            
	        } else if (coded instanceof Customer customer) {
	            System.out.println(customer.getName() + " " + customer.getLastName() + ": " + customer.getCode());

	        } else if (coded instanceof Order order) {
	            if (order.getDeliveryStatus() == DeliveryStatus.Created)
	                System.out.println(order.getCode());
	        }
	        
	    }	
	}
	
	// show all Riders
//	private void showRiders() {
//		System.out.println("Riders Id:");
//		for(Rider rider: deliveryDataBase.getRiders())
//				System.out.println(rider.getName() + " " + rider.getLastName() + ": " + rider.getId());
//	}
	
	// show all aveilable Riders
	private void showAveilableRiders() {
		for(Rider rider_: deliveryDataBase.getRiders()) {
			if(rider_.isAvailable()) {
				System.out.println("Riders Id:");
				for(Rider rider: deliveryDataBase.getRiders())
					if(rider.isAvailable())
						System.out.println(rider.getName() + " " + rider.getLastName() + ": " + rider.getId());
				return;
			}
		}
	}


	
	// the admin menu
	private void adminMenu() {
		System.out.println("Admin menu");
		
		String userName = InputManager.inputString("Enter name", true);
		if(userName.equalsIgnoreCase(BACK_STR)) return;
		int password = InputManager.inputInt("Enter the password");
		if(password == BACK_INT) return;
		while(!deliveryDataBase.logIntoAdmin(userName, password)) {
			System.out.println("No matching admin found");
			userName = InputManager.inputString("Enter name", true);
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
					+ "9-show rider with most orders \n"
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
				showCoded(deliveryDataBase.getRestAdmins());
				RestAdmin restAdmin = DataSelector.selectRestAdmin();
				if(restAdmin == null) break;

				showCoded(deliveryDataBase.getRestaurants());
				Restaurant restaurant = DataSelector.selectRestaurant();
				if(restaurant == null) break; 
				deliveryDataBase.addRestToAdmin(restAdmin.getCode(), restaurant.getCode());
				break;

			case 6:
				showAveilableRiders();
				Rider rider = DataSelector.selectAveilableRider();
				if(rider == null) break;
				
				showCoded(deliveryDataBase.getOrders());
				Order order = DataSelector.selectCreatedOrder();
				if(order == null) break;

				deliveryDataBase.addOrderToRider(rider.getId(), order.getCode());
				break;
				
			case 7:
				for(Order o : deliveryDataBase.getOrders())
					System.out.println(o);
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
					System.out.println("The rider with most orders is: " + riderWithMostOrders.getName() + " " + riderWithMostOrders.getLastName() + 
							" with id: " + riderWithMostOrders.getId() + " and number of orders: " + riderWithMostOrders.getOrders().size());
				else System.out.println("No riders found");

				break;
				
			case 10:
				showCoded(deliveryDataBase.getRestaurants());
				Restaurant restaurant2 = DataSelector.selectRestaurant();
				
				if(restaurant2 != null) {
					Boolean status = InputManager.inputBool("The restaurant is currently " +
							(restaurant2.isOpen() ? "open" : "closed") + ". Do you want to change its status?", false);
					if(status == null) {
						System.out.println("No changes made to the restaurant status.");
						break;
					}
					restaurant2.setOpen(!restaurant2.isOpen());
					System.out.println("Restaurant " + restaurant2.getName() + " is now " + (restaurant2.isOpen() ? "open" : "closed"));
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
		String userName =InputManager.inputString("Enter name", true);
		if(userName.equalsIgnoreCase(BACK_STR)) return;
		int password = InputManager.inputInt("Enter the password");
		if(password== BACK_INT) return;
		while ((restAdmin = deliveryDataBase.tryGetRestAdmin(userName, password)) == null) {
			userName =InputManager.inputString("Enter name", true);
			if(userName.equalsIgnoreCase(BACK_STR)) return;
			password = InputManager.inputInt("Enter the password");
			if(password== BACK_INT) return;
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
				if(restAdmin.getRestaurants().size() == 0) {
					System.out.println("The current RestAdmin has no restaurants");
					break;
				}
				
				showCoded(restAdmin.getRestaurants());
				
				Restaurant restaurant = DataSelector.selectOpenRestaurant();
				if(restaurant == null) break;
				while(deliveryDataBase.tryGetRestAdminRestaurant(restAdmin.getCode(), restaurant.getCode()) == null) {
					System.out.println("The current restaurant admin isnt the manager of the selected restaurant");
					restaurant = DataSelector.selectOpenRestaurant();
					if(restaurant == null) break;
				}
				if(restaurant == null) break;
				
				showCoded(deliveryDataBase.getCustomers());
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
				ArrayList<Order> orders = deliveryDataBase.getOrdersOfRestAdmin(restAdmin.getCode());
				
				boolean ifFound = false;
				for(Order order : orders) {
					if(order.getDeliveryStatus() == DeliveryStatus.Created) {
						ifFound = true;
						break;
					}
				}
				if(!ifFound) {
					System.out.println("No orders found");
					break;
				}

				showAveilableRiders();
				Rider rider = DataSelector.selectAveilableRider();
				if(rider == null) break;
				
				System.out.println("Orders code:");
				for(Order order : orders) 
					if(order.getDeliveryStatus() == DeliveryStatus.Created) 
						System.out.println(order.getCode());
				
				Order order = DataSelector.selectCreatedOrder();
				if(order == null) break;
				while(restAdmin.tryGetRestaurant(order.getRestaurantCode()) == null) {
					System.out.println("The selected order isnt from a restorant that controlled by the current manager");
					order = DataSelector.selectCreatedOrder();
					if(order == null) break;
				}
				if(order == null) break;
				
				deliveryDataBase.addOrderToRider(rider.getId(), order.getCode(), restAdmin.getCode());
				break;
				
			case 5:
				if(restAdmin.getRestaurants().size() == 0) {
					System.out.println("The current RestAdmin has no restaurants");
					break;
				}
				
				System.out.println("Aveilable restaurants:");
				for(Restaurant r : restAdmin.getRestaurants())
						System.out.println(r.getName() +": "+ r.getCode());
				
				Restaurant rest = DataSelector.selectRestaurant();
				if(rest == null) break;
				while(deliveryDataBase.tryGetRestAdminRestaurant(restAdmin.getCode(), rest.getCode()) == null) {
					System.out.println("The current restaurant admin isnt the manager of the selected restaurant");
					rest = DataSelector.selectRestaurant();
					if(rest == null) break;
				}
				if(rest == null) break;
				
				for(Order o : deliveryDataBase.getOrders())
					if(o.getRestaurantCode() == rest.getCode())
						System.out.println(o.getCode());

				break;
			case 6:
				String kitchenType = InputManager.inputString("Enter kitchen type", false);
			    if (kitchenType.equalsIgnoreCase(BACK_STR)) break;
				
				ArrayList<Restaurant> restaurants = deliveryDataBase.getOpenRestaurants(kitchenType);
				if (restaurants.size() == 0)
					System.out.println("No matching restaurants found");
				else
					for(Restaurant restaurant2 : restaurants)
						System.out.println(restaurant2.getName() +": "+ restaurant2.getCode());
				
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
				boolean isFound = false;
				for(Order order : rider.getOrders()) {
					if(order.getDeliveryStatus() != DeliveryStatus.Delivered) {
						isFound = true;
						break;
					}
				}
				if(!isFound) {
					System.out.println("No order found");
					break;
				}
				
				System.out.println("Orders code:");
				for(Order order : rider.getOrders())
					if(order.getDeliveryStatus() != DeliveryStatus.Delivered)
						System.out.println(order.getCode());
				
				Order order = DataSelector.selectOrder();
				if(order == null) break;
				while(!rider.isContainsOrder(order.getCode()) || order.getDeliveryStatus() == DeliveryStatus.Delivered){
					if(!rider.isContainsOrder(order.getCode()))
						System.out.println("The selected order isnt under the current rider control");
					else System.out.println("The order is already delivered");
					order = DataSelector.selectOrder();
					if(order == null) break;
				}
				if(order == null) break;
				
				Boolean isUpdate = InputManager.inputBool("Do you want to update the status of the order?(created->on the way->delivered)");
				if(isUpdate == null || !isUpdate) break;
				
				if(order.getDeliveryStatus() == DeliveryStatus.Created) {
					order.changeeliveryStatus();
					rider.setAvailable(false);
				}
				else if(order.getDeliveryStatus() == DeliveryStatus.OnTheWay) {					
					Date aftreDate = InputManager.createDateAfterDate(order.getOrderingDate());
					if(aftreDate == null) break;

					order.changeeliveryStatus();
					order.setDeliveringDate(aftreDate);
					rider.setAvailable(true);
				}
				break;

			case 2:
				boolean isHave = false;
				for(Order ord: rider.getOrders()) {
					if(ord.getDeliveryStatus() != DeliveryStatus.Delivered) {
						System.out.println(ord);
						isHave = true;
					}
				}
				if(!isHave)
					System.out.println("No order found");
				
				break;
				
			case 3:
				if(rider.getOrders().size() == 0) 
					System.out.println("No order found");
				else showCoded(rider.getOrders());
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
				showCoded(deliveryDataBase.getRestaurants());
				
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
				ArrayList<Order> orders = deliveryDataBase.getOrdersOfCustomer(customer);
				showCoded(orders);
				if(orders.size() == 0)
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
						int zip = InputManager.inputInt("Enter new ZIP code (positive)", NumberSign.POSITIVE);
						if(zip != BACK_INT)
						customer.setZipCode(zip);
					}
				}
				if(InputManager.inputBool("Do you want to chage your phone number?", false)) {
					String phone;
					while(!Customer.isValidPhoneNumber((phone = InputManager.inputString("Enter the new phone number (IL)", false))) &&
							!phone.equalsIgnoreCase(BACK_STR));
					if(!phone.equalsIgnoreCase(BACK_STR))
						customer.setPhoneNumber(phone);
				}
				break;
				
			case 4:
				ArrayList<Restaurant> restaurants = deliveryDataBase.getRestaurantasBuyCustomer(customer.getCode());
				showCoded(restaurants);
				if (restaurants.size() == 0)
					System.out.println("No orders found");
				
				break;
				
			case 5:
				ArrayList<PremiumRestaurant> premiumRestaurants = deliveryDataBase.getPremiumRestaurantsByCustomer(customer);
				showCoded(premiumRestaurants);
				if (premiumRestaurants.size() == 0)
					System.out.println("No orders found");
				
				break;
				
			case 6:
				System.out.println("Your balance is " + customer.getCreditBalance());
				break;
				
			case 7:
				double add = InputManager.inputDouble("Enter money (not negative)", NumberSign.NOT_NEGATIVE);
				if(add == BACK_INT) break;
				if(!deliveryDataBase.setBalanceToCustomer(customer.getCode(), customer.getCreditBalance() + add))
					System.out.println("Not valide final balance " + customer.getCreditBalance() + add);
				break;
				
			case 8:
				double sub = InputManager.inputDouble("Enter money (not negative)", NumberSign.NOT_NEGATIVE);
				if(sub == BACK_INT) break;
				if(!deliveryDataBase.setBalanceToCustomer(customer.getCode(), customer.getCreditBalance() + sub))
					System.out.println("Not valide final balance " + customer.getCreditBalance() + sub);
				break;
				
			case 9:
				ArrayList<Order> orders2 = deliveryDataBase.getOrdersOfCustomer(customer);
				showCoded(orders2);
				if(orders2.size() == 0) {
					System.out.println("No orders found");
					break;
				}
				
				Order order = DataSelector.selectOrder();
				if(order == null) break;
				while( !orders2.contains(order) || order.getDeliveryStatus() == DeliveryStatus.Delivered) {
					if(order.getDeliveryStatus() == DeliveryStatus.Delivered)
						System.out.println("You can't cencel an order that has beed deliverd");
					order = DataSelector.selectOrder();
					if(order == null) break;
				}
				if(order == null) break;
				
				deliveryDataBase.removeOrder(order.getCode(), customer.getCode());
				
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
