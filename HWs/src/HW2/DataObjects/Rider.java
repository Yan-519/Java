package HW2.DataObjects;

import java.util.ArrayList;

import HW2.DataObjects.Order.OrderStatus;

public class Rider {
	private final String id;
	private String name, lastName;
	private String phoneNumber;
	private String vehicle;
	private boolean isAvailable;
	private ArrayList<Order> deliverdOrders;
	private Order currentOrder;	
	
	public Rider(String id, String name, String lastName, String phoneNumber, String vehicle) {
		this.id = id;
		this.name = name;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.vehicle = vehicle;
		
		this.isAvailable = true;
		
		deliverdOrders = new ArrayList<>();
		currentOrder = null;
	}
	
	// removes the current order of the rider
	public void removeCurrentOrder() {
		currentOrder = null;
		isAvailable = true;
	}
	
	// moving the delivery status forward toward delivering it
	public void changeCurrentOrderStatus() {
		if(currentOrder == null) return;
		
		if(currentOrder.getOrderStatus() == OrderStatus.Created)
			currentOrder.setOrderStatus(OrderStatus.OnTheWay);
		else {
			currentOrder.setOrderStatus( OrderStatus.Delivered);
			deliverdOrders.add(currentOrder);
			removeCurrentOrder();
		}
	}

	public String getId() {
		return id;
	}

	public ArrayList<Order> getDeliverdOrders() {
		return deliverdOrders;
	}

	public void setDeliverdOrders(ArrayList<Order> deliverdOrders) {
		this.deliverdOrders = deliverdOrders;
	}

	public Order getCurrentOrder() {
		return currentOrder;
	}

	public void setCurrentOrder(Order currentOrder) {
		if(currentOrder.getOrderStatus() == OrderStatus.Delivered) 
			return;
		this.currentOrder = currentOrder;
		currentOrder.setRiderId(id);
		
		isAvailable = currentOrder == null;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	@Override
	public String toString() {
		return "Rider [id=" + id + ", name=" + name + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber
				+ ", vehicle=" + vehicle + ", isAvailable=" + isAvailable + ", orders=" + deliverdOrders + "]";
	}
	
	@Override
	public final boolean equals(Object object) {
		return object instanceof Rider rider && rider.getId().equalsIgnoreCase(id);
		
	}
	
	@Override
	public final int hashCode() {
		return id.hashCode();
	}
}
