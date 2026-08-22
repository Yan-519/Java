package HW3.Utils;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import HW3.DeliveryDataBase;
import HW3.DataObjects.*;
import HW3.DataObjects.Order.OrderStatus;
import HW3.Exceptions.RiderNotFoundException;

public class DataSelector {
	
	// DeliveryDataBase pointer
	private static DeliveryDataBase deliveryDataBase;
	// set the DeliveryDataBase pointer once
	public static void setDeliveryDataBase(DeliveryDataBase deliveryDataBase) {
		DataSelector.deliveryDataBase = deliveryDataBase;
	}
	
	
	public static <T> T dataFilter(Supplier<T> inFunction, Predicate<T> ifPredicate, Function<T, String> message){
		T val = inFunction.get();
		if(val == null) return null;
		while(!ifPredicate.test(val)) {
			MessageBox.Info(message.apply(val));
			val = inFunction.get();
			if(val == null) return null;
		}
		return val;
	}
	
	public static <T> T dataFilter(Supplier<T> inFunction, Predicate<T> ifPredicate, String message){
		return dataFilter(inFunction, ifPredicate, o -> message);
	}

	// select Open Restaurant
	public static Restaurant selectOpenRestaurant() {
		return dataFilter(DataSelector::selectRestaurant, r  -> r.isOpen() , "The selected restaurannt is close");
	}
	
	// select Restaurant
	public static Restaurant selectRestaurant() {
		int code;
		while(true) {
			code = InputManager.inputInt("Enter restaurant code");
			
			if(code == InputManager.BACK_INT) {
				return null;
			}
			try {
				return deliveryDataBase.tryGetRestaurant(code);
			} catch (Exception e) {
				MessageBox.error(e);
			}
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
			try {
				return deliveryDataBase.tryGetRestAdmin(code);
			} catch (Exception e) {
				MessageBox.error(e);
			}
		}
	}
	
	// select Available Rider
	public static Rider selectAveilableRider() {
		return dataFilter(DataSelector::selectRider, r -> r.isAvailable(), "Rider isnt aveilable");
	}
	
	// select Rider
	public static Rider selectRider() {
		String ID;
		while(true) {
			ID = InputManager.inputString("Enter rider ID", false);
			
			if(ID.equalsIgnoreCase(InputManager.BACK_STR)) {
				return null;
			}
			try {
				return deliveryDataBase.tryGetRider(ID);
			} catch (RiderNotFoundException e) {
				MessageBox.error(e);
			}
		}
	}
	
	// select  Created Order
	public static Order selectCreatedOrder() {
		return dataFilter(DataSelector::selectOrder, o -> o.getOrderStatus() == OrderStatus.Created,
				o -> "The order is already " + o.getOrderStatus());
	}
	
	// select Order
	public static Order selectOrder() {
		int code;
		while(true) {
			code = InputManager.inputInt("Enter order code");
			
			if(code == InputManager.BACK_INT) 
				return null;
			
			try {
				return deliveryDataBase.tryGetOrder(code);
			} catch (Exception e) {
				MessageBox.error(e);
			} 
		}
	}
	
	// select Customer
	public static Customer selectCustomer() {
		int code;
		while(true) {
			code = InputManager.inputInt("Enter customer code");
			
			if(code == InputManager.BACK_INT) 
				return null;
			
			try {
				return deliveryDataBase.tryGetCustomer(code);
			} catch (Exception e) {
				MessageBox.error(e);
			}
		}
	}
}
