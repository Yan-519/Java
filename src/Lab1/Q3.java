package Lab1;

import java.util.Arrays;
import java.util.Scanner;

public class Q3 {
	// the stop value for names
	private static final String STOP_NAME = "-1";

	public static void main(String[] args) {
		
		String[] names = new String[0];
		double[] greads = new double[0];
		
		Scanner in = new Scanner(System.in);
		
		System.out.print("Enter name (" + STOP_NAME + " to stop): ");
		String name = in.next();
		double avr;
		
		if (name.equalsIgnoreCase(STOP_NAME)) {
			System.out.println("NO imput");
			in.close();
			return;
		}

		System.out.print("Enter average grade: ");
		avr = in.nextDouble();
		
		// save name with max grade
		double maxG = avr;
		String maxN = name;
		
		// greads sum
		double totalSum = 0;
		// flag if first input
		Boolean isFirst = true;
		
		while (!name.equalsIgnoreCase(STOP_NAME)) {
			if(!isFirst) {
				System.out.print("Enter average grade: ");
				avr = in.nextDouble();
			}
			isFirst = false;
			
			names = Arrays.copyOf(names, names.length + 1);
			greads = Arrays.copyOf(greads, greads.length + 1);
			
			names[names.length -1] = name;
			greads[greads.length - 1] = avr;
			
			totalSum += avr;
			
			if(maxG < avr) {
				maxG = avr;
				maxN = name;
			}

			System.out.print("Enter name (" + STOP_NAME + " to stop): ");
			name = in.next();
		}
		in.close();
		
		System.out.println("Student with max greade: " + maxN);
		
		// show all above average 
		double totalAvrage = totalSum / names.length;
		System.out.println("Students with hieghr grade than avarage:");
		for(int i = 0; i < names.length; i++)
			if(totalAvrage < greads[i])
				System.out.println(names[i]);
		

	}

}
