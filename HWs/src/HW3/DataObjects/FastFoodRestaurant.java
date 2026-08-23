package HW3.DataObjects;

public class FastFoodRestaurant extends Restaurant {
	private int averagePreparingTimeInMinutes;
	private double additionalCostForExpressDelivery;
	
	
	public FastFoodRestaurant(int code, String name, String kitchenType, double rating, boolean isOpen,
			double baseDeliveryFee, int averagePreparingTimeInMinutes, double additionalCostForExpressDelivery) {
		super(code, name, kitchenType, rating, isOpen, baseDeliveryFee);
		this.averagePreparingTimeInMinutes = Math.max(averagePreparingTimeInMinutes, 0);
		this.additionalCostForExpressDelivery = Math.max(additionalCostForExpressDelivery, 0);
	}

	
//	@Override
//	public String toString() {
//		return "FastFoodRestaurant [averagePreparingTimeInMinutes=" + averagePreparingTimeInMinutes
//				+ ", additionalCostForExpressDelivery=" + additionalCostForExpressDelivery + ", code=" + code
//				+ ", name=" + name + ", kitchenType=" + kitchenType + ", rating=" + rating + ", isOpen=" + isOpen
//				+ ", baseDeliveryFee=" + baseDeliveryFee + "]";
//	}
	

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
}
