package HW3.Exceptions;

import HW3.DataObjects.Order;

@SuppressWarnings("serial")
public class OrderNotFoundException extends CodedNotFoundException {

	public OrderNotFoundException(int code) {
		super(code, Order.class.getSimpleName());
	}
	
	public OrderNotFoundException() {
		super("Order not found");
	}
}
