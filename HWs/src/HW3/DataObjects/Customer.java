package HW3.DataObjects;

import HW3.Utils.DataChecker;

public class Customer extends Coded {
	
	private String name, lastName;
	private String street, town, zipCode;
	private String phoneNumber, emain;
	private double creditBalance;

	
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
	
	public String getZipCode() {
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


	public void setCreditBalance(double creditBalance) {
		this.creditBalance = creditBalance;
	}


	@Override
	public String toString() {
		return "Customer [name=" + name + ", lastName=" + lastName + ", street=" + street + ", town=" + town
				+ ", zipCode=" + zipCode + ", phoneNumber=" + phoneNumber + ", emain=" + emain + ", creditBalance="
				+ creditBalance + ", code=" + code + "]";
	}
	
	
}
