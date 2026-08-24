package HW3.Exceptions;

public class InsufficientBalanceException extends Exception {

	public InsufficientBalanceException(double balance, double cost) {
		super("Customer with baalnce of " + balance + " can't afford " + cost);
	}
	
	public InsufficientBalanceException(double balance) {
		super("Invalide balance: " + balance);
	}
}
