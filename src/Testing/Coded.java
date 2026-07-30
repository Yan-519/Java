package Testing;

import java.util.Random;

public class Coded {
	public static final int defaultVal = -1;
	
	private int code;

	public Coded(int code) {
		if(0 <= code)
			this.code = code;
		else this.code = defaultVal;
	}
	
	public int getCode() {
		return code;
	}
	
	public boolean isDefault() {
		return code == defaultVal;
	}
	
	public boolean isIn(Coded[] codeds) {
		return Coded.isContains(codeds, code);
	}
	
	public static int generateCode(Coded[] codeds) {
		Random random = new Random();
		int code;
		while (isContains(codeds, code = Math.abs(random.nextInt())));
		return code;
	}
	
	public static boolean isContains(Coded[] codeds, int code) {
		for(Coded coded : codeds)
			if(coded != null && coded.getCode() == code)
				return true;
		return false;
	}
}
