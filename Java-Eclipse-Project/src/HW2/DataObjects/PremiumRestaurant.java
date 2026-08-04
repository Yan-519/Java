package HW2.DataObjects;

public class PremiumRestaurant extends Restaurant {
	private double minimumOrderCost;
	private double additionalCommissionPercentagePerOrder;
	
	
	public PremiumRestaurant(int code, String name, String kitchenType, double rating, boolean isOpen,
			double baseDeliveryFee, double minimumOrderCost, double additionalCommissionPercentagePerOrder) {
		super(code, name, kitchenType, rating, isOpen, baseDeliveryFee);
		this.minimumOrderCost = minimumOrderCost;
		this.additionalCommissionPercentagePerOrder = Math.max(additionalCommissionPercentagePerOrder, 0);
	}

	
	@Override
	public String toString() {
		return "PremiumRestaurant [minimumOrderCost=" + minimumOrderCost + ", additionalCommissionPercentagePerOrder="
				+ additionalCommissionPercentagePerOrder + ", code=" + getCode() + ", name=" + name + ", kitchenType="
				+ kitchenType + ", rating=" + rating + ", isOpen=" + isOpen + ", baseDeliveryFee=" + baseDeliveryFee
				+ "]";
	}

	
	public double getMinimumOrderCost() {
		return minimumOrderCost;
	}

	public double getAdditionalCommissionPercentagePerOrder() {
		return additionalCommissionPercentagePerOrder;
	}

	public void setMinimumOrderCost(double minimumOrderCost) {
		this.minimumOrderCost = minimumOrderCost;
	}

	public void setAdditionalCommissionPercentagePerOrder(double additionalCommissionPercentagePerOrder) {
		if(0 <= additionalCommissionPercentagePerOrder)
		this.additionalCommissionPercentagePerOrder = additionalCommissionPercentagePerOrder;
	}
}
