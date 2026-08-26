package HW3.DataObjects;

import HW3.DataObjects.Helpers.Coded;
import HW3.DataObjects.Helpers.ConvertorHolder;
import HW3.Utils.DataChecker;

public class Order extends Coded<Order> {
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

	private Order(int code, int clientCode, Restaurant restaurant, int restaurantCode, String riderId,
			Date orderingDate, Date deliveringDate, double basePrice, double finalPrice, OrderStatus status) {
		super(code);
		this.clientCode = clientCode;
		this.restaurant = restaurant;
		this.restaurantCode = restaurantCode;
		this.riderId = riderId;
		this.orderingDate = orderingDate;
		this.deliveringDate = deliveringDate;
		this.basePrice = basePrice;
		this.finalPrice = finalPrice;
		this.status = status;
	}


	public Order() {super(-1);}

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

	@Override
	public String convert() {
		return joiner(
			code,
			clientCode,
			restaurantCode,
			riderId,
			orderingDate.convert(),
			deliveringDate,
			basePrice,
			finalPrice,
			status.name()
		);
	}

	@Override
	public ConvertorHolder<Order> convert(String in) {
		if (in == null || in.trim().isEmpty()) {
			return null;
		}

		String[] parts = in.trim().split(" ");
		if (parts.length < 9) {
			throw new IllegalArgumentException("Invalid input format for Order: " + in);
		}

		int parsedCode = Integer.parseInt(parts[0]);
		int parsedClientCode = Integer.parseInt(parts[1]);
		int parsedRestaurantCode = Integer.parseInt(parts[2]);
		String parsedRiderId = parts[3].equals("null") ? null : parts[3].replace("_", " ");

		Date parsedOrderingDate = (new Date().convert(parts[4].replace("_", " "))).output;
		
		Date parsedDeliveringDate = (new Date().convert(parts[5].replace("_", " "))).output;

		double parsedBasePrice = Double.parseDouble(parts[6]);
		double parsedFinalPrice = Double.parseDouble(parts[7]);
		OrderStatus parsedStatus = OrderStatus.valueOf(parts[8]);

		return new ConvertorHolder<>( new Order(
			parsedCode, parsedClientCode, null, parsedRestaurantCode,
			parsedRiderId, parsedOrderingDate, parsedDeliveringDate,
			parsedBasePrice, parsedFinalPrice, parsedStatus
		));
	}


}