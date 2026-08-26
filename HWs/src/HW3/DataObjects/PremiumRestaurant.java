package HW3.DataObjects;

import HW3.DataObjects.Helpers.ConvertorHolder;

public class PremiumRestaurant extends Restaurant {
	private double minimumOrderCost;
	private double additionalCommissionPercentagePerOrder;
	
	
	public PremiumRestaurant(int code, String name, String kitchenType, double rating, boolean isOpen,
			double baseDeliveryFee, double minimumOrderCost, double additionalCommissionPercentagePerOrder) {
		super(code, name, kitchenType, rating, isOpen, baseDeliveryFee);
		this.minimumOrderCost = minimumOrderCost;
		this.additionalCommissionPercentagePerOrder = Math.max(additionalCommissionPercentagePerOrder, 0);
	}
	
	

	
	public PremiumRestaurant() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "PremiumRestaurant [minimumOrderCost=" + minimumOrderCost + ", additionalCommissionPercentagePerOrder="
				+ additionalCommissionPercentagePerOrder + ", code=" + code + ", name=" + name + ", kitchenType="
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
	
	@Override
	public String convert() {
		return joiner(
			code,
			name,
			kitchenType,
			rating,
			isOpen,
			baseDeliveryFee,
			minimumOrderCost,
			additionalCommissionPercentagePerOrder
		);
	}

	@Override
	public ConvertorHolder<PremiumRestaurant> convert(String in) {
		if (in == null || in.trim().isEmpty()) {
			return null;
		}

		String[] parts = in.trim().split(" ");
		if (parts.length < 8) {
			throw new IllegalArgumentException("Invalid input format for PremiumRestaurant: " + in);
		}

		int parsedCode = Integer.parseInt(parts[0]);
		String parsedName = parts[1].replace("_", " ");
		String parsedKitchenType = parts[2].replace("_", " ");
		double parsedRating = Double.parseDouble(parts[3]);
		boolean parsedIsOpen = Boolean.parseBoolean(parts[4]);
		double parsedBaseDeliveryFee = Double.parseDouble(parts[5]);
		double parsedMinimumOrderCost = Double.parseDouble(parts[6]);
		double parsedCommission = Double.parseDouble(parts[7]);

		return new ConvertorHolder<PremiumRestaurant>( new PremiumRestaurant(
			parsedCode, parsedName, parsedKitchenType, parsedRating,
			parsedIsOpen, parsedBaseDeliveryFee, parsedMinimumOrderCost, parsedCommission
		));
	}
}
