package HW2;

public class Customer extends Coded {
	
	private String name, lastName, street, town;
	private int zipCode;
	private String phoneNumber;
	private String emain;
	private double creditBalance;

	
	public Customer(int code, String name, String lastName, String street, String town, int zipCode, String phoneNum,
			String emain, double creditBalance) {	
		super(code);
		this.name = name;
		this.lastName = lastName;
		this.street = street;
		this.town = town;
		this.zipCode = zipCode;
		this.phoneNumber = phoneNum;
		this.emain = emain;
		this.creditBalance = creditBalance;
	}
	
	
	// spend the given double (if not go to negative as a result)
	public boolean buy(double price) {
		if(price <= creditBalance )
		{
			creditBalance -= price;
			return true;
		}
		return false;
	}
	
	// sets the balance (if not go to negative as a result)
	public boolean setBalance(double balance) {
		if( 0 <= balance )
			creditBalance = balance;
		return 0 <= balance;
	}
	
	public String getName() {
		return name;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getStreet() {
		return street;
	}
	
	public String getTown() {
		return town;
	}
	
	public int getZipCode() {
		return zipCode;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getEmain() {
		return emain;
	}
	
	public double getCreditBalance() {
		return creditBalance;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public void setZipCode(int zipCode) {
		if( 0 < zipCode)
		this.zipCode = zipCode;
	}

	public void setPhoneNumber(String phoneNumber) {
		if(isValidPhoneNumber(phoneNumber))
		this.phoneNumber = phoneNumber;
	}

	// Checks if the phone number is valid (Exactly 10 digits, starts with 05)
	public static boolean isValidPhoneNumber(String phoneNumber) {
	    if (phoneNumber == null || (phoneNumber.length() != 10 && phoneNumber.length() != 11))
	        return false;

	    if (phoneNumber.charAt(0) != '0' || phoneNumber.charAt(1) != '5' ||
	    		!Character.isDigit(phoneNumber.charAt(2)))
	        return false;
	    
	    if(phoneNumber.length() == 11 && phoneNumber.charAt(3) != '-')
	    	return false;

	    int i = 3;
	    if(phoneNumber.length() == 11)
	    	i = 4;
	    
	    for (; i < phoneNumber.length(); i++) {
	        char c = phoneNumber.charAt(i);
	        if (c < '0' || c > '9')
	            return false;
	    }

	    return true;
	}

	// Checks if the email is valid
	public static boolean isValidEmail(String email) {
	    if (email == null || email.trim().length() < 5)
	        return false;

	    if (email.startsWith("@") || email.indexOf('@') != email.lastIndexOf('@') || email.endsWith("@"))
	        return false;

	    if (email.lastIndexOf('.') <=  email.indexOf('@'))
	        return false;

	    return true;
	}
}
