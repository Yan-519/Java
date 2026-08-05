package HW2.Utils;

import HW2.DeliveryDataBase;
import HW2.DataObjects.*;
import HW2.DataObjects.Order.DeliveryStatus;
import HW2.DeliveryDataBase.CodedType;

public class DataSelector {
	
	// DeliveryDataBase pointer
	private static DeliveryDataBase deliveryDataBase = null;
	// set the DeliveryDataBase pointer once
	public static void setDeliveryDataBase(DeliveryDataBase deliveryDataBase) {
		DataSelector.deliveryDataBase = deliveryDataBase;
	}

	// select Open Restaurant
	public static Restaurant selectOpenRestaurant() {
		Restaurant restaurant = DataSelector.selectRestaurant();
		if(restaurant == null) return null;
		while(!restaurant.isOpen()) {
			System.out.println("The selected restaurannt is close");
			
			restaurant = DataSelector.selectRestaurant();
			if(restaurant == null) return null;
		}
		return restaurant;
	}
	
	// select Restaurant
	public static Restaurant selectRestaurant() {
		int code;
		while(true) {
			code = InputManager.inputInt("Enter restaurant code");
			
			if(code == InputManager.BACK_INT) {
				return null;
			}
			if(deliveryDataBase.isContains(code, CodedType.Restaurant))
				return deliveryDataBase.tryGetRestaurant(code);
			System.out.println("Restaurant with code " + code + " not found. Please try again.");
		}
	}
	
	// select RestAdmin
	public static RestAdmin selectRestAdmin() {
		int code;
		while(true) {
			code = InputManager.inputInt("Enter restaurant admin code");
			
			if(code == InputManager.BACK_INT) {
				return null;
			}
			if(deliveryDataBase.isContains(code, CodedType.RestAdmin))
				return deliveryDataBase.tryGetRestAdmin(code);
			System.out.println("Restaurant admin with code " + code + " not found. Please try again.");
		}
	}
	
	// select Available Rider
	public static Rider selectAveilableRider() {
		Rider rider = DataSelector.selectRider();
		if(rider == null) return null;
		
		while(!rider.isAvailable()) {
			System.out.println("Rider isnt aveilable");
			rider = DataSelector.selectRider();
			if(rider == null) return null;
		}
		return rider;
	}
	
	// select Rider
	public static Rider selectRider() {
		String ID;
		while(true) {
			ID = InputManager.inputString("Enter rider ID", false);
			
			if(ID.equalsIgnoreCase(InputManager.BACK_STR)) {
				return null;
			}
			if(deliveryDataBase.isContainsRider(ID))
				return deliveryDataBase.tryGetRider(ID);
			System.out.println("Rider with Id " + ID + " not found. Please try again.");
		}
	}
	
	// select  Created Order
	public static Order selectCreatedOrder() {
		Order order = DataSelector.selectOrder();
		if(order == null) return null;
		while(order.getDeliveryStatus() != DeliveryStatus.Created) {
			System.out.println("The order is already " + order.getDeliveryStatus());
			order = DataSelector.selectOrder();
			if(order == null) return null;
		}
		return order;
	}
	
	// select Order
	public static Order selectOrder() {
		int code;
		while(true) {
			code = InputManager.inputInt("Enter order code");
			
			if(code == InputManager.BACK_INT) {
				return null;
			}
			
			if(deliveryDataBase.isContains(code, CodedType.Order))
				return deliveryDataBase.tryGetOrder(code);
			System.out.println("Order with code " + code + " not found. Please try again.");
		}
	}
	
	// select Customer
	public static Customer selectCustomer() {
		int code;
		while(true) {
			code = InputManager.inputInt("Enter customer code");
			
			if(code == InputManager.BACK_INT) {
				return null;
			}
			
			if(deliveryDataBase.isContains(code, CodedType.Customer))
				return deliveryDataBase.tryGetCustomer(code);
			System.out.println("Customer with code " + code + " not found. Please try again.");
		}
	}
}
