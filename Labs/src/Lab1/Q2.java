package Lab1;

import java.util.Scanner;

public class Q2 {
	// returns if small is in big and starts in idx
	public static Boolean isStartAt(String big, String small, int idx) {
		
		if (big.length() - idx < small.length())
			return false;
		
		for (int i = idx, j = 0; j < small.length(); i++, j++) {
			if(big.charAt(i) != small.charAt(j))
				return false;
		}
		return true;
	}

	// show count of st2 in st1
	public static void func(String st1, String st2) {
		int count = 0;
		
		for (int i = 0; i < st1.length(); i++) {
			if (isStartAt(st1, st2, i))
				count++;
		}
		
		System.out.println("st2 in st1 " + count + " times");
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		System.out.print("Enter st1: ");
		String st1 = in.nextLine();
		System.out.print("Enter st2: ");
		String st2 = in.nextLine();
		
		func(st1, st2);
		
		in.close();
	}

}
