package HW1;

public class Order {
	public static final String Created = "Created", OnTheWay = "OnTheWay", Delivered = "Delivered";
	
	private int orderCode, clientCode;
	private Restaurant restaurant;
	private int restaurantCode, deliverCode;
	private Date orderingDate, deliveringDate;
	private double basePrice, finalPrice;
	
	private String deliveryStatus; 
	
	
	public Order(int orderCode, int clientCode, Restaurant restaurant, Date orderingDate, double basePrice) {
		this.orderCode = orderCode;
		this.clientCode = clientCode;
		
		this.restaurant = restaurant;
		restaurantCode = restaurant.getCode();
		
		this.deliverCode = -1;
		this.orderingDate = orderingDate;
		
		deliveringDate = new Date();
		deliveryStatus = Created;
		
		setBasePrice( Math.max(basePrice, 0));
	}

	
	public int getDeliverCode() {
		return deliverCode;
	}


	public void setDeliverCode(int deliverCode) {
		if(0 <= deliverCode)
		this.deliverCode = deliverCode;
	}


	public double getFinalPrice() {
		return finalPrice;
	}


	public void setFinalPrice(double finalPrice) {
		if(0<= finalPrice)
		this.finalPrice = finalPrice;
	}


	public int getOrderCode() {
		return orderCode;
	}

	public int getClientCode() {
		return clientCode;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public int getRestaurantCode() {
		return restaurantCode;
	}

	public Date getOrderingDate() {
		return orderingDate;
	}
	
	public Date getDeliveringDate() {
		return deliveringDate;
	}
	
	public double getBasePrice() {
		return basePrice;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}


	public void setOrderCode(int orderCode) {
		if(0 <= orderCode)
		this.orderCode = orderCode;
	}


	public void setClientCode(int clientCode) {
		if(0 <= clientCode)
		this.clientCode = clientCode;
	}


	public void setRestaurant(Restaurant restaurant) {
		if(restaurant.getCode() == restaurantCode)
		this.restaurant = restaurant;
	}


	public void setRestaurantCode(int restaurantCode) {
		if(0<= restaurantCode) {
			this.restaurantCode = restaurantCode;
			// final price calculation that determined buy restaurant type
			 if(restaurant instanceof PremiumRestaurant premiumRestaurant) 
				 finalPrice = Math.max(
						 basePrice * (1+premiumRestaurant.getAdditionalCommissionPercentagePerOrder()) + restaurant.getBaseDeliveryFee(), 
						 premiumRestaurant.getMinimumOrderCost());
			
			 else if (restaurant instanceof FastFoodRestaurant fastFoodRestaurant) 
				finalPrice = basePrice + restaurant.getBaseDeliveryFee() + fastFoodRestaurant.getAdditionalCostForExpressDelivery();
			
			 else finalPrice = basePrice + restaurant.getBaseDeliveryFee();
		}
	}


	public void setOrderingDate(Date orderingDate) {
		this.orderingDate = orderingDate;
	}


	public void setDeliveringDate(Date deliveringDate) {
		if(deliveringDate.isAfter(orderingDate))
		this.deliveringDate = deliveringDate;
	}


	public void setBasePrice(double basePrice) {
		if( 0 <= basePrice) 
		{
			this.basePrice = basePrice;
		// final price calculation that determined buy restaurant type
			if(restaurant instanceof PremiumRestaurant premiumRestaurant) 
				 finalPrice = Math.max(
						 basePrice * (1+premiumRestaurant.getAdditionalCommissionPercentagePerOrder()) + restaurant.getBaseDeliveryFee(), 
						 premiumRestaurant.getMinimumOrderCost());
				
			 else if (restaurant instanceof FastFoodRestaurant fastFoodRestaurant) 
				finalPrice = basePrice + restaurant.getBaseDeliveryFee() + fastFoodRestaurant.getAdditionalCostForExpressDelivery();
				
			 else finalPrice = basePrice + restaurant.getBaseDeliveryFee();
		}
	}


	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}


	@Override
	public String toString() {
		return "Order [orderCode=" + orderCode + ", clientCode=" + clientCode + ", restaurantCode=" + restaurantCode
				+ ", deliverCode=" + deliverCode + ", orderingDate=" + orderingDate + ", deliveringDate="
				+ deliveringDate + ", basePrice=" + basePrice + ", finalPrice=" + finalPrice + ", deliveryStatus="
				+ deliveryStatus + "]";
	}

	
}
