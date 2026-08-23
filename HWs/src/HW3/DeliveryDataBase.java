package HW3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;

import HW3.DataObjects.*;
import HW3.DataObjects.Order.OrderStatus;
import HW3.Exceptions.CodedNotFoundException;
import HW3.Exceptions.CustomerNotFoundException;
import HW3.Exceptions.DeliveryPersonUnavailableException;
import HW3.Exceptions.InsufficientBalanceException;
import HW3.Exceptions.RestaurantNotFoundException;
import HW3.Exceptions.RiderNotFoundException;
import HW3.Exceptions.TargetObjectAlreadyExistException;
import HW3.Exceptions.TargetObjectDoesntExistException;

public class DeliveryDataBase {
	
	public enum CodedType {
		RestAdmin,
		Restaurant,
		Customer,
		Order
	}

	private Admin systemAdministrator;

	private ArrayList<RestAdmin> restAdmins;
	private ArrayList<Restaurant> restaurants;
	private ArrayList<Customer> customers;
	private ArrayList<Rider> riders;
	private ArrayList<Order> orders;

	private HashMap<Integer, ArrayList<Order>> ordersByCustomer;
	private Hashtable<Integer, ArrayList<Restaurant>> selectedRestaurantsByCustomer;
	private HashMap<Integer, Double> totalSpentByCustomer;
	
	private final Comparator<Restaurant> restComparator = (r1, r2) -> (int)(r2.getRating() - r1.getRating());
	private final Comparator<Order> ordComparator = (o1, o2) -> (int)(o2.getFinalPrice() - o1.getFinalPrice()); 

	
	public DeliveryDataBase(Admin systemAdministrator) {
		this.restAdmins = new ArrayList<>();
		this.restaurants = new ArrayList<>();
		this.customers = new ArrayList<>();
		this.riders = new ArrayList<>();
		this.orders = new ArrayList<>();

		ordersByCustomer = new HashMap<>();
		selectedRestaurantsByCustomer = new Hashtable<>();
		totalSpentByCustomer = new HashMap<>();

		this.systemAdministrator = systemAdministrator;
	}

	public DeliveryDataBase() {
		this(new Admin("admin", "admin", "12345"));
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
	public void removeOrder(int code) throws CodedNotFoundException, RiderNotFoundException, TargetObjectDoesntExistException {
		Order order = getOrder(code);
		if (order == null || order.getOrderStatus() == OrderStatus.Delivered) return;
		
		Customer customer = getCustomer(order.getClientCode());
	    Restaurant restaurant = getRestaurant(order.getRestaurantCode());

	    if (!ordersByCustomer.containsKey(order.getClientCode()) || !ordersByCustomer.get(order.getClientCode()).contains(order))
	    	throw new TargetObjectDoesntExistException("Order :" + code, "ordersByCustomer, ordersByCustomer");
	    
	    if(order.getRiderId() != null) 
	    	getRider(order.getRiderId()).removeCurrentOrder();
	    
		

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
	
	// filter Orders Buy given Status
	public ArrayList<Order> filterOrdersBuyStatus(ArrayList<Order> orders, OrderStatus status){
		ArrayList<Order> res = new ArrayList<>();
		for (Order order : orders)
			if(order.getOrderStatus() == status)
				res.add(order);
		return res;
	}
	
	// checks if the data matches to the systemAdministrator data
	public boolean logIntoAdmin(String userName, String password) {
		return systemAdministrator.getUserName().equalsIgnoreCase(userName) && systemAdministrator.getPassword().equals(password);
	}

	// returns the RestAdmin by user name and password (if can't find -> return null)
	public RestAdmin getRestAdmin(String userName, String password) {
		for (RestAdmin restAdmin : restAdmins) {
			if (restAdmin.getUserName().equalsIgnoreCase(userName) && restAdmin.getPassword().equals(password)) {
				return restAdmin;
			}
		}
		return null;
	}

	// returns the RestAdmin by code (if can't find -> return null)
	public RestAdmin getRestAdmin(int code) throws CodedNotFoundException, TargetObjectDoesntExistException {
		return Coded.getCoded(restAdmins, code, RestAdmin.class);
	}

	// returns the Rider by id (if cant find -> return null)
	public Rider getRider(String id) throws RiderNotFoundException {
		for (Rider rider : riders) {
			if (rider.getId().equalsIgnoreCase(id)) {
				return rider;
			}
		}
		throw new RiderNotFoundException(id);
	}

	public Customer getCustomer(int code) throws CodedNotFoundException, TargetObjectDoesntExistException{
		return Coded.getCoded(customers, code, Customer.class);
	}
	
	public Order getOrder(int code) throws CodedNotFoundException, TargetObjectDoesntExistException {
		return Coded.getCoded(orders, code, Order.class);
	}
	
	public Restaurant getRestaurant(int code) throws CodedNotFoundException, TargetObjectDoesntExistException{
		return Coded.getCoded(restaurants, code, Restaurant.class);
	}

	// gets all the orders of the given Customer
	public ArrayList<Order> getOrdersOfCustomer(Customer customer) {
		return ordersByCustomer.getOrDefault(customer.getCode(), new ArrayList<>());
	}
	
	// get Orders Of RestAdmin
	public ArrayList<Order> getOrdersOfRestAdmin(int code) throws CodedNotFoundException, TargetObjectDoesntExistException{
		RestAdmin restAdmin = Coded.getCoded(restAdmins, code, RestAdmin.class);
		ArrayList<Order> ords = new ArrayList<>();
		for(Restaurant restaurant : restAdmin.getRestaurants())
			ords.addAll(getOrdersByuRestaurant(restaurant.getCode()));

		return ords;
	}
	
	// adds a Customer (if exists -> throws exception)
	public boolean add(Customer customer) throws TargetObjectAlreadyExistException {
	    if (customer == null)
	        return false;

	    if (customers.contains(customer))
	        throw new TargetObjectAlreadyExistException(customer.toString());

	    customers.add(customer);
	    return true;
	}

	// adds a RestAdmin (if exists -> throws exception)
	public boolean add(RestAdmin admin) throws TargetObjectAlreadyExistException {
	    if (admin == null)
	        return false;

	    if (restAdmins.contains(admin))
	        throw new TargetObjectAlreadyExistException(admin.toString());

	    restAdmins.add(admin);
	    return true;
	}

	// adds a Restaurant (if exists -> throws exception)
	public boolean add(Restaurant restaurant) throws TargetObjectAlreadyExistException {
	    if (restaurant == null)
	        return false;

	    if (restaurants.contains(restaurant))
	        throw new TargetObjectAlreadyExistException(restaurant.toString());

	    restaurants.add(restaurant);
	    return true;
	}
	
	
	public void add(Rider rider) throws TargetObjectAlreadyExistException {
		if (rider == null)
			return;
		if(riders.contains(rider)) 
			throw new TargetObjectAlreadyExistException(rider.toString());
		riders.add(rider);
	}

	// adds a Order by the needed parameters to do so (if exists -> does nothing)
	public int addOrder(int restaurantCode, int customerCode, double basePrice, Date date) throws CodedNotFoundException, InsufficientBalanceException, TargetObjectDoesntExistException {
		if (date == null)
			return -1;

		Customer customer = Coded.getCoded(customers, customerCode, Customer.class);
		if(customer == null)
			throw new CustomerNotFoundException(customerCode);
		
		Restaurant restaurant = Coded.getCoded(restaurants, restaurantCode, Restaurant.class);
		if(restaurant == null)
			throw new RestaurantNotFoundException(restaurantCode);

		int code = generateCode(CodedType.Order);
		Order order = new Order(code, customerCode, restaurant, date, basePrice);
		
		customer.buy(order.getFinalPrice());
		
		addOrderToCustomer(order.getClientCode(), order);
		return code;
	}

	// adds a Restaurant to a RestAdmin
	public void addRestToAdmin(int adminCode, int restCode) throws CodedNotFoundException, TargetObjectDoesntExistException {
		RestAdmin restAdmin = Coded.getCoded(restAdmins, adminCode, RestAdmin.class);
		Restaurant restaurant = Coded.getCoded(restaurants, restCode, Restaurant.class);

		restAdmin.addRestaurant(restaurant);
	}

	// adds an Order to a Rider (RestAdmin)
	public void addOrderToRider(String riderId, int orderCode) throws CodedNotFoundException, TargetObjectDoesntExistException, DeliveryPersonUnavailableException, RiderNotFoundException {
		Order order = Coded.getCoded(orders, orderCode, Order.class);
		if (order.getOrderStatus() != OrderStatus.Created)
			return;

		Rider rider = getRider(riderId);
		if (rider == null || !rider.isAvailable())
			return;

		if (order.getRiderId() != null) 
			getRider(order.getRiderId()).removeCurrentOrder();
		

		rider.setCurrentOrder(order);
	}

	// adds an Order to a Rider (RestAdmin)
	public void addOrderToRider(String riderId, int orderCode, int restAdminCode) throws CodedNotFoundException, TargetObjectDoesntExistException, DeliveryPersonUnavailableException, RiderNotFoundException {
		Order order = Coded.getCoded(orders, orderCode, Order.class);
		RestAdmin restAdmin = Coded.getCoded(restAdmins, restAdminCode, RestAdmin.class);
		if (!restAdmin.containsRestaurant(order.getRestaurantCode()))
			return;
		
		addOrderToRider(riderId, orderCode);
	}
	
	// Checks if contains Rider with the given id
	public boolean isContainsRider(String id) {
		for (Rider rider : riders) {
			if (rider.getId().equalsIgnoreCase(id)) {
				return true;
			}
		}
		return false;
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
	public boolean setBalanceToCustomer(int code, double balance) throws CodedNotFoundException, TargetObjectDoesntExistException {
		return getCustomer(code).setBalance(balance);
	}
	
	public void updateRestaurantRaiting(int code, double reiting) throws CodedNotFoundException, TargetObjectDoesntExistException {
		Restaurant restaurant = getRestaurant(code);
		restaurant.setRating(reiting);
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
	public void updateDeliveryStatus(String riderId, Date deliveryDate) throws RiderNotFoundException {
		Rider rider = getRider(riderId);
		Order order = rider.getCurrentOrder();
		if(order == null) return;
		
		if (deliveryDate == null && order.getOrderStatus() == OrderStatus.OnTheWay) return;
		if(deliveryDate != null)
			order.setDeliveringDate(deliveryDate);
		
		rider.changeCurrentOrderStatus();
	}
	
	// switch between open and close restaurant by code
	public void changeRestaurantStatus(int code) throws CodedNotFoundException, TargetObjectDoesntExistException {
		Restaurant restaurant = getRestaurant(code);
		restaurant.setOpen(!restaurant.isOpen());
	}

	public void sortRidersByDeliverdCount() {
		riders.sort((r1,r2) -> Double.compare(r1.getDeliverdOrders().size(), r2.getDeliverdOrders().size()));
	}
	
	public void sortCustomersByName() {
		customers.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));
	}
	
	public void sortOrdersByDate() {
		orders.sort((o1,o2) -> o1.getOrderingDate().compareTo(o2.getOrderingDate()));
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

	public Hashtable<Integer, ArrayList<Restaurant>> getSelectedRestaurantsByCustomer() {
		return selectedRestaurantsByCustomer;
	}

	public HashMap<Integer, Double> getTotalSpentByCustomer() {
		return totalSpentByCustomer;
	}

	public void setSystemAdministrator(Admin systemAdministrator) {
		this.systemAdministrator = systemAdministrator;
	}

	public void setRestAdmins(ArrayList<RestAdmin> restAdmins) {
		this.restAdmins = restAdmins;
	}

	public void setRestaurants(ArrayList<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public void setCustomers(ArrayList<Customer> customers) {
		this.customers = customers;
	}

	public void setRiders(ArrayList<Rider> riders) {
		this.riders = riders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}

	public void setOrdersByCustomer(HashMap<Integer, ArrayList<Order>> ordersByCustomer) {
		this.ordersByCustomer = ordersByCustomer;
	}

	public void setSelectedRestaurantsByCustomer(Hashtable<Integer, ArrayList<Restaurant>> selectedRestaurantsByCustomer) {
		this.selectedRestaurantsByCustomer = selectedRestaurantsByCustomer;
	}

	public void setTotalSpentByCustomer(HashMap<Integer, Double> totalSpentByCustomer) {
		this.totalSpentByCustomer = totalSpentByCustomer;
	}

	@Override
	public String toString() {
		return "DeliveryDataBase [systemAdministrator=" + systemAdministrator + ", restAdmins=" + restAdmins
				+ ", restaurants=" + restaurants + ", customers=" + customers + ", riders=" + riders + ", orders="
				+ orders + ", ordersByCustomer=" + ordersByCustomer + ", selectedRestaurantsByCustomer="
				+ selectedRestaurantsByCustomer + ", totalSpentByCustomer=" + totalSpentByCustomer + "]";
	}
}
