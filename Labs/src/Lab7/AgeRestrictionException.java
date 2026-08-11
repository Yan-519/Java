package Lab7;

public class AgeRestrictionException extends Exception {

	public AgeRestrictionException(int age) {
		super("The age " + age + " is restricted");
	}
}
