package Lecture3.Task;

public class Student extends Person {
	private double avrageGrade;

	public Student(String name, int id, double avrageGrade) {
		super(name, id);
		this.avrageGrade = avrageGrade;
	}

	@Override
	public String toString() {
		return "Student [avrageGrade=" + avrageGrade + ", name=" + name + ", id=" + id + "]";
	}

	public double getAvrageGrade() {
		return avrageGrade;
	}

	public void setAvrageGrade(double avrageGrade) {
		this.avrageGrade = avrageGrade;
	}
	
}
