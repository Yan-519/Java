package Lab3;

import java.util.Arrays;

public class Movie {
    protected String movieTitle;
    protected double price;
    protected String[] actors;
    protected int code;

    public Movie(String movieTitle, double price, String[] actors, int code) {
        this.movieTitle = movieTitle;
        this.price = price;
        this.actors = actors;
        this.code = code;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String[] getActors() {
        return actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

	@Override
	public String toString() {
		return "Movie [movieTitle=" + movieTitle + ", price=" + price + ", actors=" + Arrays.toString(actors)
				+ ", code=" + code + "]";
	}

   
}