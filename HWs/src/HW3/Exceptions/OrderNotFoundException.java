package HW3.Exceptions;

import HW3.DataObjects.Order;

public class OrderNotFoundException extends CodedNotFoundException {

	public OrderNotFoundException(int code) {
		super(code, Order.class.getSimpleName());
	}
}
