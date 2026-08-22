package HW3.Exceptions;

import HW3.DataObjects.Customer;

public class CustomerNotFoundException extends CodedNotFoundException {

	public CustomerNotFoundException(int code) {
		super(code, Customer.class.getSimpleName());
	}
}
