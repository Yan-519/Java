package Lab7;

public class AIcourse extends Course {

	private String researchModel;

	public AIcourse(int code, String name, int count) throws InvalidEnrollmentException {
		super(code, name, count);
		
		researchModel = "GPT+";
	}

	@Override
	public String toString() {
		return "AIcourse [researchModel=" + researchModel + ", code=" + code + ", name=" + name + ", count=" + count + "]";
	}
	
	
}
