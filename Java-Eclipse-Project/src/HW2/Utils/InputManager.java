package HW2.Utils;

import java.util.Scanner;

import HW2.DeliveryDataBase;
import HW2.DataObjects.*;

public class InputManager {
	// DeliveryDataBase pointer
	private static DeliveryDataBase deliveryDataBase = null;
	// set DeliveryDataBase pointer once
	public static void setDeliveryDataBase(DeliveryDataBase deliveryDataBase) {
		if(deliveryDataBase == null)
			InputManager.deliveryDataBase = deliveryDataBase;
	}

	// user input Scanner
	public static final Scanner inScanner = new Scanner(System.in);
	// stop condition for int
	public static final int BACK_INT = -1;
	// stop condition for String
	public static final String BACK_STR = Integer.toString(BACK_INT);

	// number input sign
	public enum NumberSign{ALL, POSITIVE, NOT_NEGATIVE }

	// returns an integer from the console with a stop condition that can be turned off
	public static int inputInt(String text, boolean withBack) {
		int out;
		if(withBack) 
			text += " (" + BACK_INT +" to go back): ";
		
		System.out.print(text);
		while(!inScanner.hasNextInt()) {
			inScanner.next();
			System.out.print(text);
		}
		out = inScanner.nextInt();
		inScanner.nextLine();
		return out;
	}
	
	// returns an integer from the console
	public static int inputInt(String text) {
		return inputInt(text, NumberSign.ALL, true);
	}
	
	// returns an integer from the console with a spasific sign (inputSign)
	public static int inputInt(String text, NumberSign inputSign) {
		return inputInt(text, inputSign, true);
	}
	
	// returns an integer from the console with a spasific sign (inputSign) with a stop condition that can be turned off
	public static int inputInt(String text, NumberSign inputSign, boolean withBack){
		int out;
		while(true) {
			out = inputInt(text, withBack);
			if(out == BACK_INT && withBack || inputSign == NumberSign.ALL) 
				return out;
			
			if(inputSign == NumberSign.POSITIVE && 0 < out)
				return out;
			
			if(inputSign == NumberSign.NOT_NEGATIVE && 0 <= out)
				return out;
		}
	}

	// returns an double from the console
	public static double inputDouble(String text) {
		text += " (" + BACK_INT +" to go back): ";
		System.out.print(text);
		while(!inScanner.hasNextDouble()) {
			inScanner.next();
			System.out.print(text);
		}
		double out = inScanner.nextDouble();
		inScanner.nextLine();
		return out;
	}
	// returns an double from the console with a specific sign (inputSign) that is under the given maximum
	public static double inputDouble(String text, NumberSign inputSign, double max) {
		double out;
		while (max < (out = inputDouble(text, inputSign)) && max != BACK_INT);
		return out;
	}
	
	// returns an double from the console with a specific sign (inputSign)
	public static double inputDouble(String text, NumberSign inputSign) {
		double out;
		
		while(true) {
			out = inputDouble(text);
			if(out == BACK_INT || inputSign == NumberSign.ALL) 
				return out;
			
			if(inputSign == NumberSign.POSITIVE && 0 < out)
				return out;
			
			if(inputSign == NumberSign.NOT_NEGATIVE && 0 <= out)
				return out;
		}
	}

	// returns a result of a Yes\No question with stop condition (boolean)
	public static Boolean inputBool(String text) {
		return inputBool(text, true);
	}

	// returns a result of a Yes\No question with stop condition buy choice (boolean)
	public static Boolean inputBool(String text, boolean withBack) {
		text += " (Yes/No): ";
	    String in = inputString(text, false, withBack);
	    while (!in.equalsIgnoreCase("Yes") && !in.equalsIgnoreCase("No")  && (!in.equalsIgnoreCase(BACK_STR) || !withBack)) {
			in = inputString(text, false, withBack);
		}
	    if(in.equalsIgnoreCase(BACK_STR) && withBack)
	    	return null;
	    return in.equalsIgnoreCase("Yes");
	}
	
	// Returns a String with a stop condition
	public static String inputString(String text, boolean isName) {
		return inputString(text, isName, true);
	}
	

	// Returns a String with a stop condition buy choice
	public static String inputString(String text, boolean isName, boolean isWithBack) {
		if(isName)
			text += " (without numbers)";
		if(isWithBack)
			text += " (" + BACK_INT +" to go back): ";
		
		String str;
		while (true) {
			System.out.print(text);
			str = inScanner.nextLine().trim();
			
			if(str.isEmpty()) continue;
			else if(str.equalsIgnoreCase(BACK_STR) && isWithBack) 
				return str;
			
			else if(isName) {
				if(DataChecker.isValidName(str))
					break;
			}
			else break;
		}
		return str;
	}

	// returns a new Rider
	public static Rider createRider() {
	    System.out.println("create Rider");

	    String id;
	    while(!(id = inputString("Enter ID (positive 9 digits)", false)).equalsIgnoreCase(BACK_STR)) {
	    	if(DataChecker.isValidId(id)) {
	    		if(deliveryDataBase.isContainsRider(id)) {
	    			System.out.println("Rider with ID " + id + " already exists. Please enter a different ID.");
	    		} else {
	    			break;
	    		}
	    	}
	    }
	    if (id.equalsIgnoreCase(BACK_STR)) return null;

	    String phone;
	    while (!DataChecker.isValidPhoneNumber(phone = inputString("Enter phone number (IL)", false)) &&
	            !phone.equalsIgnoreCase(BACK_STR));
	    if (phone.equalsIgnoreCase(BACK_STR)) return null;

	    String firstName = inputString("Enter first name", true);
	    if (firstName.equalsIgnoreCase(BACK_STR)) return null;
	    if(firstName.indexOf(' ') != -1)
	    	firstName = firstName.substring(0, firstName.indexOf(' '));

	    String lastName = inputString("Enter last name", true);
	    if (lastName.equalsIgnoreCase(BACK_STR)) return null;

	    String vehicle = inputString("Enter vehicle", false);
	    if (vehicle.equalsIgnoreCase(BACK_STR)) return null;
	    
	    Boolean isAveilable = inputBool("Is aveilable");
	    if(isAveilable == null) return null;

	    return new Rider(
	            id,
	            firstName,
	            lastName,
	            phone,
	            vehicle,
	            isAveilable
	    );
	}

	// returns a new Customer
	public static Customer creatCustomer(int code) {
	    System.out.println("create Customer");

	    String phone;
	    while (!DataChecker.isValidPhoneNumber(phone = inputString("Enter the new phone number (IL)", false)) &&
	            !phone.equalsIgnoreCase(BACK_STR))
	    	System.out.println("Not valid phone number");
	    if (phone.equalsIgnoreCase(BACK_STR)) return null;

	    String firstName = inputString("Enter first name", true);
	    if (firstName.equalsIgnoreCase(BACK_STR)) return null;
	    if(firstName.indexOf(' ') != -1)
	    	firstName = firstName.substring(0, firstName.indexOf(' '));

	    String lastName = inputString("Enter last name", true);
	    if (lastName.equalsIgnoreCase(BACK_STR)) return null;

	    String street = inputString("Enter street", false);
	    if (street.equalsIgnoreCase(BACK_STR)) return null;

	    String town = inputString("Enter town", true);
	    if (town.equalsIgnoreCase(BACK_STR)) return null;
	    
	    String zip;
	    while(!DataChecker.isValidZipCode(zip = inputString("Enter new ZIP code", false)) && !zip.equalsIgnoreCase(BACK_STR))
	    	System.out.println("Zip code must be not newgative 5-7 digits");
	    if(zip.equalsIgnoreCase(BACK_STR)) return null;

	    String email;
	    while(!DataChecker.isValidEmail( email = inputString("Enter email", false)) && !email.equalsIgnoreCase(BACK_STR))
	    	System.out.println("Not valid email");
	    if (email.equalsIgnoreCase(BACK_STR)) return null;

	    double balance = inputDouble("Enter the balance ");
	    if (balance == BACK_INT) return null;

	    return new Customer(
	            code,
	            firstName,
	            lastName,
	            street,
	            town,
	            zip,
	            phone,
	            email,
	            balance
	    );
	}

	// returns a new RestAdmin
	public static RestAdmin createRestAdmin(int code) {
	    System.out.println("create RestAdmin");

	    String name = inputString("Enter name", true);
        if (name.equalsIgnoreCase(BACK_STR)) return null;

	    String username = inputString("Enter username", false);
	    if (username.equalsIgnoreCase(BACK_STR)) return null;

	    int password = inputInt("Enter password");
	    if (password == BACK_INT) return null;

	    return new RestAdmin(
	    		code,
	            name,
	            username,
	            password
	    );
	}

	// returns a new Restaurant
	public static Restaurant createRestaurant(int code) {
	    System.out.println("create Restaurant");

	    String name = inputString("Enter restaurant name", true);
	    if (name.equalsIgnoreCase(BACK_STR)) return null;

	    String kitchenType = inputString("Enter kitchen type", false);
	    if (kitchenType.equalsIgnoreCase(BACK_STR)) return null;

	    double rating = inputDouble("Enter rating (0 to 5)", NumberSign.NOT_NEGATIVE, 5);
	    if (rating == BACK_INT) return null;

	    double fee = inputDouble("Enter base delivery fee (not negative)", NumberSign.NOT_NEGATIVE);
	    if (fee == BACK_INT) return null;

	    Boolean isOpen = inputBool("Is the restaurant open?");
	    if(isOpen == null) return null;
	    
	    return new Restaurant(
	            code,
	            name,
	            kitchenType,
	            rating,
	            isOpen,
	            fee
	    );
	}

	// returns a new FastFoodRestaurant
	public static FastFoodRestaurant createFastFoodRestaurant(int code) {
	    System.out.println("create FastFoodRestaurant");

	    String name = inputString("Enter restaurant name", true);
	    if (name.equalsIgnoreCase(BACK_STR)) return null;

	    String kitchenType = inputString("Enter kitchen type", false);
	    if (kitchenType.equalsIgnoreCase(BACK_STR)) return null;

	    double rating = inputDouble("Enter rating (0 to 5)", NumberSign.NOT_NEGATIVE, 5);
	    if (rating == BACK_INT) return null;

	    double fee = inputDouble("Enter base delivery fee (not negative)", NumberSign.NOT_NEGATIVE);
	    if (fee == BACK_INT) return null;

	    int prepTime = inputInt("Enter average preparing time (not negative)", NumberSign.NOT_NEGATIVE);
	    if (prepTime == BACK_INT) return null;

	    double expressCost = inputDouble("Enter additional cost for express delivery (not negative)", NumberSign.NOT_NEGATIVE);
	    if (expressCost == BACK_INT) return null;
	    
	    Boolean isOpen = inputBool("Is the restaurant open?");
	    if(isOpen == null) return null;

	    return new FastFoodRestaurant(
	            code,
	            name,
	            kitchenType,
	            rating,
	            isOpen,
	            fee,
	            prepTime,
	            expressCost
	    );
	}

	// returns a new PremiumRestaurant
	public static PremiumRestaurant createPremiumRestaurant(int code) {
	    System.out.println("create PremiumRestaurant");

	    String name = inputString("Enter restaurant name", true);
	    if (name.equalsIgnoreCase(BACK_STR)) return null;

	    String kitchenType = inputString("Enter kitchen type", false);
	    if (kitchenType.equalsIgnoreCase(BACK_STR)) return null;

	    double rating = inputDouble("Enter rating (0 to 5)", NumberSign.NOT_NEGATIVE, 5);
	    if (rating == BACK_INT) return null;

	    double fee = inputDouble("Enter base delivery fee (not negative)", NumberSign.NOT_NEGATIVE);
	    if (fee == BACK_INT) return null;

	    double minOrder = inputDouble("Enter minimum order cost (not negative)", NumberSign.NOT_NEGATIVE);
	    if (minOrder == BACK_INT) return null;

	    double commission = inputDouble("Enter additional commission percentage per order (not negative)", NumberSign.NOT_NEGATIVE);
	    if (commission == BACK_INT) return null;

	    Boolean isOpen = inputBool("Is the restaurant open?");
	    if(isOpen == null) return null;

	    return new PremiumRestaurant(
	            code,
	            name,
	            kitchenType,
	            rating,
	            isOpen,
	            fee,
	            minOrder,
	            commission
	    );
	}

	// returns a new Date
	public static Date createDate() {
	    System.out.println("create Date");

	    int year;
	    do {
	        year = inputInt("Enter year (2000-2026)");
	        if (year == BACK_INT) return null;
	    } while (year < 2000 || year > 2026);

	    int month;
	    do {
	        month = inputInt("Enter month (1-12)");
	        if (month == BACK_INT) return null;
	    } while (month < 1 || month > 12);
	    
	    int maxDay = DataChecker.getDaysInMonth(year, month);
	    int day;
	    do {
	        day = inputInt("Enter day (1-"+ maxDay +")");
	        if (day == BACK_INT) return null;
	    } while (day < 1 || day > maxDay);


	    return new Date(day, month, year);
	}
	
	// Returns a new Date that is later than the given one
	public static Date createDateAfterDate(Date before) {
		Date date;
		while((date = createDate()) != null && !date.isAfter(before)) {
			System.out.println("The delivering date should be aftre the order create date");
		}
		return date;
	}
}
