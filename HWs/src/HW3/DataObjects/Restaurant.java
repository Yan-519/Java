package HW3.DataObjects;

import HW3.DataObjects.Helpers.Coded;
import HW3.DataObjects.Helpers.ConvertorHolder;

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

	public Restaurant() {super(-1);}
	
	
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
	
	public boolean getIsOpen() {
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
	public String convert() {
		return joiner(
			code,
			name,
			kitchenType,
			rating,
			isOpen,
			baseDeliveryFee
		);
	}

	@Override
	public ConvertorHolder<? extends Restaurant> convert(String in) {
		try {
			if(!in.trim().split(" ")[6].contains("."))
				return (new FastFoodRestaurant()).convert(in);
			return (new PremiumRestaurant()).convert(in);
		} catch (Exception e) {
		}
		
		if (in == null || in.trim().isEmpty()) {
			return null;
		}

		String[] parts = in.trim().split(" ");
		if (parts.length < 6) {
			throw new IllegalArgumentException("Invalid input format for Restaurant: " + in);
		}

		int parsedCode = Integer.parseInt(parts[0]);
		String parsedName = parts[1].replace("_", " ");
		String parsedKitchenType = parts[2].replace("_", " ");
		double parsedRating = Double.parseDouble(parts[3]);
		boolean parsedIsOpen = Boolean.parseBoolean(parts[4]);
		double parsedBaseDeliveryFee = Double.parseDouble(parts[5]);

		return new ConvertorHolder<>( new Restaurant(parsedCode, parsedName, parsedKitchenType, parsedRating, parsedIsOpen, parsedBaseDeliveryFee));
	}
	
}