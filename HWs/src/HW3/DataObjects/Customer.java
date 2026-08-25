package HW3.DataObjects;

import HW3.DataObjects.Helpers.Coded;
import HW3.DataObjects.Helpers.ConvertorHolder;
import HW3.Exceptions.InsufficientBalanceException;
import HW3.Utils.DataChecker;

public class Customer extends Coded<Customer> implements Comparable<Customer>{
	
	private String name, lastName;
	private String street, town, zipCode;
	private String phoneNumber, emain;
	private double balance;

	
	public Customer(int code, String name, String lastName, String street, String town, String zipCode, String phoneNum,
			String emain, double creditBalance) {	
		super(code);
		this.name = name;
		this.lastName = lastName;
		this.street = street;
		this.town = town;
		this.zipCode = zipCode;
		this.phoneNumber = phoneNum;
		this.emain = emain;
		this.balance = creditBalance;
	}
	
	public Customer() {super(-1);}
	
	// spend the given double (if not go to negative as a result)
	public void buy(double price) throws InsufficientBalanceException {
		if(price <= balance )
			balance -= price;
		
		throw new InsufficientBalanceException(balance, price);
	}
	
	// sets the balance (if not go to negative as a result)
	public void setBalance(double balance) throws InsufficientBalanceException {
		if( balance < 0)
			throw new InsufficientBalanceException(balance);

		this.balance = balance;
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
	
	public String getZipCode() {
		return zipCode;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getEmain() {
		return emain;
	}
	
	public double getBalance() {
		return balance;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public void setZipCode(String zipCode) {
		if(DataChecker.isValidZipCode(zipCode))
			this.zipCode = zipCode;
	}

	public void setPhoneNumber(String phoneNumber) {
		if(DataChecker.isValidPhoneNumber(phoneNumber))
		this.phoneNumber = phoneNumber;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public void setEmain(String emain) {
		this.emain = emain;
	}


	@Override
	public String toString() {
		return "Customer [name=" + name + ", lastName=" + lastName + ", street=" + street + ", town=" + town
				+ ", zipCode=" + zipCode + ", phoneNumber=" + phoneNumber + ", emain=" + emain + ", creditBalance="
				+ balance + ", code=" + code + "]";
	}


	@Override
	public int compareTo(Customer o) {
		return Double.compare(o.getBalance(), balance);
	}


	@Override
	public ConvertorHolder<Customer> convert(String in) {
		String[] tokens = in.trim().split(" ");
        if (tokens.length < 9) {
            throw new IllegalArgumentException("Invalid input format for Customer: " + in);
        }

        int parsedCode = Integer.parseInt(tokens[0].trim());
        String parsedName = tokens[1].replace("_", " ");
        String parsedLastName = tokens[2].replace("_", " ");
        String parsedStreet = tokens[3].replace("_", " ");
        String parsedTown = tokens[4].replace("_", " ");
        String parsedZipCode = tokens[5].replace("_", " ");
        String parsedPhone = tokens[6].replace("_", " ");
        String parsedEmail = tokens[7];
        double parsedBalance = Double.parseDouble(tokens[8].trim());
        
        return new ConvertorHolder<>(new Customer(parsedCode, parsedName, parsedLastName, parsedStreet, 
                parsedTown, parsedZipCode, parsedPhone, parsedEmail, parsedBalance));
	}


	@Override
	public String convert() {
		return joiner(code, name, lastName, street, town, zipCode, phoneNumber, emain, balance);
	}
	
	
}
