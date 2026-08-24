package HW3.DataObjects;

import HW3.Utils.DataChecker;

public class Order extends Coded {
	public enum OrderStatus{ Created, OnTheWay, Delivered }
	
	
	private int clientCode;
	private Restaurant restaurant;
	private int restaurantCode;
	private String riderId;
	private Date orderingDate, deliveringDate;
	private double basePrice, finalPrice;
	
	private OrderStatus status; 
	
	
	public Order(int code, int clientCode, Restaurant restaurant, Date orderingDate, double basePrice) {
		super(code);
		this.clientCode = clientCode;
		this.restaurant = restaurant;
		this.restaurantCode = restaurant.getCode();
		this.orderingDate = orderingDate;
		this.basePrice = basePrice;
		setBasePrice(basePrice);
		
		this.status = OrderStatus.Created;

		this.riderId = null;
		this.deliveringDate = new Date();
	}

	public String getRiderId() {
		return riderId;
	}

	public void setRiderId(String riderId) {
		if(DataChecker.isValidId(riderId))
			this.riderId = riderId;
	}

	public double getFinalPrice() {
		return finalPrice;
	}


	public void setFinalPrice(double finalPrice) {
		if( 0 <= finalPrice)
		this.finalPrice = finalPrice;
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
	

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public void setClientCode(int clientCode) {
		this.clientCode = clientCode;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public void setRestaurantCode(int restaurantCode) {
		this.restaurantCode = restaurantCode;
	}

	public void setOrderingDate(Date orderingDate) {
		this.orderingDate = orderingDate;
	}

	@Override
	public String toString() {
		return "Order [clientCode=" + clientCode + ", restaurant=" + restaurant + ", restaurantCode=" + restaurantCode
				+ ", riderId=" + riderId + ", orderingDate=" + orderingDate + ", deliveringDate=" + deliveringDate
				+ ", basePrice=" + basePrice + ", finalPrice=" + finalPrice + ", status=" + status + ", code=" + code
				+ "]";
	}


}