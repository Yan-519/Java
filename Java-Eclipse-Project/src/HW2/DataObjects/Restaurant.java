package HW2.DataObjects;

public class Restaurant extends Coded {
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
		return "Restaurant [code=" + code + ", name=" + name + ", kitchenType=" + kitchenType + ", rating=" + rating
				+ ", isOpen=" + isOpen + ", baseDeliveryFee=" + baseDeliveryFee + "]";
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
}