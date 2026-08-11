package Lab7;

public abstract class Course {

	protected int code;
	protected String name;
	protected int count;
	
	public Course(int code, String name, int count) throws InvalidEnrollmentException {
		super();
		if( count < 0)
			throw new InvalidEnrollmentException(count);
		this.code = code;
		this.name = name;
		this.count = count;
	}


	public void display() {
		System.out.println(toString());
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}


	@Override
	public String toString() {
		return "Course [code=" + code + ", name=" + name + ", count=" + count + "]";
	}
}
