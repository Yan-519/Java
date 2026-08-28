package Lab8;

import java.util.Map.Entry;
import java.util.TreeMap;

public class Q2 {
	
	
	public static void main(String[] args) {
		try {
			// part 1
			int[] arr = FileReaderHelper.readCurrentYear();
			
			int sum = 0;
			for (int i : arr)
				sum += i;
			
			System.out.println("This year sum is " + sum);
			
			FileReaderHelper.writeYear(2022, sum);
			
			// part 2
			TreeMap<Employee, Result> map = FileReaderHelper.getWorkersResult();
			for (Entry<Employee, Result> entry : map.entrySet()) 
				System.out.println(entry.getKey() + " -> " + entry.getValue());
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
