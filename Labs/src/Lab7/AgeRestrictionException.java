package Lab7;

@SuppressWarnings("serial")
public class AgeRestrictionException extends Exception {

	public AgeRestrictionException(int age) {
		super("The age " + age + " is restricted");
	}
}
