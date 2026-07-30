package Lab3;

public class Order {

    private int code;
    private String customerId;
    private Movie[] movies;

    public Order(int code, String customerId, Movie[] movies) {
        this.code = code;
        this.customerId = customerId;
        this.movies = movies;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Movie[] getMovies() {
        return movies;
    }

    public void setMovies(Movie[] movies) {
        this.movies = movies;
    }
}