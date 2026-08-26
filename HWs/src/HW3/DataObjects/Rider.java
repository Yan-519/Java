package HW3.DataObjects;

import java.util.ArrayList;

import HW3.DataObjects.Helpers.ConvertorHolder;
import HW3.DataObjects.Helpers.StringConverter;
import HW3.DataObjects.Order.OrderStatus;
import HW3.Exceptions.DeliveryPersonUnavailableException;
import HW3.Exceptions.TargetObjectAlreadyExistException;
import HW3.Exceptions.TargetObjectDoesntExistException;

public class Rider extends StringConverter<Rider> {
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
	

	public Rider() {id = "";}

	public void addDeliverdOrder(Order order) throws TargetObjectDoesntExistException, TargetObjectAlreadyExistException {
		if(order == null)
			throw new TargetObjectDoesntExistException("Deliverd order");
			
		if(deliverdOrders.contains(order))
			throw new TargetObjectAlreadyExistException(order.toString());
		
		deliverdOrders.add(order);
	}

	// removes the current order of the rider
	public void removeCurrentOrder() {
		currentOrder = null;
		isAvailable = true;
	}
	
	// moving the delivery status forward toward delivering it
	public void changeCurrentOrderStatus() {
		if(currentOrder == null) return;
		
		if(currentOrder.getStatus() == OrderStatus.Created)
			currentOrder.setStatus(OrderStatus.OnTheWay);
		else {
			currentOrder.setStatus( OrderStatus.Delivered);
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

	public void setCurrentOrder(Order currentOrder) throws DeliveryPersonUnavailableException {
		if(currentOrder == null) return;
		
		if(!isAvailable)
			throw new DeliveryPersonUnavailableException(name, lastName);

		this.currentOrder = currentOrder;
		currentOrder.setRiderId(id);
		
		isAvailable = false;
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

	public boolean getIsAvailable() {
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
				+ ", vehicle=" + vehicle + ", isAvailable=" + isAvailable + ", deliverdOrders=" + deliverdOrders
				+ ", currentOrder=" + currentOrder + "]";
	}

	@Override
	public final boolean equals(Object object) {
		return object instanceof Rider rider && rider.getId().equalsIgnoreCase(id);
		
	}
	
	@Override
	public final int hashCode() {
		return id.hashCode();
	}

	@Override
	public String convert() {
		ArrayList<Integer> orderCodes = new ArrayList<>(deliverdOrders.stream().map(o-> o.getCode()).toList());
		return joiner(
			id,
			name,
			lastName,
			phoneNumber,
			vehicle,
			orderCodes,
			currentOrder == null ? "null" : currentOrder.getCode()
		);
	}

	@Override
	public ConvertorHolder<Rider> convert(String in) throws DeliveryPersonUnavailableException {
		if (in == null || in.trim().isEmpty()) 
			return null;
		

		String[] parts = in.trim().split(" ");
		if (parts.length < 7) 
			throw new IllegalArgumentException("Invalid input format for Rider: " + in);
		

		String parsedId = parts[0].replace("_", " ");
		String parsedName = parts[1].replace("_", " ");
		String parsedLastName = parts[2].replace("_", " ");
		String parsedPhoneNumber = parts[3].replace("_", " ");
		String parsedVehicle = parts[4].replace("_", " ");

		ArrayList<Integer> parsedDeliveredOrders = new ArrayList<>();
		if (!parts[5].equals("none")) 
			for (String codeStr : parts[5].split(",")) 
				if (!codeStr.trim().isEmpty()) 
					parsedDeliveredOrders.add(Integer.parseInt(codeStr));


		Integer parsedCurrentOrder = null;
		if (!parts[6].equals("null")) 
			parsedCurrentOrder = Integer.valueOf(parts[6]);
		

		Rider rider = new Rider(parsedId, parsedName, parsedLastName, parsedPhoneNumber, parsedVehicle);

		return new ConvertorHolder<>( rider, parsedDeliveredOrders, parsedCurrentOrder);
	}
}
