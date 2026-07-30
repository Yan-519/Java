package HW1;

import java.util.Arrays;

public class Rider {
	private String id, name, lastName;
	private String phoneNumber;
	private String vehicle;
	private boolean isAvailable;
	private Order[] orders;
	
	
	public Rider(String id, String name, String lastName, String phoneNumber, String vehicle) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.vehicle = vehicle;
		this.isAvailable = true;
		this.orders = new Order[0];
	}
	
	// Adds an order (if doesnt exist already)
	public void addOrder(Order order) {
		if (getOrder(order.getOrderCode()) == null)
		{
			orders = Arrays.copyOf(orders, orders.length + 1);
			orders[orders.length -1] = order;
		}
	}
	
	// returns an order by code (if cant -> null)
	public Order getOrder(int code) {
		for(Order order : orders)
			if(order.getOrderCode() == code)
				return order;
		return null;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getVehicle() {
		return vehicle;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public Order[] getOrders() {
		return orders;
	}

	public void setId(String id) {
    	if(id.length() != 9) return;
    	
    	for(char chr : id.toCharArray())
    		if(!Character.isDigit(chr))
    			return;
    	
    	this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		if(Customer.isValidePhoneNumber(phoneNumber))
		this.phoneNumber = phoneNumber;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public void setOrders(Order[] orders) {
		this.orders = orders;
	}
	
}
