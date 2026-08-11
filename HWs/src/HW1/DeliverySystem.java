package HW1;

import java.util.Arrays;
import java.util.Random;

public class DeliverySystem {

	private Customer[] customers = new Customer[0];
	private Admin[] admins = new Admin[0];
	private Restaurant[] restaurants = new Restaurant[0];
	private Rider[] riders = new Rider[0];
	private Order[] orders = new Order[0];

	private int customersCount = 0,
			adminsCount = 0,
			restaurantsCount = 0,
			ridersCount = 0,
			ordersCount = 0;
	
	// checks if the restaurant of the given code open 
	public boolean isRestaurantOpen(int code) {
		for (int i = 0; i < restaurantsCount; i++) {
			if(restaurants[i].getCode() == code) {
				return restaurants[i].isOpen();
			}
		}
		return false;
	}

	// returns the Admin by user name and password (if cant find -> return null)
	public Admin tryGetAdmin(String userName, int password) {
		for (int i = 0; i < adminsCount; i++) {
			if(admins[i].getUserName().equalsIgnoreCase(userName) &&
					admins[i].getPassword() == password && !(admins[i] instanceof RestAdmin) ) {
				return admins[i];
			}
		}

		return null;
	}

	// returns the RestAdmin by user name and password (if cant find -> return null)
	public RestAdmin tryGetRestAdmin(String userName, int password) {
		for (int i = 0; i < adminsCount; i++) {
			if(admins[i].getUserName().equalsIgnoreCase(userName) &&
					admins[i].getPassword() == password && admins[i] instanceof RestAdmin tmp ) {
				return tmp;
			}
		}

		return null;
	}

	// returns the Rider by id (if cant find -> return null)
	public Rider tryGetRider(String id) {
		for (int i = 0; i < ridersCount; i++) {
			if(riders[i].getId().equalsIgnoreCase(id)) {
				return riders[i];
			}
		}
		return null;
	}

	// returns the Customer by code (if cant find -> return null)
	public Customer tryGetCustomer(int code) {
		for (int i = 0; i < customersCount; i++) {
			if(customers[i].getCode() == code) {
				return customers[i];
			}
		}
		return null;
	}

	// adds a Customer (if exists -> does nothing)
	public boolean addCustomer(Customer customer) {
		if(customer == null) return false;
		for (int i = 0; i < customersCount; i++) {
			if(customers[i].getCode() == customer.getCode()) {
				return false;
			}
		}

		if(customersCount >= customers.length) {
			customers = Arrays.copyOf(customers, customersCount + 1);
		}

		customers[customersCount++] = customer;
		return true;
	}

	// adds a Admin (if exists -> does nothing)
	public boolean addAdmine(Admin admin) {
		if(admin == null) return false;
		for (int i = 0; i < adminsCount; i++) {
			if(admins[i].getCode() == admin.getCode()) {
				return false;
			}
		}

		if(adminsCount >= admins.length) {
			admins = Arrays.copyOf(admins, adminsCount + 1);
		}

		admins[adminsCount++] = admin;
		return true;
	}

	// adds a Restaurant (if exists -> does nothing)
	public boolean addRestaurant(Restaurant restaurant) {
		if(restaurant == null) return false;
		for (int i = 0; i < restaurantsCount; i++) {
			if(restaurants[i].getCode() == restaurant.getCode()) {
				return false;
			}
		}

		if(restaurantsCount >= restaurants.length) {
			restaurants = Arrays.copyOf(restaurants, restaurantsCount + 1);
		}

		restaurants[restaurantsCount++] = restaurant;
		return true;
	}

	// adds a Rider (if exists -> does nothing)
	public void addRiders(Rider rider) {
		if(rider == null) return;
		for (int i = 0; i < ridersCount; i++) {
			if(riders[i].getId() == rider.getId()) {
				return;
			}
		}

		if(ridersCount >= riders.length) {
			riders = Arrays.copyOf(riders, ridersCount + 1);
		}

		riders[ridersCount++] = rider;
	}

	// adds a Order (if exists -> does nothing)
	private void addOrder(Order order) {
		for (int i = 0; i < ordersCount; i++) {
			if(orders[i].getOrderCode() == order.getOrderCode()) {
				return;
			}
		}

		if(ordersCount >= orders.length) {
			orders = Arrays.copyOf(orders, ordersCount + 1);
		}

		orders[ordersCount++] = order;
	}

	// adds a Order by the needet parameters to do so (if exists -> does nothing)
	public int addOrder(int restCode, Customer customer, double basePrice, Date date) {
		if(date == null) return -1;
		for (int i = 0; i < restaurantsCount; i++) {
			if(restaurants[i].getCode() == restCode) {
				int code = generateOrderCode();
				Order order = new Order(code, customer.getCode(), restaurants[i], date, basePrice );
				if(customer.getCreditBalance() < order.getFinalPrice()) {
					return -1;
				}
				customer.setCreditBalance(customer.getCreditBalance() - order.getFinalPrice());
				addOrder(order);
				return code;
			}
		}
		return -1;
	}

	// adds a Restaurant to a RestAdmin
	public boolean addRestToAdmin(int adminCode, int restCode) {
		Restaurant restaurant = null;

		for (int i = 0; i < restaurantsCount; i++) {
			if(restaurants[i].getCode() == restCode)
			{
				restaurant = restaurants[i];
				break;
			}
		}
		if(restaurant == null) {
			return false;
		}

		for (int i = 0; i < adminsCount; i++) {
			if(admins[i].getCode() == adminCode) {
				if(admins[i] instanceof RestAdmin restAdmin) {
					restAdmin.addRestaurant(restaurant);
				}
				return true;
			}
		}
		return false;
	}

	// adds an Order to a Rider
	public boolean addOrderToRider(String riderId, int orderCode, Admin admin) {
		Order order = null;

		for (int i = 0; i < ordersCount; i++) {
			if (orders[i].getOrderCode() == orderCode) {
				order = orders[i];
				
				if(admin instanceof RestAdmin restAdmin) 
					if(restAdmin.tryGetRestaurant(order.getRestaurantCode()) == null)
						return false;
				
				break;
			}
		}
		if(order == null) {
			return false;
		}

		for (int i = 0; i < ridersCount; i++) {
			if(riders[i].getId().equalsIgnoreCase(riderId)) {
				if(riders[i].isAvailable()) {
					riders[i].addOrder(order);
					return true;
				}
				return false;
			}
		}
		return false;
	}

	// returns the Restaurant of a restAdmin by codes (if fail returns null)
	public Restaurant tryGetRestAdminRestaurant(int adminCode, int restCode) {
		for (int i = 0; i < adminsCount; i++) {
			if(admins[i].getCode() == adminCode && admins[i] instanceof RestAdmin restAdmin) {
				return restAdmin.tryGetRestaurant(restCode);
			}
		}
		return null;
	}

	// gets all the orders of the given Customer
	public Order[] getOrdersOfCustomer(Customer customer ) {
		Order[] ordersTmp = new Order[ordersCount];
		int idx = 0;

		for (int i = 0; i < ordersCount; i++) {
			if(orders[i].getClientCode() == customer.getCode()) {
				ordersTmp[idx++] = orders[i];
			}
		}
		return Arrays.copyOf(ordersTmp, idx);
	}

	// returns the restaurant info (toString) (can't find -> returns null)
	public String tryGetRestaurantInfo(int code) {
		for (int i = 0; i < restaurantsCount; i++) 
			if(restaurants[i].getCode() == code)
				return restaurants[i].toString();
		return null;
	}

	// cheacks if contains restaurant with the given code
	public boolean isContainsRestaurant(int code) {
		for (int i = 0; i < restaurantsCount; i++) {
			if(restaurants[i].getCode() == code) {
				return true;
			}
		}

		return false;
	}

	// cheacks if contains admin with the given code
	private boolean isContainsAdmin(int code) {
		for(int i = 0; i < adminsCount; i++) {
			if(admins[i].getCode() == code) {
				return true;
			}
		}
		return false;
	}

	// cheacks if contains order with the given code
	private boolean isContainsOrder(int code) {
		for(int i = 0; i < ordersCount; i++) {
			if(orders[i].getOrderCode() == code) {
				return true;
			}
		}
		return false;
	}


	// returns a unique code (not negative) for a Restaurant
	public int generateRestaurantCode() {
		Random random = new Random();
		int code = Math.abs(random.nextInt());
		while (isContainsRestaurant(code)) {
			code = Math.abs(random.nextInt());
		}

		return code;
	}

	// returns a unique code (not negative) for a Customer
	public int generateCustomerCode() {
		Random random = new Random();
		int code = Math.abs(random.nextInt());
		while ( tryGetCustomer(code) != null  ) {
			code = Math.abs(random.nextInt());
		}

		return code;
	}


	// returns a unique code (not negative) for a Admin (or RestAdmin)
	public int generateAdminCode() {
		Random random = new Random();
		int code = Math.abs(random.nextInt());
		while (isContainsAdmin(code) ) {
			code = Math.abs(random.nextInt());
		}
		
		return code;
	}


	// returns a unique code (not negative) for a Order
	public int generateOrderCode() {
		Random random = new Random();
		int code = Math.abs(random.nextInt());
		while (isContainsOrder(code) ) {
			code = Math.abs(random.nextInt());
		}

		return code;
	}

	public Customer[] getCustomers() {
		return customers;
	}


	public Admin[] getAdmins() {
		return admins;
	}


	public Restaurant[] getRestaurants() {
		return restaurants;
	}


	public Rider[] getRiders() {
		return riders;
	}


	public Order[] getOrders() {
		return orders;
	}


	public int getCustomersCount() {
		return customersCount;
	}


	public int getAdminsCount() {
		return adminsCount;
	}


	public int getRestaurantsCount() {
		return restaurantsCount;
	}


	public int getRidersCount() {
		return ridersCount;
	}


	public int getOrdersCount() {
		return ordersCount;
	}


	public void setCustomers(Customer[] customers) {
		this.customers = customers;
	}


	public void setAdmins(Admin[] admins) {
		this.admins = admins;
	}


	public void setRestaurants(Restaurant[] restaurants) {
		this.restaurants = restaurants;
	}


	public void setRiders(Rider[] riders) {
		this.riders = riders;
	}


	public void setOrders(Order[] orders) {
		this.orders = orders;
	}


	public void setCustomersCount(int customersCount) {
		if(0 <= customersCount) {
			this.customersCount = customersCount;
		}
	}


	public void setAdminsCount(int adminsCount) {
		if(0 <= adminsCount) {
			this.adminsCount = adminsCount;
		}
	}


	public void setRestaurantsCount(int restaurantsCount) {
		if( 0 <= restaurantsCount) {
			this.restaurantsCount = restaurantsCount;
		}
	}


	public void setRidersCount(int ridersCount) {
		if (0<= ridersCount) {
			this.ridersCount = ridersCount;
		}
	}


	public void setOrdersCount(int ordersCount) {
		if(0<= ordersCount) {
			this.ordersCount = ordersCount;
		}
	}


}
