package HW3.DataObjects;

import java.util.ArrayList;
import java.util.Random;

import HW3.Exceptions.CodedNotFoundException;
import HW3.Exceptions.CustomerNotFoundException;
import HW3.Exceptions.OrderNotFoundException;
import HW3.Exceptions.RestAdminNotFoundException;
import HW3.Exceptions.RestaurantNotFoundException;
import HW3.Exceptions.TargetObjectDoesntExistException;

public abstract class Coded {
	public static final Random random = new Random();
	
	protected final int code;

	public Coded(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	// generate a random positive code that isn't in the given list
	public static <T extends Coded> int generateCode(ArrayList<T> list) {
		int code;
		while (isContains(list, code = random.nextInt() & Integer.MAX_VALUE));
		return code;
	}
	
	// returns if the given code appear in the given list
	public static <T extends Coded> boolean isContains(ArrayList<T> codeds, int code) {
		for (T coded : codeds) 
	        if (coded.getCode() == code) 
	        	return true;
	        
	    return false;
	}
	
	// returns the Coded woth the given code from the given list
	public static <T extends Coded> T tryGetCoded(ArrayList<T> codeds, int code, Class<T> name) throws CodedNotFoundException, TargetObjectDoesntExistException {
		for (T coded : codeds) {
	        if (coded.getCode() == code) {
	        	return coded;
	        }
	    }

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
		if(obj instanceof Coded coded) {
			return coded.getCode() == this.code;
		}
		return false;
	}
	
	@Override
	public final int hashCode() {
		return code;
	}

	@Override
	public String toString() {
		return "Coded [code=" + code + "]";
	}
	
	
}
