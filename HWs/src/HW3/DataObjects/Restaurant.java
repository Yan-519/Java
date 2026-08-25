package HW3.DataObjects;

public class Restaurant extends Coded<Restaurant> {
    protected String name;
    protected String kitchenType;
    protected double rating;
    protected boolean isOpen;
    protected double baseDeliveryFee;
    
    
	public Restaurant(int code, String name, String kitchenType, double rating, boolean isOpen, double baseDeliveryFee) {
		super(code);
		this.name = name;
		this.kitchenType = kitchenType;
		this.rating = rating;
		this.isOpen = isOpen;
		this.baseDeliveryFee = baseDeliveryFee;
	}
	
	
	
	@Override
	public String toString() {
		return "Restaurant [name=" + name + ", kitchenType=" + kitchenType + ", rating=" + rating + ", isOpen=" + isOpen
				+ ", baseDeliveryFee=" + baseDeliveryFee + ", code=" + code + "]";
	}



	public String getName() {
		return name;
	}
	
	public String getKitchenType() {
		return kitchenType;
	}
	
	public double getRating() {
		return rating;
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public double getBaseDeliveryFee() {
		return baseDeliveryFee;
	}
	
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setKitchenType(String kitchenType) {
		this.kitchenType = kitchenType;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}


	public void setBaseDeliveryFee(double baseDeliveryFee) {
		this.baseDeliveryFee = baseDeliveryFee;
	}



	@Override
	public Restaurant convert(String in) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String convert() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}