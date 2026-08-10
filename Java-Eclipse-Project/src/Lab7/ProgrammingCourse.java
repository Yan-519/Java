package Lab7;

public class ProgrammingCourse extends Course {
	private String language = "Java";

	public ProgrammingCourse(int code, String name, int count) throws InvalidEnrollmentException {
		super(code, name, count);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ProgrammingCourse [language=" + language + ", code=" + code + ", name=" + name + ", count=" + count
				+ "]";
	}
	
	
}
