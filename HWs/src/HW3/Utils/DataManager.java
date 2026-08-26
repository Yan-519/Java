package HW3.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import HW3.DeliveryDataBase;
import HW3.DataObjects.Admin;
import HW3.DataObjects.Customer;
import HW3.DataObjects.Date;
import HW3.DataObjects.FastFoodRestaurant;
import HW3.DataObjects.Order;
import HW3.DataObjects.PremiumRestaurant;
import HW3.DataObjects.RestAdmin;
import HW3.DataObjects.Restaurant;
import HW3.DataObjects.Rider;
import HW3.DataObjects.Helpers.ConvertorHolder;
import HW3.DataObjects.Helpers.StringConverter;
import HW3.Exceptions.DeliveryPersonUnavailableException;
import HW3.Exceptions.OrderNotFoundException;
import HW3.Exceptions.TargetObjectDoesntExistException;

public class DataManager {
	private static final String DIR = "src" + File.separator + "HW3" + File.separator + "DataFiles";

	// initial program values:
	public static DeliveryDataBase initialDataBase() throws OrderNotFoundException {

		// Customers
		Customer[] customers = {
		    new Customer(1, "Alice", "Green", "Oak St", "New York", "10001", "051111111", "alice@mail.com", 100),
		    new Customer(2, "Bob", "White", "Maple St", "Chicago", "10002", "052222222", "bob@mail.com", 50),
		    new Customer(3, "Charlie", "Black", "Pine St", "Boston", "10003", "053333333", "charlie@mail.com", 80),
		    new Customer(4, "Diana", "Gray", "Hill St", "Dallas", "10004", "054444444", "diana@mail.com", 120),
		    new Customer(5, "Ethan", "Stone", "Lake St", "Miami", "10005", "055555555", "ethan@mail.com", 30),
		    new Customer(6, "Fiona", "King", "River St", "Seattle", "10006", "056666666", "fiona@mail.com", 200),
		    new Customer(7, "George", "Hall", "Elm St", "Denver", "10007", "057777777", "george@mail.com", 90),
		    new Customer(8, "Helen", "Young", "Sun St", "Austin", "10008", "058888888", "helen@mail.com", 70),
		    new Customer(9, "Ian", "Scott", "Main St", "Phoenix", "10009", "059999999", "ian@mail.com", 150),
		    new Customer(10, "Julia", "Lee", "King St", "Atlanta", "10010", "051010101", "julia@mail.com", 40)
		};

		// Restaurants
		Restaurant[] restaurants = {
		    new Restaurant(1, "Bella Pizza", "Italian", 4.6, true, 10),
		    new Restaurant(2, "Dragon Wok", "Chinese", 4.4, true, 12),
		    new Restaurant(3, "Tokyo Sushi", "Japanese", 4.8, true, 15),
		    
		    new Restaurant(4, "Burger House", "American", 4.2, false, 9),
		    
		    new Restaurant(5, "Taco Land", "Mexican", 4.3, true, 8),
		    new Restaurant(6, "Curry King", "Indian", 4.7, true, 11),
		    new Restaurant(7, "Greek Corner", "Greek", 4.1, true, 10),
		    new Restaurant(8, "Paris Cafe", "French", 4.5, true, 13),
		    
		    new Restaurant(9, "Thai Spice", "Thai", 4.6, false, 14),
		    
		    new Restaurant(10, "Mediterraneo", "Mediterranean", 4.9, true, 16),

		    new FastFoodRestaurant(101, "McBurger", "Fast Food", 4.1, true, 8, 10, 5),
		    new FastFoodRestaurant(102, "Quick Pizza", "Pizza", 4.2, true, 9, 15, 4),
		    new FastFoodRestaurant(103, "Hot Chicken", "Chicken", 4.0, true, 7, 12, 3),
		    
		    new FastFoodRestaurant(104, "Burger Max", "Burgers", 4.3, false, 8, 11, 6),
		    
		    new FastFoodRestaurant(105, "Fast Taco", "Mexican", 4.4, true, 7, 8, 2),
		    new FastFoodRestaurant(106, "Express Sushi", "Japanese", 4.5, true, 10, 14, 7),
		    new FastFoodRestaurant(107, "Wrap Zone", "Wraps", 4.0, true, 6, 9, 3),
		    new FastFoodRestaurant(108, "Fry King", "Fast Food", 3.9, true, 5, 7, 2),
		    new FastFoodRestaurant(109, "Pizza Box", "Pizza", 4.6, true, 9, 16, 5),
		    
		    new FastFoodRestaurant(110, "Snack Hub", "Snacks", 4.2, false, 6, 10, 4),

		    new PremiumRestaurant(201, "Royal Steak", "Steakhouse", 4.9, true, 20, 100, 15),
		    new PremiumRestaurant(202, "Golden Sushi", "Japanese", 4.8, true, 18, 120, 18),
		    new PremiumRestaurant(203, "Elite Pasta", "Italian", 4.7, true, 17, 90, 12),
		    
		    new PremiumRestaurant(204, "Luxury Grill", "Grill", 4.9, false, 22, 150, 20),
		    
		    new PremiumRestaurant(205, "Ocean Pearl", "Seafood", 5.0, true, 25, 200, 25),
		    new PremiumRestaurant(206, "Chef's Table", "French", 4.8, true, 19, 180, 18),
		    new PremiumRestaurant(207, "Prime Kitchen", "International", 4.7, true, 21, 130, 16),
		    new PremiumRestaurant(208, "Sky Lounge", "Fusion", 4.9, true, 24, 160, 19),
		    new PremiumRestaurant(209, "Black Truffle", "European", 4.8, true, 23, 170, 17),
		    new PremiumRestaurant(210, "Diamond Dining", "Fine Dining", 5.0, true, 30, 250, 30)
		};
		

		// Riders
		Rider[] riders = {
		    new Rider("123456789", "John", "Smith", "0511234567", "Motorcycle"),
		    new Rider("212345678", "David", "Brown", "0521234567", "Scooter"),
		    new Rider("312345678", "Michael", "Johnson", "0531234567", "Car"),
		    new Rider("412345678", "Daniel", "Wilson", "0541234567", "Bicycle"),
		    new Rider("512345678", "James", "Taylor", "0551234567", "Motorcycle")
		};
		
		// Orders
		Order[] orders = {
		    new Order(1001, customers[0].getCode(), restaurants[0], new Date(1, 2, 2026), 45.0),
		    new Order(1002, customers[0].getCode(), restaurants[10], new Date(3, 1, 2026), 32.5),
		    new Order(1003, customers[2].getCode(), restaurants[20], new Date(5, 1, 2026), 180.0),
		    new Order(1004, customers[3].getCode(), restaurants[5], new Date(8, 1, 2026), 67.0),
		    new Order(1005, customers[4].getCode(), restaurants[14], new Date(10, 1, 2026), 29.5),
		    
		    new Order(1006, customers[4].getCode(), restaurants[24], new Date(12, 1, 2026), 210.0),
		    new Order(1007, customers[6].getCode(), restaurants[2], new Date(15, 1, 2026), 58.0),
		    new Order(1008, customers[7].getCode(), restaurants[17], new Date(18, 1, 2026), 41.0),
		    new Order(1009, customers[7].getCode(), restaurants[28], new Date(20, 1, 2026), 155.0),
		    new Order(1010, customers[9].getCode(), restaurants[9], new Date(25, 1, 2026), 72.0)
		};
		
		// connect the orders to the riders
		try {
			riders[0].setCurrentOrder(orders[0]);
			riders[1].setCurrentOrder(orders[1]);
			riders[2].setCurrentOrder(orders[2]);
			riders[3].setCurrentOrder(orders[3]);
			riders[4].setCurrentOrder(orders[4]);
		} catch (DeliveryPersonUnavailableException e) {
			MessageBox.error(e);
		}

		// Admins
		RestAdmin[] admins = {
		    new RestAdmin(1, "Tom", "tom", "1111"),
		    new RestAdmin(2, "Sarah", "sarah", "2222"),
		    new RestAdmin(3, "Mike", "mike", "3333")
		};
		
		return new DeliveryDataBase(new Admin("Admin", "admin", "12345"), customers, restaurants, riders, orders, admins);
	}
	
	public static <T extends StringConverter<T>> ArrayList<ConvertorHolder<? extends T>> load(Class<T> c) 
			throws Exception{
		String fileName = c.getSimpleName().toUpperCase() + ".txt";
		
		File file = new File(DIR, fileName);
		
		if(!file.exists())
			throw new TargetObjectDoesntExistException(file.getPath());
		
		ArrayList<ConvertorHolder<? extends T>> res = new ArrayList<>();
		
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
				
		T empty = c.getDeclaredConstructor().newInstance();
		
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			ConvertorHolder<? extends T> t = empty.convert(line);
			if(t == null)
				throw new TargetObjectDoesntExistException(line);
			res.add(t);
			
		}
	
		bufferedReader.close();
		return res;
	}

	public static <T extends StringConverter<T>> void save(ArrayList<T> arr, Class<T> c) throws Exception {
		String fileName = c.getSimpleName().toUpperCase() + ".txt";
		
		File file = new File(DIR, fileName);
		file.createNewFile();
		
		BufferedWriter bufferedWriter = new BufferedWriter( new FileWriter(file));
		
		for (T t : arr) {
			if(t== null) 
				throw new TargetObjectDoesntExistException(c.getSimpleName());
			bufferedWriter.write(t.convert());
			bufferedWriter.newLine();
		}
		
		bufferedWriter.close();
	}
	
}
