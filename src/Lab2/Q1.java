package Lab2;

//import Lab2.Company;
import java.util.Scanner;

public class Q1 {
	
	public static final Scanner inScanner = new Scanner(System.in);

	// input of an int
	public static int input(String text){
		System.out.print(text);
		while(!inScanner.hasNextInt()) {
			inScanner.next();
			System.out.print(text);
		}
		return inScanner.nextInt();
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Company[] companys = new Company[2];
		
		for(int i = 0; i < companys.length; i++) {
			System.out.println("Company " + (i+1) + ":" );
			int id = input("Enter Id (int): ");
			System.out.print("Enter name: ");
			String name = inScanner.next();
			
			Driver[] drivers = new Driver[5];
			for(int j = 0; j < drivers.length; j++) {
				System.out.println("Driver " + (j+1) + ":");
				int driverId = input("Enter Id (int): ");
				System.out.print("Enter name: ");
				String driverName = inScanner.next();
				int salary, trip;
				while((salary = input("Enter monthly salary (not negative int): ")) < 0);
				while((trip = input("Enter monthly trips (not negative int): ")) < 0);
				
				drivers[j] = new Driver(driverId, driverName, salary, trip); 
			}
			companys[i] = new Company(id, name, drivers);
		}
		
		
		for(int i = 0; i < companys.length; i++) {
			System.out.println("Company " + (i+1) + ":" );
			
			for(int j = 0; j < companys[i].getDrivers().length; j++) {
				if(companys[i].getDrivers()[j].isEfficient()) {
					System.out.println("Above 150 driver: " + companys[i].getDrivers()[j]);
					
				}
			}
			System.out.println("Best driver: " + companys[i].bestDriver());
			System.out.println();
		}
	}

}
