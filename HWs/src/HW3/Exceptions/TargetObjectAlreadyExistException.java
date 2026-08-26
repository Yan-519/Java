package HW3.Exceptions;

@SuppressWarnings("serial")
public class TargetObjectAlreadyExistException extends Exception {

	public TargetObjectAlreadyExistException(String auth) {
		super(auth + " already exists");
	}
}
