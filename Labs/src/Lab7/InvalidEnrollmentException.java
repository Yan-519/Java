package Lab7;

public class InvalidEnrollmentException extends Exception {
	public InvalidEnrollmentException(int count) {
		super("Invalid rnrollment " + count);
	}
}
