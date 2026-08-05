package HW2.DataObjects;

import java.util.ArrayList;
import java.util.Random;

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
		return Coded.tryGetCoded(codeds, code) != null;
	}
	
	// returns the Coded woth the given code from the given list
	public static <T extends Coded> T tryGetCoded(ArrayList<T> codeds, int code) {
		for (T coded : codeds) {
	        if (coded.getCode() == code) {
	        	return coded;
	        }
	    }
		return null;
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
}
