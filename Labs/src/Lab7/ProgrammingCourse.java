package Lab7;

public class ProgrammingCourse extends Course {
	private String language = "Java";

	public ProgrammingCourse(int code, String name, int count, String language) throws InvalidEnrollmentException {
		super(code, name, count);
		this.language = language;
	}

	@Override
	public String toString() {
		return "ProgrammingCourse [language=" + language + ", code=" + code + ", name=" + name + ", count=" + count + "]";
	}
	
	
}
