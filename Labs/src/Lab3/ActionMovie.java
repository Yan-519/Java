package Lab3;

import java.util.Arrays;

public class ActionMovie extends Movie {

    private int age;
    private boolean subtitles;

    public ActionMovie(String movieTitle, double price, String[] actors,
                       int code, int age, boolean subtitles) {
        super(movieTitle, price, actors, code);
        this.age = age;
        this.subtitles = subtitles;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSubtitles() {
        return subtitles;
    }

    public void setSubtitles(boolean subtitles) {
        this.subtitles = subtitles;
    }

	@Override
	public String toString() {
		return "ActionMovie [age=" + age + ", subtitles=" + subtitles + ", movieTitle=" + movieTitle + ", price="
				+ price + ", actors=" + Arrays.toString(actors) + ", code=" + code + "]";
	}

    
}
