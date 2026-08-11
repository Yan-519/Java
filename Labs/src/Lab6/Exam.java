package Lab6;

public class Exam {
	private String course;
	private int moed;
	private int grade;
	
	public Exam(String course, int moed, int grade) {
		super();
		this.course = course;
		this.moed = moed;
		this.grade = grade;
	}
	
	public boolean equals(Exam other) {
		return other.getMoed() == moed && other.getCourse().equalsIgnoreCase(course);
	}

	public String getCourse() {
		return course;
	}

	public int getMoed() {
		return moed;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "Exam [course=" + course + ", moed=" + moed + ", grade=" + grade + "]";
	}
	
	
	
}
