package Testing;

import java.util.ArrayList;
import java.util.Random;

public class Coded {
	public static final int defaultVal = -1;
	public static final Random random = new Random();
	
	protected final int code;

	public Coded(int code) {
		if(0 < code)
			this.code = code;
		else this.code = defaultVal;
	}
	
	public int getCode() {
		return code;
	}
	
	public boolean isDefault() {
		return code == defaultVal;
	}
	
	public <T extends Coded> boolean isIn(ArrayList<T> codeds) {
		return codeds.contains(new Code(code));
	}
	
//	public boolean isIn(Coded[] codeds) {
//		return Coded.isContains(codeds, code);
//	}
//	
//	public static int generateCode(Coded[] codeds) {
//		int code;
//		while (isContains(codeds, code = Math.abs(random.nextInt())));
//		return code;
//	}
//	
//	public static boolean isContains(Coded[] codeds, int code) {
//		for(Coded coded : codeds)
//			if(coded != null && coded.getCode() == code)
//				return true;
//		return false;
//	}
	
	public static <T extends Coded> int generateCode(ArrayList<T> list) {
		int code;
		while (isContains(list, code = Math.abs(random.nextInt() - 2) + 1));
		return code;
	}
	
	public static <T extends Coded> boolean isContains(ArrayList<T> codeds, int code) {
		return codeds.contains(new Coded(code));
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Coded coded) {
			return coded.getCode() == this.code;
		}
		return false;
	}
}