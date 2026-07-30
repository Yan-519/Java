package Testing;

import java.util.Random;
import java.util.stream.Stream;

public class Main {
	
	public static final Random random = new Random();
	public static final String ALL_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static final int len = ALL_STRING.length();
	
	public static String genWinKey() {
		
		String str = "";
		
		for (int i = 0; i < 5; i++) {
			if(i != 0)
				str += "-";
			for (int j = 0; j < 5; j++)
				str += ALL_STRING.charAt(random.nextInt(len));
		}
		
		return str;
	}
	
	
	
	public static void main(String[] args) {

		Stream.iterate(new long[]{0, 1}, f -> new long[]{f[1], f[0] + f[1]})
	      .map(f -> f[0])
	      .limit(100)
	      .forEach(System.out::println);

		
//		HashSet<String> set = new HashSet<String>();
//
//		for (int i = 0; set.size() == i && i < 100; i++) {
//			set.add(genWinKey());
//		}
//		
//		System.out.println(set.size());
//		for (String string : set) {
//			System.out.println(string);
//		}
	}

}
