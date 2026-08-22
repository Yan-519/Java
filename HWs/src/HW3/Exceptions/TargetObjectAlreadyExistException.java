package HW3.Exceptions;

public class TargetObjectAlreadyExistException extends Exception {

	public TargetObjectAlreadyExistException(String auth) {
		super(auth + " already exists");
	}
}
