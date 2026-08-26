package HW3.Exceptions;

import HW3.DataObjects.Restaurant;

@SuppressWarnings("serial")
public class RestaurantNotFoundException extends CodedNotFoundException {

	public RestaurantNotFoundException(int code) {
		super(code, Restaurant.class.getSimpleName());
	}
}
