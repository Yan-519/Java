package HW3.Exceptions;

public class TargetObjectDoesntExistException extends Exception {
	
	public TargetObjectDoesntExistException(String auth) {
		super(auth + " does't exists");
	}
	
	public TargetObjectDoesntExistException(String auth, String deskription) {
		super(auth + " does't exists [" + deskription + "]");
	}
}
