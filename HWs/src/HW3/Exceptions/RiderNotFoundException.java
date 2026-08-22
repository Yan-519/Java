package HW3.Exceptions;

public class RiderNotFoundException extends Exception {

	public RiderNotFoundException(String id) {
		super("Rider with id " + id + " not found");
	}
}
