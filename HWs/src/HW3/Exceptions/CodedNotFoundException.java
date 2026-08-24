package HW3.Exceptions;

public abstract class CodedNotFoundException extends Exception {

	public CodedNotFoundException(String message) {
		super(message + " not found");
		// TODO Auto-generated constructor stub
	}

	public CodedNotFoundException(int code, String name) {
		super(name + " with code " + code + " not found");
	}
}
