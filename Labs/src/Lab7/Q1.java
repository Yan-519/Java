package Lab7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q1 {
	
	public static void playWithAnimals(List<? extends Animal> animals)
	{
		animals.forEach(System.out::println);
	}
	
	public static void addDogs(List<? super Dog> list) 
	{
		try {
			list.add(new Dog("Buster", 6));
		} catch (AgeRestrictionException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		List<Animal> lst;
		try {
			lst = new ArrayList<Animal>(Arrays.asList(
				new Dog("Rex", 5),
				new Cat("Mittens", 3),
				new Bird("Tweety", 4),
				new Dog("Buddy", 7),
				new Cat("Luna", 6),
				new Cat("Whiskers", 4),
				new Bird("Sky", 3),
				new Bird("Kiwi", 5),
				new Dog("Charlie", 8),
				new Dog("Rocky", 6),
				new Cat("Bella", 5),
				new Bird("Sunny", 7)
		));
		}
		catch (AgeRestrictionException e) {
			System.out.println(e.getMessage());
			System.out.println("Animals list is empty");
			lst = new ArrayList<Animal>();
		}
		
		lst.forEach((Animal animal) -> System.out.println(animal.getName() + " says " + animal.makeSound()));
		System.out.println();
		
		
		// sort by age
		lst.sort((a1,a2) -> Integer.compare(a1.getAge(), a2.getAge()));
		lst.forEach(System.out::println);
		System.out.println();
		
		// sort by name
		lst.sort((a1,a2) -> a1.getName().compareTo(a2.getName()));
		lst.forEach(System.out::println);
		System.out.println();

		// sort by sound
		lst.sort((a1,a2) -> a1.makeSound().compareTo(a2.makeSound()));
		lst.forEach(System.out::println);
		System.out.println();
		
		/*
		 * the func can get dog and all objects above it 
		 * and so every object
		 */
		addDogs(lst);
		
		/*
		 * the func get Anima or all objects that extending it
		 * Animl, Dog, Cat, Bird
		 */
		playWithAnimals(lst);
	}

}
