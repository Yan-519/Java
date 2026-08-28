package HW3;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;

import HW3.DataObjects.*;
import HW3.DataObjects.Helpers.Coded;
import HW3.DataObjects.Helpers.ConvertorHolder;
import HW3.DataObjects.Order.OrderStatus;
import HW3.Exceptions.*;

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
	
	public final Comparator<Restaurant> restComparator = (r1, r2) -> Double.compare(r2.getRating(), r1.getRating());
	private final Comparator<Order> ordComparator = (o1, o2) -> Double.compare(o2.getFinalPrice(), o1.getFinalPrice()); 

	
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

//	public DeliveryDataBase(Admin systemAdministrator, Customer[] customers, Restaurant[] restaurants, Rider[] riders,
//			Order[] orders, RestAdmin[] restAdmins) throws TargetObjectAlreadyExistException {
//
//		this(systemAdministrator);
//		
//		this.customers = new ArrayList<>(Arrays.asList(customers));
//		this.restaurants = new ArrayList<>(Arrays.asList(restaurants));
//		this.riders = new ArrayList<>(Arrays.asList(riders));
//		this.restAdmins = new ArrayList<>(Arrays.asList(restAdmins));
//		
//		
//		setOrders(new ArrayList<Order>(Arrays.asList(orders)));
//	}

	// add Order To Customer
	public void addOrderToCustomer(int customerCode, Order order) throws TargetObjectAlreadyExistException {
	    if (order == null) return;
	    if (orders.contains(order)) 
	    	throw new TargetObjectAlreadyExistException(order.toString());
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
	public void removeOrder(int code) throws CodedNotFoundException, TargetObjectDoesntExistException, RiderNotFoundException, InsufficientBalanceException {
		Order order = getOrder(code);
		if (order.getStatus() == OrderStatus.Delivered) return;
		
		Customer customer = getCustomer(order.getClientCode());
	    Restaurant restaurant = getRestaurant(order.getRestaurantCode());

	    if (!ordersByCustomer.containsKey(order.getClientCode()) || !ordersByCustomer.get(order.getClientCode()).contains(order))
	    	throw new TargetObjectDoesntExistException("Order :" + code, "ordersByCustomer, ordersByCustomer");
	    
	    if(order.getRiderId() != null) 
	    	getRider(order.getRiderId()).removeCurrentOrder();
	    
		

	    double backMoney = order.getFinalPrice() * (order.getStatus() == OrderStatus.Created ? 1.0 : 0.5);

	    double currentTotal = totalSpentByCustomer.getOrDefault(order.getClientCode(), 0.0);
	    totalSpentByCustomer.put(order.getClientCode(), Math.max(0.0, currentTotal - backMoney));

	    customer.setBalance(customer.getBalance() + backMoney);

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
			if (restaurant.getIsOpen() && restaurant.getKitchenType().equalsIgnoreCase(kitchenType)) {
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

	
	// checks if the data matches to the systemAdministrator data
	public boolean logIntoAdmin(String userName, String password) {
		return systemAdministrator.getUserName().equalsIgnoreCase(userName) && systemAdministrator.getPassword().equals(password);
	}

	// returns the RestAdmin by user name and password
	public RestAdmin getRestAdmin(String userName, String password) throws RestAdminNotFoundException {
		for (RestAdmin restAdmin : restAdmins) {
			if (restAdmin.getUserName().equalsIgnoreCase(userName) && restAdmin.getPassword().equals(password)) {
				return restAdmin;
			}
		}
		throw new RestAdminNotFoundException(userName, password);
	}

	// returns the RestAdmin by code 
	public RestAdmin getRestAdmin(int code) throws CodedNotFoundException, TargetObjectDoesntExistException {
		return Coded.getCoded(restAdmins, code, RestAdmin.class);
	}

	// returns the Rider by id 
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
	
	
	// adds a Customer (if exists -> throws exception)
	public void add(Customer customer) throws TargetObjectAlreadyExistException {
	    if (customer == null)
	    	throw new NullPointerException("Added object cant be null");

	    if (customers.contains(customer))
	        throw new TargetObjectAlreadyExistException(customer.toString());

	    customers.add(customer);
	}


	// adds a Restaurant (if exists -> throws exception)
	public void add(Restaurant restaurant) throws TargetObjectAlreadyExistException {
	    if (restaurant == null)
	    	throw new NullPointerException("Added object cant be null");

	    if (restaurants.contains(restaurant))
	        throw new TargetObjectAlreadyExistException(restaurant.toString());

	    restaurants.add(restaurant);
	}
	
	// add s Rider (if exists -> throws exception)
	public void add(Rider rider) throws TargetObjectAlreadyExistException {
		if (rider == null)
	    	throw new NullPointerException("Added object cant be null");
		if(riders.contains(rider)) 
			throw new TargetObjectAlreadyExistException(rider.toString());
		riders.add(rider);
	}

	// adds a Order by the needed parameters to do so (if exists -> does nothing)
	public int addOrder(int restaurantCode, int customerCode, double basePrice, Date date) throws CodedNotFoundException, InsufficientBalanceException, TargetObjectDoesntExistException, TargetObjectAlreadyExistException {
		if (date == null)
			throw new TargetObjectDoesntExistException("Orderring date");

		Customer customer = Coded.getCoded(customers, customerCode, Customer.class);
		Restaurant restaurant = Coded.getCoded(restaurants, restaurantCode, Restaurant.class);

		int code = generateCode(CodedType.Order);
		Order order = new Order(code, customerCode, restaurant, date, basePrice);
		
		
		addOrderToCustomer(order.getClientCode(), order);
		customer.buy(order.getFinalPrice());
		return code;
	}

	// adds a Restaurant to a RestAdmin
	public void addRestToAdmin(int adminCode, int restCode) throws CodedNotFoundException, TargetObjectDoesntExistException {
		RestAdmin restAdmin = Coded.getCoded(restAdmins, adminCode, RestAdmin.class);
		Restaurant restaurant = Coded.getCoded(restaurants, restCode, Restaurant.class);

		restAdmin.addRestaurant(restaurant);
	}

	// adds an Order to a Rider (RestAdmin)
	public void addOrderToRider(String riderId, int orderCode) throws Exception {
		Order order = Coded.getCoded(orders, orderCode, Order.class);
		if (order.getStatus() != OrderStatus.Created)
			throw new Exception("Can assing to a rider only Created orders ");

		Rider rider = getRider(riderId);
		
		if (!rider.getIsAvailable())
			throw new DeliveryPersonUnavailableException(rider.getName(), rider.getLastName());

		if (order.getRiderId() != null) 
			getRider(order.getRiderId()).removeCurrentOrder();
		

		rider.setCurrentOrder(order);
	}

	// adds an Order to a Rider (RestAdmin)
	public void addOrderToRider(String riderId, int orderCode, int restAdminCode) throws Exception {
		Order order = Coded.getCoded(orders, orderCode, Order.class);
		RestAdmin restAdmin = Coded.getCoded(restAdmins, restAdminCode, RestAdmin.class);
		if (!restAdmin.containsRestaurant(order.getRestaurantCode()))
			throw new TargetObjectDoesntExistException("RestAdmin ("+ restAdminCode +") isnt in control of Restaurant (" + order.getRestaurantCode() +")");
		
		addOrderToRider(riderId, orderCode);
	}
	
	// Checks if contains Rider with the given id
	public boolean isContainsRider(String id) {
		return riders.stream().anyMatch(r -> r.getId().equalsIgnoreCase(id));
	}
	
	// returns a unique code (not negative) for the given type
	public int generateCode(CodedType type) throws TargetObjectDoesntExistException {
		switch (type) {
			case RestAdmin:
				return Coded.generateCode(restAdmins);

			case Restaurant:
				return Coded.generateCode(restaurants);

			case Customer:
				return Coded.generateCode(customers);

			case Order:
				return Coded.generateCode(orders);
				
			default: throw new TargetObjectDoesntExistException("CodedType " + type);
		}
	}
	
	// updating the balance of a customer by his code
	public void setBalanceToCustomer(int code, double balance) throws InsufficientBalanceException, CodedNotFoundException, TargetObjectDoesntExistException  {
		getCustomer(code).setBalance(balance);
	}
	
	// Updating the raiting of a restaurant by code
	public void updateRestaurantRaiting(int code, double reiting) throws CodedNotFoundException, TargetObjectDoesntExistException {
		Restaurant restaurant = getRestaurant(code);
		restaurant.setRating(reiting);
	}
	
	// get all open restaurants
	public ArrayList<Restaurant> getOpenRestaurants(){
		ArrayList<Restaurant> res = new ArrayList<Restaurant>();
		for(Restaurant restaurant: restaurants)
			if(restaurant.getIsOpen())
				res.add(restaurant);
		return res;
	}
	

	// update the delivery status (add delivering date)
	public void updateDeliveryStatus(String riderId, Date deliveryDate) throws RiderNotFoundException, TargetObjectDoesntExistException {
		Rider rider = getRider(riderId);
		Order order = rider.getCurrentOrder();
		if(order == null) 
			throw new TargetObjectDoesntExistException("Rider current order", riderId);
		
		if (deliveryDate == null && order.getStatus() == OrderStatus.OnTheWay) 
			throw new TargetObjectDoesntExistException("Delivering date");
		if(deliveryDate != null)
			order.setDeliveringDate(deliveryDate);
		
		rider.changeCurrentOrderStatus();
	}
	
	// switch between open and close restaurant by code
	public void changeRestaurantStatus(int code) throws CodedNotFoundException, TargetObjectDoesntExistException {
		Restaurant restaurant = getRestaurant(code);
		restaurant.setOpen(!restaurant.getIsOpen());
	}

	public void sortRidersByDeliverdCount() {
		riders.sort((r1,r2) -> Integer.compare(r1.getDeliverdOrders().size(), r2.getDeliverdOrders().size()));
	}
	
	public void sortCustomersByName() {
		customers.sort((c1, c2) -> c1.getName().compareTo(c2.getName()));
	}
	
	public void sortOrdersByDate() {
		orders.sort((o1,o2) -> o1.getOrderingDate().compareTo(o2.getOrderingDate()));
	}
	
	public void sortCustomersByBalance() {
		customers = new ArrayList<Customer>(customers.stream().sorted().toList());
	}
	
	public void sortRestaurantsByRaiting() {
	    restaurants.sort(restComparator);
	}

	public void sortOrdersByFinalPrice() {
	    orders.sort(ordComparator);
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
	
	// Loads RestAdmins from memory with their restaurants
	public void loadRestAdmins(ArrayList<ConvertorHolder<? extends RestAdmin>> restAdmins) throws CodedNotFoundException, TargetObjectDoesntExistException {
		for(ConvertorHolder<? extends  RestAdmin> convertorHolder : restAdmins) {
			RestAdmin restAdmin = convertorHolder.output;
			for(Integer code : convertorHolder.codes)
				restAdmin.addRestaurant(getRestaurant(code));
			
			this.restAdmins.add(restAdmin);
		}
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
	
	// Loads Riders from memo with their orders
	public void loadRiders(ArrayList<ConvertorHolder<? extends  Rider>> riders)
			throws TargetObjectDoesntExistException, TargetObjectAlreadyExistException, CodedNotFoundException, DeliveryPersonUnavailableException {
		for(ConvertorHolder<? extends Rider> convertorHolder : riders) {
			Rider rider = convertorHolder.output;
			for(Integer code : convertorHolder.codes)
				rider.addDeliverdOrder(getOrder(code));
			
			if(convertorHolder.additional != null)
				rider.setCurrentOrder(getOrder(convertorHolder.additional));
			
			this.riders.add(rider);
		}
	}

	public void setOrders(ArrayList<Order> orders) throws TargetObjectAlreadyExistException {
		for (Order order : orders)
			addOrderToCustomer(order.getClientCode(), order);
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
