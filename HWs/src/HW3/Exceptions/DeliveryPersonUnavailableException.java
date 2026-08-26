package HW3.Exceptions;

@SuppressWarnings("serial")
public class DeliveryPersonUnavailableException extends Exception {

	public DeliveryPersonUnavailableException(String name, String lastName) {
		super(name + " " + lastName + " isn't aveilable");
	}
}
