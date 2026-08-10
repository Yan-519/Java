package Lab7;

public class AIcourse extends Course {

	private int tokenCountToUse = 100;

	public AIcourse(int code, String name, int count) throws InvalidEnrollmentException {
		super(code, name, count);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "AIcourse [tokenCountToUse=" + tokenCountToUse + ", code=" + code + ", name=" + name + ", count=" + count
				+ "]";
	}
	
	
}
