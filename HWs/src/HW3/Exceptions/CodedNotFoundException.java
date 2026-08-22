package HW3.Exceptions;

public abstract class CodedNotFoundException extends Exception {

	public CodedNotFoundException(int code, String name) {
		super(name + " with code " + code + " not found");
	}
}
