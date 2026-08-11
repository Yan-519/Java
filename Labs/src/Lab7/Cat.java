package Lab7;

public class Cat extends Animal {
	
	public Cat(String name, int age) throws AgeRestrictionException {
		super(name, age);
	}

	@Override
	public String makeSound() {
		// return a cat sound
		return "Meow!";
	}

}
