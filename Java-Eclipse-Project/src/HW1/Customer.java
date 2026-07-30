package HW1;

public class Customer {
	
	private int code;
	private String name, lastName, street, town;
	private int zipCode;
	private String phoneNumber;
	private String emain;
	private double creditBalance;
	// A value that indecates the Customer gender
	private String gender;
	// A value that indecates if the Customer has a degree
	private boolean isHasDegree;

	
	public Customer(int code, String name, String lastName, String street, String town, int zipCode, String phoneNum,
			String emain, double creditBalance) {		
		this.code = code;
		this.name = name;
		this.lastName = lastName;
		this.street = street;
		this.town = town;
		this.zipCode = zipCode;
		this.phoneNumber = phoneNum;
		this.emain = emain;
		this.creditBalance = creditBalance;
		this.gender = "Mail";
		this.isHasDegree = false;
	}

	
	public int getCode() {
		return code;
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
	
	public String getGender() {
		return gender;
	}
	
	public boolean isHasDegree() {
		return isHasDegree;
	}

	public void setCode(int code) {
		if(0<= code)
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
		if(isValidePhoneNumber(phoneNumber))
		this.phoneNumber = phoneNumber;
	}

	public void setEmain(String emain) {
		this.emain = emain;
	}

	public void setCreditBalance(double creditBalance) {
		this.creditBalance = creditBalance;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setHasDegree(boolean isHasDegree) {
		this.isHasDegree = isHasDegree;
	}
	
	// Checks if the phone number is valid (Exactly 10 digits, starts with 05)
	public static boolean isValidePhoneNumber(String phoneNumber) {
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
	public static boolean isValideEmail(String email) {
	    if (email == null || email.trim().length() < 5)
	        return false;

	    if (email.startsWith("@") || email.indexOf('@') != email.lastIndexOf('@') || email.endsWith("@"))
	        return false;

	    if (email.lastIndexOf('.') <=  email.indexOf('@'))
	        return false;

	    return true;
	}
}
