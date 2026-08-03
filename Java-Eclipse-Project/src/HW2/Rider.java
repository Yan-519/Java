package HW2;

import java.util.ArrayList;

public class Rider {
	private final String id;
	private String name, lastName;
	private String phoneNumber;
	private String vehicle;
	private boolean isAvailable;
	private ArrayList<Order> orders;
	
	
	public Rider(String id, String name, String lastName, String phoneNumber, String vehicle, boolean isAvailable) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.vehicle = vehicle;
		this.isAvailable = isAvailable;

		orders = new ArrayList<>();
	}


	public boolean isContainsOrder(int code) {
		return Coded.isContains(orders, code);
	}
	
	public void addOrder(Order order) {
		if(order != null && !orders.contains(order))
			this.orders.add(order);
	}
	
	public void removeOrder(int code) {
		if(!isContainsOrder(code)) return;
		orders.remove(Coded.tryGetCoded(orders, code));
	}

	public ArrayList<Order> getOrders() {
		return orders;
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

	
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
	
	public static boolean isValidId(String id) {
		if(id == null || id.length() != 9) return false;
		
		for(char chr : id.toCharArray())
			if(!Character.isDigit(chr))
				return false;
		
		return true;
	}

}
