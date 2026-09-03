package HW3.Utils;

import java.util.function.Predicate;
import java.util.function.Supplier;

import HW3.DeliveryDataBase;
import HW3.DataObjects.*;
import HW3.Exceptions.RiderNotFoundException;
import HW3.Utils.MessageBox.NumberSign;

public class DataSelector {
	
	// DeliveryDataBase pointer
	private static DeliveryDataBase deliveryDataBase;
	// set the DeliveryDataBase pointer once
	public static void setDeliveryDataBase(DeliveryDataBase deliveryDataBase) {
		DataSelector.deliveryDataBase = deliveryDataBase;
	}

	// filter input and gives explanation message
	public static <T> T dataFilter(Supplier<T> inFunction, Predicate<T> ifPredicate, String message){
		T val = inFunction.get();
		if(val == null) return null;
		while(!ifPredicate.test(val)) {
			MessageBox.Info(message);
			val = inFunction.get();
			if(val == null) return null;
		}
		return val;
	}
	
	// select Restaurant
	public static Restaurant selectRestaurant() {
		if(deliveryDataBase.getRestaurants().isEmpty()) {
			MessageBox.Info("No restaurants available.");
			return null;
		}
		Integer code;
		while(true) {
			code = MessageBox.inputINT(null, "Enter restaurant code", NumberSign.POSITIVE);
			if(code == null) return null;
			try {
				return deliveryDataBase.getRestaurant(code);
			} catch (Exception e) {
				MessageBox.error(e);
			}
		}
	}
	
	// select RestAdmin
	public static RestAdmin selectRestAdmin() {
		if(deliveryDataBase.getRestAdmins().isEmpty()) {
			MessageBox.Info("No restaurant admins available.");
			return null;
		}
		Integer code;
		while(true) {
			code = MessageBox.inputINT(null, "Enter restaurant admin code", NumberSign.POSITIVE);
			
			if(code == null) {
				return null;
			}
			try {
				return deliveryDataBase.getRestAdmin(code);
			} catch (Exception e) {
				MessageBox.error(e);
			}
		}
	}
	
	// select Rider
	public static Rider selectRider() {
		if(deliveryDataBase.getRiders().isEmpty()) {
			MessageBox.Info("No riders available.");
			return null;
		}
		String ID;
		while(true) {
			ID = MessageBox.inputSTR(null,  "Enter rider ID", null);
			
			if(ID == null) return null;
			
			try {
				return deliveryDataBase.getRider(ID);
			} catch (RiderNotFoundException e) {
				MessageBox.error(e);
			}
		}
	}
	
	// select Order
	public static Order selectOrder() {
		if(deliveryDataBase.getOrders().isEmpty()) {
			MessageBox.Info("No orders available.");
			return null;
		}
		Integer code;
		while(true) {
			code = MessageBox.inputINT(null, "Enter order code", NumberSign.POSITIVE);
			
			if(code == null) 
				return null;
			
			try {
				return deliveryDataBase.getOrder(code);
			} catch (Exception e) {
				MessageBox.error(e);
			} 
		}
	}
	
	// select Customer
	public static Customer selectCustomer() {
		if(deliveryDataBase.getCustomers().isEmpty()) {
			MessageBox.Info("No customers available.");
			return null;
		}
		Integer code;
		while(true) {
			code = MessageBox.inputINT(null, "Enter customer code", NumberSign.POSITIVE);
			if(code == null) 
				return null;
			
			try {
				return deliveryDataBase.getCustomer(code);
			} catch (Exception e) {
				MessageBox.error(e);
			}
		}
	}
}
