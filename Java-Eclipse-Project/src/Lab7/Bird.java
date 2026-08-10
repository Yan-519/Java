package Lab7;

public class Bird extends Animal {
	
	public Bird(String name, int age) throws AgeRestrictionException {
		super(name, age);
	}

	@Override
	public String makeSound() {
		// return a bird sound
		return "Tweet!";
	}

}
