package HW1;

import java.util.Locale;

public class Main {

	private static final DeliverySystem deliverySystem = new DeliverySystem();

	public static final int BACK = -1;

	// the admin menu
	public static void adminMenu() {
		System.out.println("Admin menu");

		String userName = InputManager.inputString("Enter name", true);
		if(userName.equalsIgnoreCase(Integer.toString(BACK))) return;
		int password = InputManager.inputInt("Enter the password");
		if(password == BACK) return;
		while(deliverySystem.tryGetAdmin(userName, password) == null) {
			System.out.println("No matching admin found");
			userName = InputManager.inputString("Enter name", true);
			if(userName.equalsIgnoreCase(Integer.toString(BACK))) return;
			password = InputManager.inputInt("Enter the password");
			if(password == BACK) return;
		}

		while (true) {
			switch (InputManager.inputInt(
					"Enter command \n"
					+ "1-add cunsumer, \n"
					+ "2-add restaurant manager, \n"
					+ "3-assign a manager to a restaurant, \n"
					+ "4-add restaurant, \n"
					+ "5-add rider, \n"
					+ "6-assign a rider to a delivery \n"
					+ "else-exit \n"
					+ ": ", false)) {
			case 1:
				int code = deliverySystem.generateCustomerCode();
				if(deliverySystem.addCustomer(InputManager.creatCustomer(code)))
					System.out.println("The customer code is " + code);
				break;

			case 2:
				int admCode = deliverySystem.generateAdminCode();
				if( deliverySystem.addAdmine(InputManager.createRestAdmin(admCode)))
					System.out.println("The restaurant manager code is " + admCode);
				break;

			case 3:
				int adminCode = InputManager.inputInt("Enter admin code");
				if(adminCode == BACK) break;
				int restCode = InputManager.inputInt("Enter restaurant code");
				if(restCode == BACK) break;
				while(!deliverySystem.addRestToAdmin(adminCode, restCode)) {
					System.out.println("Ether the admin or the restaurant doesn't exist");
					adminCode = InputManager.inputInt("Enter admin code");
					if(adminCode == BACK) break;
					restCode = InputManager.inputInt("Enter restaurant code");
					if(restCode == BACK) break;
				}
				break;

			case 4:
				switch (InputManager.inputInt(
						"Enter command: \n"
						+ "1-add restaurant, \n"
						+ "2-add fast food restaurant, \n"
						+ "3-add premium restaurant, \n"
						+ "else-go back \n"
						+ ": ", false)) {
				case 1:
					int normalCode = deliverySystem.generateRestaurantCode();
					if( deliverySystem.addRestaurant(InputManager.createRestaurant(normalCode)))
					System.out.println("The restorant code is " + normalCode);
					break;
				case 2:
					int fastCode = deliverySystem.generateRestaurantCode();
					if( deliverySystem.addRestaurant(InputManager.createFastFoodRestaurant(fastCode)))
					System.out.println("The restorant code is " + fastCode);
					break;
				case 3:
					int premiumCode = deliverySystem.generateRestaurantCode();
					if( deliverySystem.addRestaurant(InputManager.createPremiumRestaurant(premiumCode)))
					System.out.println("The restorant code is " + premiumCode);
					break;

				default:
					break;
				}
				break;

			case 5:
				deliverySystem.addRiders(InputManager.createRider());
				break;

			case 6:
				Rider rider;
				String riderId = InputManager.inputString("Enter rider id", false);
				if(riderId.equalsIgnoreCase(Integer.toString(BACK))) break;
				int orderCode = InputManager.inputInt("Enter order code");
				if(orderCode ==  BACK) break;
				while(!deliverySystem.addOrderToRider(riderId, orderCode)) {
					if((rider = deliverySystem.tryGetRider(riderId)) != null && rider.isAvailable())
						System.out.println("Ether the rider doesn't exist or the order doesn't exist");
					else System.out.println("Rider isnt aveilable");
					riderId = InputManager.inputString("Enter rider id", false);
					if(riderId.equalsIgnoreCase(Integer.toString(BACK))) break;
					orderCode = InputManager.inputInt("Enter order code");
					if(orderCode ==  BACK) break;
				}
				break;

			default: return;
			}
		}
	}

	// the restaurant admin menu
	public static void resAdminMenu() {
		System.out.println("ResAdmin menu");
		RestAdmin restAdmin;
		String userName =InputManager.inputString("Enter name", true);
		if(userName.equalsIgnoreCase(Integer.toString(BACK))) return;
		int password = InputManager.inputInt("Enter the password");
		if(password== BACK) return;
		while ((restAdmin = deliverySystem.tryGetRestAdmin(userName, password)) == null) {
			userName =InputManager.inputString("Enter name", true);
			if(userName.equalsIgnoreCase(Integer.toString(BACK))) return;
			password = InputManager.inputInt("Enter the password");
			if(password== BACK) return;
		}
		
		int restAdminCode = restAdmin.getCode();
		while (true) {
			switch (InputManager.inputInt(
					"Enter command \n"
					+ "1-add cunsumer, \n"
					+ "2-add order, \n"
					+ "3-add rider, \n"
					+ "4-assign a rider to a delivery, \n"
					+ "else-exit \n"
					+ ": ", false)) {
			case 1:
				int code = deliverySystem.generateCustomerCode();
				if(deliverySystem.addCustomer(InputManager.creatCustomer(code)))
					System.out.println("The customer code is " + code);
				break;

			case 2:
				int restCode = InputManager.inputInt("Enter restaurant code");
				while(deliverySystem.getRestAdminRestaurant(restAdminCode, restCode) == null && restCode != BACK) {
					System.out.println("Can't get the restaurant");
					restCode = InputManager.inputInt("Enter restaurant code");
				}
				if(restCode == BACK) break;

				int clientCode = InputManager.inputInt("Enter client code");
				Customer customer;
				while((customer = deliverySystem.tryGetCustomer(clientCode)) == null && clientCode != BACK) {
					System.out.println("No customer matching the code");
					clientCode = InputManager.inputInt("Enter client code");
				}
				if(clientCode == BACK) break;

				int basePrice = InputManager.inputInt("Enter base price (not negative)",InputManager.NOT_NEGATIVE);
				if(basePrice == BACK) break;

				int orderCode = deliverySystem.addOrder(restCode, customer, basePrice, InputManager.createDate());
				if(orderCode != -1)
					System.out.println("The order code is " + orderCode);
				else System.out.println("Customer cant afford the order");
				break;

			case 3:
				deliverySystem.addRiders(InputManager.createRider());
				break;

			case 4:
				if(deliverySystem.getOrdersCount() == 0)
				{
					System.out.println("No orders in the system");
					break;
				}
				
				Rider rider;
				String riderId = InputManager.inputString("Enter rider id", false);
				if(riderId.equalsIgnoreCase(Integer.toString(BACK))) break;
				int ordCode = InputManager.inputInt("Enter order code");
				if(ordCode == BACK) break;
				while(!deliverySystem.addOrderToRider(riderId, ordCode)) {					
					if((rider = deliverySystem.tryGetRider(riderId)) != null && rider.isAvailable())
						System.out.println("Ether the rider doesn't exist or the order doesn't exist");
					else System.out.println("Rider isnt aveilable");

					riderId = InputManager.inputString("Enter rider id", false);
					if(riderId.equalsIgnoreCase(Integer.toString(BACK))) break;
					ordCode = InputManager.inputInt("Enter restaurant code");
					if(ordCode == BACK) break;
				}
				break;

			default: return;
			}
		}

	}

	// the rider menu
	public static void riderMenu() {
		System.out.println("Rider menu");
		Rider rider;
		String id = InputManager.inputString("Enter rider id", false);
		while((rider = deliverySystem.tryGetRider(id)) == null) {
			if(id.equalsIgnoreCase(Integer.toString(BACK))) return;
			System.out.println("Rider of this id doesn't exist");
			id = InputManager.inputString("Enter rider id", false);
		}

		while (true) {
			switch (InputManager.inputInt("Enter command \n"
					+ "1-update dilivery status, \n"
					+ "2-show all orders, \n"
					+ "else-exit \n"
					+ ": ", false)) {
			case 1:
				if(rider.getOrders().length == 0) {
					System.out.println("No order found");
					break;
				}

				Order order;
				int code = InputManager.inputInt("Enter order code");
				while ((order = rider.getOrder(code)) == null && code != BACK) {
					System.out.println("No matching order found");
					code = InputManager.inputInt("Enter order code");
				}
				if(code == BACK) break;
				int act;
				if((act = InputManager.inputInt("Enter command \n"
						+ "1-update status to on the way, \n"
						+ "else-update status to deliverd, \n"
						)) == 1) {
					order.setDeliveryStatus(Order.OnTheWay);
					rider.setAvailable(false);
				}
				else if(act != BACK) {
					Date aftreDate = InputManager.createDateAfterDate(order.getOrderingDate());
					if(aftreDate == null) break;
					order.setDeliveryStatus(Order.Delivered);
					order.setDeliveringDate(aftreDate);
					boolean isFree = true;
					for(Order ord : rider.getOrders()) {
						if(ord.getDeliveryStatus() == Order.OnTheWay) {
							isFree = false;
							break;
						}
					}
					if(isFree)
						rider.setAvailable(true);
				}


				break;

			case 2:
				if(rider.getOrders().length == 0) 
					System.out.println("No order found");
				
				else for(Order ord: rider.getOrders()) 
					System.out.println(ord);
				
				break;

			default: return;
			}

		}
	}

	// the customer menu
	public static void customerMenu() {
		System.out.println("Customer menu");
		Customer customer;
		int code = InputManager.inputInt("Enter code");
		while((customer = deliverySystem.tryGetCustomer(code)) == null) {
			if(code == BACK) return;
			
			System.out.println("Customer of this code doesn't exist");
			code = InputManager.inputInt("Enter code");
		}
		while (true) {
			switch (InputManager.inputInt(
					"Enter command \n"
					+ "1-Make an order, \n"
					+ "2-show all orders, \n"
					+ "3-update personal information, \n"
					+ "4-show restaurant information, \n"
					+ "else-exit \n"
					+ ": ", false)) {
			case 1:
				if(customer.getCreditBalance() < 0) {
					System.out.println("You cant buy anything");
					break;
				}
				int tmp = InputManager.inputInt("Enter restaurant code");
				while(!deliverySystem.isContainsRestaurant(tmp) && tmp != BACK) {
					System.out.println("Can't get the restaurant");
					tmp = InputManager.inputInt("Enter restaurant code");
				}
				if(tmp == BACK) break;

				double basePrice = InputManager.inputDouble("Enter the base fee (not negative)", InputManager.NOT_NEGATIVE);
				if(basePrice == BACK) break;

				int orderCode = deliverySystem.addOrder(code, customer, basePrice, InputManager.createDate());
				if(orderCode != -1) 
					System.out.println("The order code is " + orderCode);
				else System.out.println("You cant afford the order");
				break;

			case 2:
				Order[] orders = deliverySystem.getOrdersOfCustomer(customer);
				for(Order order : orders) {
					System.out.println(order);
				}
				if(orders.length == 0)
					System.out.println("No orders found");

				break;

			case 3:
				if(InputManager.inputBool("Do you want to change your adress?")){
					if(InputManager.inputBool("Do you want to chage the town?")) {
						String townString = InputManager.inputString("Enter new town", true);
						if(!townString.equalsIgnoreCase(Integer.toString(BACK)))
							customer.setTown(townString);
					}

					if(InputManager.inputBool("Do you want to chage the street?")) {
						String streetString = InputManager.inputString("Enter new street", false);
						if(!streetString.equalsIgnoreCase(Integer.toString(BACK)))
							customer.setStreet(streetString);
					}

					if(InputManager.inputBool("Do you want to chage the ZIP code?")) {
						int zip = InputManager.inputInt("Enter new ZIP code (positive)", InputManager.POSITIVE);
						if(zip != BACK)
						customer.setZipCode(zip);
					}
				}
				if(InputManager.inputBool("Do you want to chage your phone number?")) {
					String phone;
					while(!Customer.isValidePhoneNumber((phone = InputManager.inputString("Enter the new phone number (IL)", false))) &&
							!phone.equalsIgnoreCase(Integer.toString(BACK)));
					if(!phone.equalsIgnoreCase(Integer.toString(BACK)))
						customer.setPhoneNumber(phone);
				}
				break;

			case 4:

				int restCode = InputManager.inputInt("Enter restaurant code");
				String info;
				while((info = deliverySystem.tryGetRestaurantInfo(restCode)) == null && restCode != BACK) {
					System.out.println("Can't get the restaurant");
					restCode = InputManager.inputInt("Enter restaurant code");
				}
				if(restCode != BACK)
				System.out.println(info);

				break;

			default: return;
			}

		}

	}

	// the main menu
	public static void mainMenu() {
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// to use the 0.1 format for double input
		InputManager.inScanner.useLocale(Locale.US);
		
		// initial programm values:

		// Riders
		Rider[] riders = {
		    new Rider("123456789", "John", "Smith", "0511234567", "Motorcycle"),
		    new Rider("212345678", "David", "Brown", "0521234567", "Scooter"),
		    new Rider("312345678", "Michael", "Johnson", "0531234567", "Car"),
		    new Rider("412345678", "Daniel", "Wilson", "0541234567", "Bicycle"),
		    new Rider("512345678", "James", "Taylor", "0551234567", "Motorcycle")
		};

		// Customers
		Customer[] customers = {
		    new Customer(1, "Alice", "Green", "Oak St", "New York", 10001, "111111111", "alice@mail.com", 100),
		    new Customer(2, "Bob", "White", "Maple St", "Chicago", 10002, "222222222", "bob@mail.com", 50),
		    new Customer(3, "Charlie", "Black", "Pine St", "Boston", 10003, "333333333", "charlie@mail.com", 80),
		    new Customer(4, "Diana", "Gray", "Hill St", "Dallas", 10004, "444444444", "diana@mail.com", 120),
		    new Customer(5, "Ethan", "Stone", "Lake St", "Miami", 10005, "555555555", "ethan@mail.com", 30),
		    new Customer(6, "Fiona", "King", "River St", "Seattle", 10006, "666666666", "fiona@mail.com", 200),
		    new Customer(7, "George", "Hall", "Elm St", "Denver", 10007, "777777777", "george@mail.com", 90),
		    new Customer(8, "Helen", "Young", "Sun St", "Austin", 10008, "888888888", "helen@mail.com", 70),
		    new Customer(9, "Ian", "Scott", "Main St", "Phoenix", 10009, "999999999", "ian@mail.com", 150),
		    new Customer(10, "Julia", "Lee", "King St", "Atlanta", 10010, "101010101", "julia@mail.com", 40)
		};

		// Restaurants
		Restaurant[] restaurants = {
		    new Restaurant(1, "Bella Pizza", "Italian", 4.6, true, 10),
		    new Restaurant(2, "Dragon Wok", "Chinese", 4.4, true, 12),
		    new Restaurant(3, "Tokyo Sushi", "Japanese", 4.8, true, 15),
		    new Restaurant(4, "Burger House", "American", 4.2, false, 9),
		    new Restaurant(5, "Taco Land", "Mexican", 4.3, true, 8),
		    new Restaurant(6, "Curry King", "Indian", 4.7, true, 11),
		    new Restaurant(7, "Greek Corner", "Greek", 4.1, true, 10),
		    new Restaurant(8, "Paris Cafe", "French", 4.5, true, 13),
		    new Restaurant(9, "Thai Spice", "Thai", 4.6, false, 14),
		    new Restaurant(10, "Mediterraneo", "Mediterranean", 4.9, true, 16),

		    new FastFoodRestaurant(101, "McBurger", "Fast Food", 4.1, true, 8, 10, 5),
		    new FastFoodRestaurant(102, "Quick Pizza", "Pizza", 4.2, true, 9, 15, 4),
		    new FastFoodRestaurant(103, "Hot Chicken", "Chicken", 4.0, true, 7, 12, 3),
		    new FastFoodRestaurant(104, "Burger Max", "Burgers", 4.3, false, 8, 11, 6),
		    new FastFoodRestaurant(105, "Fast Taco", "Mexican", 4.4, true, 7, 8, 2),
		    new FastFoodRestaurant(106, "Express Sushi", "Japanese", 4.5, true, 10, 14, 7),
		    new FastFoodRestaurant(107, "Wrap Zone", "Wraps", 4.0, true, 6, 9, 3),
		    new FastFoodRestaurant(108, "Fry King", "Fast Food", 3.9, true, 5, 7, 2),
		    new FastFoodRestaurant(109, "Pizza Box", "Pizza", 4.6, true, 9, 16, 5),
		    new FastFoodRestaurant(110, "Snack Hub", "Snacks", 4.2, false, 6, 10, 4),

		    new PremiumRestaurant(201, "Royal Steak", "Steakhouse", 4.9, true, 20, 100, 15),
		    new PremiumRestaurant(202, "Golden Sushi", "Japanese", 4.8, true, 18, 120, 18),
		    new PremiumRestaurant(203, "Elite Pasta", "Italian", 4.7, true, 17, 90, 12),
		    new PremiumRestaurant(204, "Luxury Grill", "Grill", 4.9, false, 22, 150, 20),
		    new PremiumRestaurant(205, "Ocean Pearl", "Seafood", 5.0, true, 25, 200, 25),
		    new PremiumRestaurant(206, "Chef's Table", "French", 4.8, true, 19, 180, 18),
		    new PremiumRestaurant(207, "Prime Kitchen", "International", 4.7, true, 21, 130, 16),
		    new PremiumRestaurant(208, "Sky Lounge", "Fusion", 4.9, true, 24, 160, 19),
		    new PremiumRestaurant(209, "Black Truffle", "European", 4.8, true, 23, 170, 17),
		    new PremiumRestaurant(210, "Diamond Dining", "Fine Dining", 5.0, true, 30, 250, 30)
		};

		// Admins
		Admin[] admins = {
		    new RestAdmin("Tom", 1, "tom", 1111),
		    new RestAdmin("Sarah", 2, "sarah", 2222),
		    new RestAdmin("Mike", 3, "mike", 3333),
		    new Admin(
				    "admin",
				    0,
				    "admin",
				    1234
				)
		};
		
		deliverySystem.setAdmins(admins);
		deliverySystem.setAdminsCount(admins.length);

		deliverySystem.setCustomers(customers);
		deliverySystem.setCustomersCount(customers.length);

		deliverySystem.setRestaurants(restaurants);
		deliverySystem.setRestaurantsCount(restaurants.length);

		deliverySystem.setRiders(riders);
		deliverySystem.setRidersCount(riders.length);

		mainMenu();
		System.out.println("Have a nice day.");
	}

}
