package HW3.DataObjects;

import HW3.DataObjects.Helpers.ConvertorHolder;

public class FastFoodRestaurant extends Restaurant {
	private int averagePreparingTimeInMinutes;
	private double additionalCostForExpressDelivery;
	
	
	public FastFoodRestaurant(int code, String name, String kitchenType, double rating, boolean isOpen,
			double baseDeliveryFee, int averagePreparingTimeInMinutes, double additionalCostForExpressDelivery) {
		super(code, name, kitchenType, rating, isOpen, baseDeliveryFee);
		this.averagePreparingTimeInMinutes = Math.max(averagePreparingTimeInMinutes, 0);
		this.additionalCostForExpressDelivery = Math.max(additionalCostForExpressDelivery, 0);
	}

	
	
	public FastFoodRestaurant() {
		super();
		// TODO Auto-generated constructor stub
	}



	@Override
	public String toString() {
		return "FastFoodRestaurant [averagePreparingTimeInMinutes=" + averagePreparingTimeInMinutes
				+ ", additionalCostForExpressDelivery=" + additionalCostForExpressDelivery + ", code=" + code
				+ ", name=" + name + ", kitchenType=" + kitchenType + ", rating=" + rating + ", isOpen=" + isOpen
				+ ", baseDeliveryFee=" + baseDeliveryFee + "]";
	}
	

	public int getAveragePreparingTimeInMinutes() {
		return averagePreparingTimeInMinutes;
	}

	public double getAdditionalCostForExpressDelivery() {
		return additionalCostForExpressDelivery;
	}

	public void setAveragePreparingTimeInMinutes(int averagePreparingTimeInMinutes) {
		if( 0 <= averagePreparingTimeInMinutes)
		this.averagePreparingTimeInMinutes = averagePreparingTimeInMinutes;
	}
	
	public void setAdditionalCostForExpressDelivery(double additionalCostForExpressDelivery) {
		if(0 <= additionalCostForExpressDelivery)
		this.additionalCostForExpressDelivery = additionalCostForExpressDelivery;
	}
	
	@Override
	public String convert() {
		return joiner(
			code,
			name,
			kitchenType,
			rating,
			isOpen,
			baseDeliveryFee,
			averagePreparingTimeInMinutes,
			additionalCostForExpressDelivery
		);
	}

	@Override
	public ConvertorHolder<FastFoodRestaurant> convert(String in) {
		if (in == null || in.trim().isEmpty()) {
			return null;
		}

		String[] parts = in.trim().split(" ");
		if (parts.length < 8) {
			throw new IllegalArgumentException("Invalid input format for FastFoodRestaurant: " + in);
		}

		int parsedCode = Integer.parseInt(parts[0]);
		String parsedName = parts[1].replace("_", " ");
		String parsedKitchenType = parts[2].replace("_", " ");
		double parsedRating = Double.parseDouble(parts[3]);
		boolean parsedIsOpen = Boolean.parseBoolean(parts[4]);
		double parsedBaseDeliveryFee = Double.parseDouble(parts[5]);
		int parsedPrepTime = Integer.parseInt(parts[6]);
		double parsedExpressFee = Double.parseDouble(parts[7]);

		return new ConvertorHolder<>( new FastFoodRestaurant(
			parsedCode, parsedName, parsedKitchenType, parsedRating,
			parsedIsOpen, parsedBaseDeliveryFee, parsedPrepTime, parsedExpressFee
		));
	}
}
