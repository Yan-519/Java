package Lab7;

public class Dog extends Animal {

	public Dog(String name, int age) throws AgeRestrictionException {
		super(name, age);
	}

	@Override
	public String makeSound() {
		// return a dog sound
		return "Woof!";
	}

}
