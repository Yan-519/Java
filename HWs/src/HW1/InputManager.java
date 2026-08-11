package HW1;

import java.util.Scanner;

public class InputManager {

	public static final Scanner inScanner = new Scanner(System.in);
	public static final int BACK = Main.BACK;
	
	public enum NumberSign{ALL, POSITIVE, NOT_NEGATIVE }

	// returns an integer from the console with a stop condition that can be turned off
	public static int inputInt(String text, boolean withBack) {
		return inputInt(text, NumberSign.ALL, withBack);
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
		if(withBack) text += " (" + BACK +" to go back): ";
		
		while(true) {
			System.out.print(text);
			while(!inScanner.hasNextInt()) {
				inScanner.next();
				System.out.print(text);
			}
			out = inScanner.nextInt();
			inScanner.nextLine();
			if(out == BACK && withBack) return BACK;
			
			switch (inputSign) {
			case ALL: return out;
			case POSITIVE:
				if( 0 < out) {
					return out;
				}
				break;
			case NOT_NEGATIVE:
				if( 0 <= out) {
					return out;
				}
				break;
			default:
				break;
			}

		}
	}

	// returns an double from the console
	public static double inputDouble(String text) {
		return inputDouble(text, NumberSign.ALL);
	}
	// returns an double from the console with a spasific sign (inputSign) that is under the given maximum
	public static double inputDouble(String text, NumberSign inputSign, double max) {
		double out;
		while (max < (out = inputDouble(text, inputSign)) && max != BACK);
		return out;
	}
	// returns an double from the console with a spasific sign (inputSign)
	public static double inputDouble(String text, NumberSign inputSign) {
		double out;
		text += " (" + BACK +" to go back): ";
		
		while(true) {
			System.out.print(text);
			while(!inScanner.hasNextDouble()) {
				inScanner.next();
				System.out.print(text);
			}
			out = inScanner.nextDouble();
			inScanner.nextLine();
			if(out == BACK) return BACK;
			
			switch (inputSign) {
			case ALL: return out;
			case POSITIVE:
				if( 0 < out) {
					return out;
				}
				break;
			case NOT_NEGATIVE:
				if( 0 <= out) {
					return out;
				}
				break;
			default:
				break;
			}

		}
	}

	// returns a rerult of a Yes\No quastion (boolean)
	public static Boolean inputBool(String text) {
		text += " (Yes/No): ";
	    String in = inputString(text, false);
	    while (!in.equalsIgnoreCase("Yes") && !in.equalsIgnoreCase("No")  && !in.equalsIgnoreCase(Integer.toString(BACK))) {
			in = inputString(text, false);
		}
	    if(in.equalsIgnoreCase(Integer.toString(BACK)))
	    	return null;
	    return in.equalsIgnoreCase("Yes");
	}
	

	// retruns a String with a stop condition
	public static String inputString(String text, boolean isName) {
		if(isName)
			text += " (without numbers)";

		text += " (" + BACK +" to go back): ";
		
		String str;
		while (true) {
			System.out.print(text);
			str = inScanner.nextLine().trim();
			
			if(str.isEmpty()) continue;
			else if(str.equalsIgnoreCase(Integer.toString(BACK))) 
				return str;
			
			else if(isName) {
				boolean isGood = true;
				for(char chr : str.toCharArray()) {
					if(!Character.isLetter(chr) && chr != ' ') {
						isGood = false;
						break;
					}
				}
				if(isGood)
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
	    while(!(id = inputString("Enter ID (positive 9 digits)", false)).equalsIgnoreCase(Integer.toString(BACK))) {
	    	if(id.length() != 9) continue;
	    	boolean isGood = true;
	    	for(char chr : id.toCharArray())
	    		if(!Character.isDigit(chr))
	    			isGood = false;
	    	if(isGood) break;
	    }
	    if (id.equalsIgnoreCase(Integer.toString(BACK))) return null;

	    String phone;
	    while (!Customer.isValidePhoneNumber(phone = inputString("Enter phone number (IL)", false)) &&
	            !phone.equals(Integer.toString(BACK)));
	    if (phone.equals(Integer.toString(BACK))) return null;

	    String firstName = inputString("Enter first name", true);
	    while( firstName.indexOf(' ') != -1 && firstName.substring(0, firstName.indexOf(' ')).isEmpty() &&
	    		!firstName.equals(Integer.toString(BACK)) )
	    	firstName = inputString("Enter first name", true);
	    if (firstName.equals(Integer.toString(BACK))) return null;

	    String lastName = inputString("Enter last name", true);
	    if (lastName.equals(Integer.toString(BACK))) return null;

	    String vehicle = inputString("Enter vehicle", false);
	    if (vehicle.equals(Integer.toString(BACK))) return null;
	    
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
	    while (!Customer.isValidePhoneNumber(phone = inputString("Enter the new phone number (IL)", false)) &&
	            !phone.equals(Integer.toString(BACK)));
	    if (phone.equals(Integer.toString(BACK))) return null;

	    String firstName = inputString("Enter first name", true);
	    while( firstName.indexOf(' ') != -1 && firstName.substring(0, firstName.indexOf(' ')).isEmpty() &&
	    		!firstName.equals(Integer.toString(BACK)) )
	    	firstName = inputString("Enter first name", true);
	    if (firstName.equals(Integer.toString(BACK))) return null;

	    String lastName = inputString("Enter last name", true);
	    if (lastName.equals(Integer.toString(BACK))) return null;

	    String street = inputString("Enter street", false);
	    if (street.equals(Integer.toString(BACK))) return null;

	    String town = inputString("Enter town", true);
	    if (town.equals(Integer.toString(BACK))) return null;

	    int zip = inputInt("Enter new ZIP code (positive)", NumberSign.POSITIVE);
	    if (zip == BACK) return null;

	    String email;
	    while(!Customer.isValideEmail( email = inputString("Enter email", false)) && !email.equals(Integer.toString(BACK)));
	    if (email.equals(Integer.toString(BACK))) return null;

	    double balance = inputDouble("Enter the balance (not negative)", NumberSign.NOT_NEGATIVE);
	    if (balance == BACK) return null;

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
	public static Admin createRestAdmin(int code) {
	    System.out.println("create RestAdmin");

	    String name = inputString("Enter name", true);
        if (name.equals(Integer.toString(BACK))) return null;

	    String username = inputString("Enter username", false);
	    if (username.equals(Integer.toString(BACK))) return null;

	    int password = inputInt("Enter password");
	    if (password == BACK) return null;

	    return new RestAdmin(
	            name,
	            code,
	            username,
	            password
	    );
	}

	// returns a new Restaurant
	public static Restaurant createRestaurant(int code) {
	    System.out.println("create Restaurant");

	    String name = inputString("Enter restaurant name", true);
	    if (name.equals(Integer.toString(BACK))) return null;

	    String kitchenType = inputString("Enter kitchen type", false);
	    if (kitchenType.equals(Integer.toString(BACK))) return null;

	    double rating = inputDouble("Enter rating (0 to 5)", NumberSign.NOT_NEGATIVE, 5);
	    if (rating == BACK) return null;

	    double fee = inputDouble("Enter base delivery fee (not negative)", NumberSign.NOT_NEGATIVE);
	    if (fee == BACK) return null;

	    return new Restaurant(
	            code,
	            name,
	            kitchenType,
	            rating,
	            inputBool("Is the restaurant open?"),
	            fee
	    );
	}

	// returns a new FastFoodRestaurant
	public static FastFoodRestaurant createFastFoodRestaurant(int code) {
	    System.out.println("create FastFoodRestaurant");

	    String name = inputString("Enter restaurant name", true);
	    if (name.equals(Integer.toString(BACK))) return null;

	    String kitchenType = inputString("Enter kitchen type", false);
	    if (kitchenType.equals(Integer.toString(BACK))) return null;

	    double rating = inputDouble("Enter rating (0 to 5)", NumberSign.NOT_NEGATIVE, 5);
	    if (rating == BACK) return null;

	    double fee = inputDouble("Enter base delivery fee (not negative)", NumberSign.NOT_NEGATIVE);
	    if (fee == BACK) return null;

	    int prepTime = inputInt("Enter average preparing time (not negative)", NumberSign.NOT_NEGATIVE);
	    if (prepTime == BACK) return null;

	    double expressCost = inputDouble("Enter additional cost for express delivery (not negative)", NumberSign.NOT_NEGATIVE);
	    if (expressCost == BACK) return null;

	    return new FastFoodRestaurant(
	            code,
	            name,
	            kitchenType,
	            rating,
	            inputBool("Is the restaurant open?"),
	            fee,
	            prepTime,
	            expressCost
	    );
	}

	// returns a new PremiumRestaurant
	public static PremiumRestaurant createPremiumRestaurant(int code) {
	    System.out.println("create PremiumRestaurant");

	    String name = inputString("Enter restaurant name", true);
	    if (name.equals(Integer.toString(BACK))) return null;

	    String kitchenType = inputString("Enter kitchen type", false);
	    if (kitchenType.equals(Integer.toString(BACK))) return null;

	    double rating = inputDouble("Enter rating (0 to 5)", NumberSign.NOT_NEGATIVE, 5);
	    if (rating == BACK) return null;

	    double fee = inputDouble("Enter base delivery fee (not negative)", NumberSign.NOT_NEGATIVE);
	    if (fee == BACK) return null;

	    double minOrder = inputDouble("Enter minimum order cost (not negative)", NumberSign.NOT_NEGATIVE);
	    if (minOrder == BACK) return null;

	    double commission = inputDouble("Enter additional commission percentage per order (not negative)", NumberSign.NOT_NEGATIVE);
	    if (commission == BACK) return null;

	    return new PremiumRestaurant(
	            code,
	            name,
	            kitchenType,
	            rating,
	            inputBool("Is the restaurant open?"),
	            fee,
	            minOrder,
	            commission
	    );
	}

	// returns a new Date
	public static Date createDate() {
	    System.out.println("create Date");

	    int day;
	    do {
	        day = inputInt("Enter day (1-31)");
	        if (day == BACK) return null;
	    } while (day < 1 || day > 31);

	    int month;
	    do {
	        month = inputInt("Enter month (1-12)");
	        if (month == BACK) return null;
	    } while (month < 1 || month > 12);

	    int year;
	    do {
	        year = inputInt("Enter year (2000-2026)");
	        if (year == BACK) return null;
	    } while (year < 2000 || month > 2026);

	    return new Date(day, month, year);
	}
	
	// retruns a new Date that is later than the given one
	public static Date createDateAfterDate(Date before) {
		Date date;
		while((date = createDate()) != null && !date.isAfter(before)) {
			System.out.println("The delivering date should be aftre the order create date");
		}
		return date;
	}
}
