package HW2;

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
	
	// generate a random positive code that isnt in the given list
	public static <T extends Coded> int generateCode(ArrayList<T> list) {
		int code;
		while (isContains(list, code = Math.abs(random.nextInt() - 2) + 1));
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
	public boolean equals(Object obj) {
		if(obj instanceof Coded coded) {
			return coded.getCode() == this.code;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return code;
	}
}
