package Lab2;

import java.util.Scanner;
import java.util.Locale;

public class Q2 {

	public static final Scanner inScanner = new Scanner(System.in);
	
	// input of an int
	public static int inputInt(String text){
		System.out.print(text);
		while(!inScanner.hasNextInt()) {
			inScanner.next();
			System.out.print(text);
		}
		return inScanner.nextInt();
	}

	// input of a double
	public static double inputDouble(String text) {
	    System.out.print(text);
	    while (!inScanner.hasNextDouble()) {
	        inScanner.next();
	        System.out.print(text);
	    }
	    return inScanner.nextDouble();
	}

	public static void main(String[] args) {
		// use 0.0 for double input
		inScanner.useLocale(Locale.US);
		
		Library lib = new Library();
		
		int count = inputInt("Enter number of books to add: ");
		for (int i = 0; i < count; i++) {
			int bookId = inputInt("Book ID (int): ");
			
			System.out.print("Title: ");
			String title = inScanner.next();
			
			System.out.print("Author: ");
			String author = inScanner.next();
			
			int ages;
			double price;
			while((ages = inputInt("Minimum age (not negative int): ")) < 0);
			while((price = inputDouble("Price (not negative double): ")) < 0);

			if(!lib.addBook(new Book(bookId, title, author, ages, price)))
				System.out.println("The library is full");
		}
		
		for(String name : lib.getAllAuthors())
			System.out.println("Cheapest book of " + name + " is: " + lib.cheapestByAuthor(name));
	}
}
