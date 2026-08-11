package Lab3;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Main {
	
	private static final Scanner in = new Scanner(System.in);
	
	// input of an int
	public static int inputInt(String text){
		System.out.print(text);
		while(!in.hasNextInt()) {
			in.next();
			System.out.print(text);
		}
		return in.nextInt();
	}
	
	public static double inputDouble(String text) {
	    System.out.print(text);
	    while (!in.hasNextDouble()) {
	        in.next();
	        System.out.print(text);
	    }
	    return in.nextDouble();
	}
	
	public static boolean inputBool(String text) {
		text += " (Yes/No): ";
	    String in = inputString(text);
	    while (!in.equalsIgnoreCase("Yes") && !in.equalsIgnoreCase("No")) {
			in = inputString(text);
		}
	    return in.equalsIgnoreCase("Yes");
	}
	
	public static String inputString(String text) {
		System.out.print(text);
		return in.next();
	}

	// creating the movies by user input 
	public static Movie[] inputMovies() {
		Movie[] movies = new Movie[0];
		int idx = 0;
		
		String actionStr = "Input action : \n"
				+ "1-add Movie \n"
				+ "2-add FamilyMovie \n"
				+ "3-add ActionMovie \n"
				+ "-1-stop adding \n"
				+ "else-stop adding and delete all added \n"
				+ ":";
		
		int action = inputInt(actionStr);
		while(1 <= action && action <= 3) {
			String movieTitle = inputString("Enter movie title: ");
			
			double price;
			while((price = inputDouble("Enter price (not negative): ")) < 0);

			int actorCount;
			while((actorCount = inputInt("Enter number of actors (not negative): ")) < 0);
			String[] actor = new String[actorCount];
			for (int i = 0; i < actor.length; i++) {
			    actor[i] = inputString("Enter actor " + (i + 1) + ": ");
			}

			int code = Service.generateMovieCode(movies);

			
			Movie movie = null;
			switch (action) {
			case 1:
				movie = new Movie(movieTitle, price, actor, code);
				break;
				
			case 2:
				
				String movieTopic = inputString("Enter movie topic: ");
				int rating = inputInt("Enter movie rating: ");
				while (rating < 1 || 10 < rating) {
					System.out.println("Input aout of range (1 to 10)");
					rating = inputInt("Enter movie rating: ");
				}
				
				movie = new FamilyMovie(movieTitle, price, actor, code, movieTopic, rating);
				break;
				
			case 3:
				int age;
				while((age = inputInt("Enter movie age (not negative): ")) < 0);
				Boolean subtitles = inputBool("Enter is movie contains subtitles: ");
				
				movie = new ActionMovie(movieTitle, price, actor, code, age, subtitles);
				break;
			}

			if(idx == movies.length)
				movies = Arrays.copyOf(movies, movies.length +1);
			movies[idx++] = movie;

			action = inputInt(actionStr);
		}
		if(action != -1)
			return new Movie[0];
		
		return movies = Arrays.copyOf(movies, idx);

	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		// use 0.1 for double input
		in.useLocale(Locale.US);
		
		Movie[] movies = inputMovies();
		if(movies.length == 0) {
			System.out.println("No movies entered");
			return;
		}
		
		Customer customer1 = new Customer("Dan", "1111222233334444", "123456789");
		Customer customer2 = new Customer("Noa", "5555666677778888", "987654321");      
		
		int n = movies.length;

		// First quarter of the movies -> Customer 1, Order 1
		Order order1 = new Order(1, customer1.getId(), Arrays.copyOfRange(movies, 0, n / 4));
		// Second quarter of the movies -> Customer 1, Order 2
		Order order2 = new Order(2, customer1.getId(), Arrays.copyOfRange(movies, n / 4, n / 2));

		// Third quarter of the movies -> Customer 2, Order 1
		Order order3 = new Order(3, customer2.getId(), Arrays.copyOfRange(movies, n / 2, 3 * n / 4));
		// Last quarter of the movies -> Customer 2, Order 2
		Order order4 = new Order(4, customer2.getId(), Arrays.copyOfRange(movies, 3 * n / 4, n));
        
        // showing the Average rating customer1's movies (order1 + order2)
		double avrg = Service.getRating(Arrays.copyOf(movies, n/2));
		if(avrg != -1)
			System.out.println("Average rating customer1 = " + avrg);
		else System.out.println("No movies with raiting found for customer1");

        ActionMovie[] subtitles = Service.getNumberOfSubtitles(movies);
        
        if(0 < subtitles.length ) {
            System.out.println("\nAction movies with subtitles:");
            for (ActionMovie movie : subtitles) 
                System.out.println(movie);
        }
        else System.out.println("No movies with subtitles");
	

	}
}