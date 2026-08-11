package Lab7;

public class DatabaseCourse extends Course {
	private int baseSizeMB;

	public DatabaseCourse(int code, String name, int count) throws InvalidEnrollmentException {
		super(code, name, count);
		baseSizeMB = 1000;
	}

	@Override
	public String toString() {
		return "DatabaseCourse [baseSizeMB=" + baseSizeMB + ", code=" + code + ", name=" + name + ", count=" + count + "]";
	}
}
