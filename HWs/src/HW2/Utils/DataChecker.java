package HW2.Utils;


public class DataChecker {

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
	    
	    for (; i < phoneNumber.length(); i++) 
	    	if(!Character.isDigit(phoneNumber.charAt(i)))
	            return false;
	    

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
	

	// checks if the ID is valid
	public static boolean isValidId(String id) {
		if(id == null || id.length() != 9) return false;
		
		for(char chr : id.toCharArray())
			if(!Character.isDigit(chr))
				return false;
		
		return true;
	}
	
	// checks if the name is valid
	public static boolean isValidName(String name) {
		for(char chr : name.toCharArray()) 
			if(!Character.isLetter(chr) && chr != ' ')
				return false;
		
		return true;
	}
	
	// checks if the zip code is valid
	public static boolean isValidZipCode(String zip) {
		if(zip.length() < 5 || 7 < zip.length()) return false;
		
		for (char c : zip.toCharArray())
			if(!Character.isDigit(c))
				return false;
		return true;
	}
	
	// get Days In Month by year and month
	public static int getDaysInMonth(int year, int month) {
		switch (month) {
		    case 1:  return 31;
		    case 2:  return ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0) ? 29 : 28);
		    case 3:  return 31;
		    case 4:  return 30; 
		    case 5:  return 31;
		    case 6:  return 30; 
		    case 7:  return 31; 
		    case 8:  return 31;
		    case 9:  return 30;
		    case 10: return 31;
		    case 11: return 30; 
		    case 12: return 31;
		    default: return -1;
		}
	}
	
}
