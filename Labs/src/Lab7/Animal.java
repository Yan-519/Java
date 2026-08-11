package Lab7;

import java.util.Objects;

public abstract class Animal {

	protected String name;
	protected int age;
	
	public Animal(String name, int age) throws AgeRestrictionException{
		if( age <= 2 )
			throw new AgeRestrictionException(age);
			
		this.age = age;
		this.name = name;
	}

	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract String makeSound();

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [name=" + name + ", age=" + age + ", makeSound()=" + makeSound() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(Integer.valueOf(age), name);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Animal animal && animal.age == age && animal.name == name;
	}
	
	
	
}
