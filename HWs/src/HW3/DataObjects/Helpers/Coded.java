package HW3.DataObjects.Helpers;

import java.util.ArrayList;

import HW3.DataObjects.Customer;
import HW3.DataObjects.Order;
import HW3.DataObjects.RestAdmin;
import HW3.DataObjects.Restaurant;
import HW3.Exceptions.CodedNotFoundException;
import HW3.Exceptions.CustomerNotFoundException;
import HW3.Exceptions.OrderNotFoundException;
import HW3.Exceptions.RestAdminNotFoundException;
import HW3.Exceptions.RestaurantNotFoundException;
import HW3.Exceptions.TargetObjectDoesntExistException;

public abstract class Coded<E> extends StringConvertertable<E> {
	
	protected final int code;

	public Coded(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	// generate a positive code that isn't in the given list
	public static <T extends Coded<?>> int generateCode(ArrayList<T> list) {
		if(list.isEmpty()) return 1;
		
		int code = list.stream().max((c1,c2) -> Integer.compare(c1.getCode(), c2.getCode())).get().getCode() + 1;
		code = Math.max(1, code);
		while (isContains(list, code))
			code++;
		return code;
	}
	
	// returns if the given code appear in the given list
	public static <T extends Coded<?>> boolean isContains(ArrayList<T> codeds, int code) {
		for (T coded : codeds) 
	        if (coded.getCode() == code) 
	        	return true;
	        
	    return false;
	}
	
	// returns the Coded with the given code from the given list
	public static <T extends Coded<?>> T getCoded(ArrayList<T> codeds, int code, Class<T> name) 
			throws CodedNotFoundException, TargetObjectDoesntExistException {
		for (T coded : codeds) 
	        if (coded.getCode() == code) 
	        	return coded;

		if (name == RestAdmin.class) 
			throw new RestAdminNotFoundException(code);
		else if (name == Restaurant.class) 
			throw new RestaurantNotFoundException(code);
		
		else if (name == Customer.class)
			throw new CustomerNotFoundException(code);

		else if (name == Order.class)
			throw new OrderNotFoundException(code);
		
		throw new TargetObjectDoesntExistException(name.getSimpleName() +": " + code);
	}
	
	@Override
	public final boolean equals(Object obj) {
		return obj instanceof Coded coded && coded.getCode() == this.code;
	}
	
	@Override
	public final int hashCode() {
		return code;
	}

	@Override
	public abstract String toString();
}
