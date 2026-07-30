package Lab3;

import java.util.Arrays;
import java.util.Random;

public class Service {
	private static final Random random = new Random();
	
	public static double getRating(Movie[] movies) {
		double sum = 0;
		int count = 0;
		
		for(Movie movie : movies) {
			if (movie instanceof FamilyMovie familyMovie)
			{
				count++;
				sum += familyMovie.getRating();
			}
		}
		if(count == 0)
			return -1;
		return sum / count;
	}
	
	public static ActionMovie[] getNumberOfSubtitles(Movie[] movies) {
		ActionMovie[] actionMovies = new ActionMovie[movies.length];
		int idx = 0;
		
		for(Movie movie : movies) 
			if (movie instanceof ActionMovie actionMovie && actionMovie.isSubtitles() )
				actionMovies[idx++] = actionMovie;
		
		return Arrays.copyOf(actionMovies, idx);
	}
	
	public static int generateMovieCode(Movie[] movies) {
		int code ;
		do {
			code = Math.abs(random.nextInt());
		}while(isContains(movies, code));
		return code;
	}
	
	public static boolean isContains(Movie[] movies, int code) {
		for(Movie movie : movies)
			if(movie != null && movie.getCode() == code) 
				return true;
		return false;
	}
}
