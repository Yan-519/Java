package Lab7;

@SuppressWarnings("serial")
public class InvalidEnrollmentException extends Exception {
	public InvalidEnrollmentException(int count) {
		super("Invalid rnrollment " + count);
	}
}
