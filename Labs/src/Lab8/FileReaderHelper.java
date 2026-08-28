package Lab8;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

import javafx.util.Pair;

public class FileReaderHelper {
	private static final String DIR = "src" + File.separator + "Lab8";

	public static int[] readCurrentYear() throws FileNotFoundException {
		
		Scanner in = new Scanner(new File(DIR, "CurrentYear.txt"));
		
		int[] res = new int[12];

		try {
			while(in.hasNextInt()) 
				res[in.nextInt() - 1] = in.nextInt();
			
		}catch (Exception e) {
		}
		finally {
			in.close();
		}
		return res;
	}
	
	public static void writeYear(int year, int sum) throws IOException {
		
		ArrayList<Pair<Integer, Integer>> prev = new ArrayList<Pair<Integer,Integer>>(); 
		
		Scanner in = new Scanner(new File(DIR, "Years.txt"));

		try {
			while(in.hasNextInt()) 
				prev.add(new Pair<Integer, Integer>(in.nextInt(), in.nextInt()));
			
		}catch (Exception e) {
		}
		finally {
			in.close();
		}
		
		prev.sort((p1,p2) -> Integer.compare(p2.getKey(), p1.getKey()));

		FileWriter fileWriter = new FileWriter(new File(DIR, "Years.txt"));
		fileWriter.write(year + " " + sum + "\n");
		
		for (Pair<Integer, Integer> pair : prev) 
			fileWriter.write(pair.getKey() + " " + pair.getValue() + "\n");
			
		fileWriter.close();
	}
	
	public static TreeMap<Employee, Result> getWorkersResult() throws FileNotFoundException{
		
		TreeMap<Employee, Result> lst = new TreeMap<Employee, Result>();
		
		Scanner in = new Scanner(new File(DIR, "Workers.txt"));
		
		try {
			while(in.hasNextInt()) {
				int month = in.nextInt();
				int ID = in.nextInt();
				int count = in.nextInt();
				int result = in.nextInt();
				
				lst.put(new Employee(ID, "Employee " + ID), new Result(month, count, result));
			}
		}catch (Exception e) {
		}
		finally {
			in.close();
		}
		
		return lst;
	}
	
	
}
