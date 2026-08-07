package HW2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import HW2.DataObjects.*;
import HW2.DataObjects.Order.OrderStatus;

public class DeliveryDataBase {
	
	public enum CodedType {
		RestAdmin,
		Restaurant,
		Customer,
		Order
	}

	private final Admin systemAdministrator;

	private final ArrayList<RestAdmin> restAdmins;
	private final ArrayList<Restaurant> restaurants;
	private final ArrayList<Customer> customers;
	private final ArrayList<Rider> riders;
	private final ArrayList<Order> orders;

	private final HashMap<Integer, ArrayList<Order>> ordersByCustomer;
	private final HashMap<Integer, ArrayList<Restaurant>> selectedRestaurantsByCustomer;
	private final HashMap<Integer, Double> totalSpentByCustomer;

	
	public DeliveryDataBase(Admin systemAdministrator) {
		this.restAdmins = new ArrayList<>();
		this.restaurants = new ArrayList<>();
		this.customers = new ArrayList<>();
		this.riders = new ArrayList<>();
		this.orders = new ArrayList<>();

		ordersByCustomer = new HashMap<>();
		selectedRestaurantsByCustomer = new HashMap<>();
		totalSpentByCustomer = new HashMap<>();

		this.systemAdministrator = systemAdministrator;
	}

	public DeliveryDataBase() {
		this(new Admin("admin", "admin", 12345));
	}

	public DeliveryDataBase(Admin systemAdministrator, Customer[] customers, Restaurant[] restaurants, Rider[] riders,
			Order[] orders, RestAdmin[] restAdmins) {

		this(systemAdministrator);
		
		for (Customer customer : customers)
			this.customers.add(customer);

		for (Restaurant restaurant : restaurants)
			this.restaurants.add(restaurant);

		for (Rider rider : riders)
			this.riders.add(rider);

		for (RestAdmin restAdmin : restAdmins)
			this.restAdmins.add(restAdmin);

		for (Order order : orders) 
			addOrderToCustomer(order.getClientCode(), order);

	}

	// add Order To Customer
	public void addOrderToCustomer(int customerCode, Order order) {
	    if (order == null || orders.contains(order)) return;
	    orders.add(order);

	    ordersByCustomer.putIfAbsent(customerCode, new ArrayList<>());
	    ArrayList<Order> custOrders = ordersByCustomer.get(customerCode);
	    if (!custOrders.contains(order))
	        custOrders.add(order);

	    totalSpentByCustomer.putIfAbsent(customerCode, 0.0);
	    double currentTotal = totalSpentByCustomer.getOrDefault(customerCode, 0.0);
	    totalSpentByCustomer.put(customerCode, currentTotal + order.getFinalPrice());

	    selectedRestaurantsByCustomer.putIfAbsent(customerCode, new ArrayList<>());
	    ArrayList<Restaurant> selRests = selectedRestaurantsByCustomer.get(customerCode);
	    if (!selRests.contains(order.getRestaurant()))
	        selRests.add(order.getRestaurant());
	}


	// remove an order from the system
	public void removeOrder(int code) {
		Order order = tryGetOrder(code);
		if (order == null || order.getOrderStatus() == OrderStatus.Delivered) return;
		
		Customer customer = tryGetCustomer(order.getClientCode());
		if(customer == null) return;

		
	    Restaurant restaurant = tryGetRestaurant(order.getRestaurantCode());
	    if (restaurant == null)
	        return;

	    if (!ordersByCustomer.containsKey(order.getClientCode()) || !ordersByCustomer.get(order.getClientCode()).contains(order))
	        return;
	    
	    if(order.getRiderId() != null) {

		    Rider rider = tryGetRider(order.getRiderId());
		    if (rider != null) 
		    	rider.removeCurrentOrder();
	    }
		

	    double backMoney = order.getFinalPrice() * (order.getOrderStatus() == OrderStatus.Created ? 1.0 : 0.5);

	    double currentTotal = totalSpentByCustomer.getOrDefault(order.getClientCode(), 0.0);
	    totalSpentByCustomer.put(order.getClientCode(), Math.max(0.0, currentTotal - backMoney));

	    customer.setBalance(customer.getCreditBalance() + backMoney);

	    orders.remove(order);
	    ordersByCustomer.get(order.getClientCode()).remove(order);

	    if (selectedRestaurantsByCustomer.containsKey(order.getClientCode())) {
	        selectedRestaurantsByCustomer.get(order.getClientCode()).remove(restaurant);
	        
	        for (Order o : getOrdersOfCustomer(customer)) {
	            if (o.getRestaurantCode() == restaurant.getCode()) {
	                if (!selectedRestaurantsByCustomer.get(order.getClientCode()).contains(restaurant))
	                    selectedRestaurantsByCustomer.get(order.getClientCode()).add(restaurant);
	                break;
	            }
	        }
	    }
	}

 
	// get Premium Restaurants By Customer
	public ArrayList<PremiumRestaurant> getPremiumRestaurantsByCustomer(Customer customer) {
		if(customer == null) return new ArrayList<>();
		ArrayList<PremiumRestaurant> premiumRestaurants = new ArrayList<>();
		if (selectedRestaurantsByCustomer.containsKey(customer.getCode())) {
			for (Restaurant restaurant : selectedRestaurantsByCustomer.get(customer.getCode())) {
				if (restaurant instanceof PremiumRestaurant premiumRestaurant) {
					premiumRestaurants.add(premiumRestaurant);
				}
			}
		}
		return premiumRestaurants;
	}

	// get Number Of Orders For Customer
	public int getNumberOfOrdersForCustomer(int code) {
		return ordersByCustomer.containsKey(code) ? ordersByCustomer.get(code).size() : 0;
	}

	// get Customer With Most Orders
	public Customer getCustomerWithMostOrders() {
		Customer customerWithMostOrders = null;
		int maxOrders = 0;

		for (Customer customer : customers) {
			int customerCode = customer.getCode();
			int orderCount = getNumberOfOrdersForCustomer(customerCode);

			if (orderCount >= maxOrders) {
				maxOrders = orderCount;
				customerWithMostOrders = customer;
			}
		}

		return customerWithMostOrders;
	}

	// get Rider With Most Delivered Orders
	public Rider getRiderWithMostDeliverdOrders() {
		Rider riderWithMostOrders = null;
		int maxOrders = 0;

		for (Rider rider : riders) {
			int orderCount = rider.getDeliverdOrders().size();

			if (orderCount >= maxOrders) {
				maxOrders = orderCount;
				riderWithMostOrders = rider;
			}
		}

		return riderWithMostOrders;
	}
	
	// returns all open restaurants by kitchen type
	public ArrayList<Restaurant> getOpenRestaurants(String kitchenType) {
		ArrayList<Restaurant> openRestaurants = new ArrayList<>();
		for (Restaurant restaurant : restaurants) {
			if (restaurant.isOpen() && restaurant.getKitchenType().equalsIgnoreCase(kitchenType)) {
				openRestaurants.add(restaurant);
			}
		}
		return openRestaurants;
	}

	// returns the restaurants thet selected by a customer (the given customer code)
	public ArrayList<Restaurant> getRestaurantasBuyCustomer(int code) {
		if (!selectedRestaurantsByCustomer.containsKey(code)) {
			return new ArrayList<>();
		}
		return selectedRestaurantsByCustomer.get(code);
	}
	
	// returns all the orders of the given restaurant (by restaurant code)
	public ArrayList<Order> getOrdersByuRestaurant(int code){
		ArrayList<Order> ords = new ArrayList<>();
		for(Order order: orders)
			if(order.getRestaurantCode() == code)
				ords.add(order);
		return ords;
	}
	
	public Hashtable<OrderStatus, ArrayList<Order>> splitOrdersBuyTaype(ArrayList<Order> orders){
		Hashtable<OrderStatus, ArrayList<Order>> res = new Hashtable<Order.OrderStatus, ArrayList<Order>>(3);
		res.put(OrderStatus.Created, new ArrayList<>());
		res.put(OrderStatus.OnTheWay, new ArrayList<>());
		res.put(OrderStatus.Delivered, new ArrayList<>());
		
		for(Order order : orders)
			res.get(order.getOrderStatus()).add(order);
		return res;
	}
	
	// filter Orders Buy given Status
	public ArrayList<Order> filterOrdersBuyStatus(ArrayList<Order> orders, OrderStatus status){
		return splitOrdersBuyTaype(orders).get(status);
	}

//	// filter Orders Buy not given Status
//	public ArrayList<Order> filterOrdersBuyNotStatus(ArrayList<Order> orders, OrderStatus status){
//		ArrayList<Order> res = new ArrayList<Order>();
//		
//		Hashtable<OrderStatus, ArrayList<Order>> ordersHashtable = splitOrdersBuyTaype(orders);
//		ordersHashtable.remove(status);
//		
//		for(ArrayList<Order> ords : ordersHashtable.values())
//			res.addAll(ords);
//		return res;
//	}
	
	// checks if the data matches to the systemAdministrator data
	public boolean logIntoAdmin(String userName, int password) {
		return systemAdministrator.getUserName().equalsIgnoreCase(userName) && systemAdministrator.getPassword() == password;
	}

	// returns the RestAdmin by user name and password (if can't find -> return null)
	public RestAdmin tryGetRestAdmin(String userName, int password) {
		for (RestAdmin restAdmin : restAdmins) {
			if (restAdmin.getUserName().equalsIgnoreCase(userName) && restAdmin.getPassword() == password) {
				return restAdmin;
			}
		}
		return null;
	}

	// returns the RestAdmin by code (if can't find -> return null)
	public RestAdmin tryGetRestAdmin(int code) {
		return Coded.tryGetCoded(restAdmins, code);
	}

	// returns the Rider by id (if cant find -> return null)
	public Rider tryGetRider(String id) {
		for (Rider rider : riders) {
			if (rider.getId().equalsIgnoreCase(id)) {
				return rider;
			}
		}
		return null;
	}

	// returns the Customer by code (if cant find -> return null)
	public Customer tryGetCustomer(int code) {
		return Coded.tryGetCoded(customers, code);
	}
	
	// returns the Order by code (if cant find -> return null)
	public Order tryGetOrder(int code) {
		return Coded.tryGetCoded(orders, code);
	}
	
	// returns the Restaurant by code (if cant find -> return null)
	public Restaurant tryGetRestaurant(int code) {
		return Coded.tryGetCoded(restaurants, code);
	}

	// gets all the orders of the given Customer
	public ArrayList<Order> getOrdersOfCustomer(Customer customer) {
		return ordersByCustomer.getOrDefault(customer.getCode(), new ArrayList<>());
	}
	
	// get Orders Of RestAdmin
	public ArrayList<Order> getOrdersOfRestAdmin(int code){
		RestAdmin restAdmin = Coded.tryGetCoded(restAdmins, code);
		if(restAdmin == null) return  new ArrayList<>();

		ArrayList<Order> ords = new ArrayList<>();
		for(Restaurant restaurant : restAdmin.getRestaurants())
			ords.addAll(getOrdersByuRestaurant(restaurant.getCode()));

		return ords;
	}

	// adds a Customer (if exists -> does nothing)
	public boolean addCustomer(Customer customer) {
		if (customer == null || isContains(customer.getCode(), CodedType.Customer))
			return false;
		customers.add(customer);
		return true;
	}

	// adds a Admin (if exists -> does nothing)
	public boolean addRestAdmine(RestAdmin admin) {
		if (admin == null || isContains(admin.getCode(), CodedType.RestAdmin))
			return false;
		restAdmins.add(admin);
		return true;
	}

	// adds a Restaurant (if exists -> does nothing)
	public boolean addRestaurant(Restaurant restaurant) {
		if (restaurant == null || isContains(restaurant.getCode(), CodedType.Restaurant))
			return false;
		restaurants.add(restaurant);
		return true;
	}

	// adds a Rider (if exists -> does nothing)
	public void addRiders(Rider rider) {
		if (rider == null || isContainsRider(rider.getId()))
			return;
		riders.add(rider);
	}

	// adds a Order by the needed parameters to do so (if exists -> does nothing)
	public int addOrder(int restaurantCode, int customerCode, double basePrice, Date date) {
		if (date == null)
			return -1;

		Customer customer = Coded.tryGetCoded(customers, customerCode);
		Restaurant restaurant = Coded.tryGetCoded(restaurants, restaurantCode);
		if (restaurant == null || customer == null)
			return -1;

		int code = generateCode(CodedType.Order);
		Order order = new Order(code, customerCode, restaurant, date, basePrice);
		
		if (!customer.buy(order.getFinalPrice()))
			return -1;
		
		addOrderToCustomer(order.getClientCode(), order);
		return code;
	}

	// adds a Restaurant to a RestAdmin
	public void addRestToAdmin(int adminCode, int restCode) {
		RestAdmin restAdmin = Coded.tryGetCoded(restAdmins, adminCode);
		if (restAdmin == null)
			return;

		Restaurant restaurant = Coded.tryGetCoded(restaurants, restCode);
		if (restaurant == null)
			return;

		restAdmin.addRestaurant(restaurant);
	}

	// adds an Order to a Rider (RestAdmin)
	public void addOrderToRider(String riderId, int orderCode) {
		Order order = Coded.tryGetCoded(orders, orderCode);
		if (order == null || order.getOrderStatus() != OrderStatus.Created)
			return;

		Rider rider = tryGetRider(riderId);
		if (rider == null || !rider.isAvailable())
			return;

		if (order.getRiderId() != null) {
			Rider oldRider = tryGetRider(order.getRiderId());
			if(oldRider != null)
				oldRider.removeCurrentOrder();
		}

		rider.setCurrentOrder(order);
	}

	// adds an Order to a Rider (RestAdmin)
	public void addOrderToRider(String riderId, int orderCode, int restAdminCode) {
		Order order = Coded.tryGetCoded(orders, orderCode);
		RestAdmin restAdmin = Coded.tryGetCoded(restAdmins, restAdminCode);
		if (restAdmin == null || order == null || !restAdmin.containsRestaurant(order.getRestaurantCode()))
			return;
		
		addOrderToRider(riderId, orderCode);
	}

	// Checks if contains the object with the given code and type
	public boolean isContains(int code, CodedType type) {
		switch (type) {
			case RestAdmin:
				return Coded.isContains(restAdmins, code);

			case Restaurant:
				return Coded.isContains(restaurants, code);

			case Customer:
				return Coded.isContains(customers, code);

			case Order:
				return Coded.isContains(orders, code);

			default: return false;
		}
	}
	
	// Checks if contains Rider with the given id
	public boolean isContainsRider(String id) {
		return tryGetRider(id) != null;
	}
	
	// returns a unique code (not negative) for the given type
	public int generateCode(CodedType type) {
		switch (type) {
			case RestAdmin:
				return Coded.generateCode(restAdmins);

			case Restaurant:
				return Coded.generateCode(restaurants);

			case Customer:
				return Coded.generateCode(customers);

			case Order:
				return Coded.generateCode(orders);
				
			default: return -1;
		}
	}
	
	// updating the balance of a customer by his code
	public boolean setBalanceToCustomer(int code, double balance) {
		Customer customer = tryGetCustomer(code);
		if(customer == null) return false;
		return customer.setBalance(balance);
	}
	
	// get all open restaurants
	public ArrayList<Restaurant> getOpenRestaurants(){
		ArrayList<Restaurant> res = new ArrayList<Restaurant>();
		for(Restaurant restaurant: restaurants)
			if(restaurant.isOpen())
				res.add(restaurant);
		return res;
	}
	

	// update the delivery status (add delivering date)
	public void updateDeliveryStatus(String riderId, Date deliveryDate) {
		Rider rider = tryGetRider(riderId);
		if( rider == null) return;
		Order order = rider.getCurrentOrder();
		if(order == null) return;
		
		if (deliveryDate == null && order.getOrderStatus() == OrderStatus.OnTheWay) return;
		if(deliveryDate != null)
			order.setDeliveringDate(deliveryDate);
		
		rider.changeCurrentOrderStatus();
	}
	
	// switch between open and close restaurant by code
	public boolean changeRestaurantStatus(int code) {
		Restaurant restaurant = tryGetRestaurant(code);
		if(restaurant == null) return false;
		
		restaurant.setOpen(!restaurant.isOpen());
		return true;
	}

	public ArrayList<RestAdmin> getRestAdmins() {
		return restAdmins;
	}

	public ArrayList<Restaurant> getRestaurants() {
		return restaurants;
	}

	public ArrayList<Customer> getCustomers() {
		return customers;
	}

	public ArrayList<Rider> getRiders() {
		return riders;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public Admin getSystemAdministrator() {
		return systemAdministrator;
	}

	public HashMap<Integer, ArrayList<Order>> getOrdersByCustomer() {
		return ordersByCustomer;
	}

	public HashMap<Integer, ArrayList<Restaurant>> getSelectedRestaurantsByCustomer() {
		return selectedRestaurantsByCustomer;
	}

	public HashMap<Integer, Double> getTotalSpentByCustomer() {
		return totalSpentByCustomer;
	}

	@Override
	public String toString() {
		return "DeliveryDataBase [systemAdministrator=" + systemAdministrator + ", restAdmins=" + restAdmins
				+ ", restaurants=" + restaurants + ", customers=" + customers + ", riders=" + riders + ", orders="
				+ orders + ", ordersByCustomer=" + ordersByCustomer + ", selectedRestaurantsByCustomer="
				+ selectedRestaurantsByCustomer + ", totalSpentByCustomer=" + totalSpentByCustomer + "]";
	}

	
	
}
