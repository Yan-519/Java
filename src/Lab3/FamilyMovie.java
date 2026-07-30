package Lab3;

import java.util.Arrays;

public class FamilyMovie extends Movie {

    private String movieTopic;
    private int rating;

    public FamilyMovie(String movieTitle, double price, String[] actors,
                       int code, String movieTopic, int rating) {
        super(movieTitle, price, actors, code);
        this.movieTopic = movieTopic;
        this.rating = rating;
    }

    public String getMovieTopic() {
        return movieTopic;
    }

    public void setMovieTopic(String movieTopic) {
        this.movieTopic = movieTopic;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

	@Override
	public String toString() {
		return "FamilyMovie [movieTopic=" + movieTopic + ", rating=" + rating + ", movieTitle=" + movieTitle
				+ ", price=" + price + ", actors=" + Arrays.toString(actors) + ", code=" + code + "]";
	}

	
    
}