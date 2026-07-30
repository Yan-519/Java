package Lecture3.Task;

public class Teacher extends Employee {
	private int classNumber;

	public Teacher(String name, int id, int salary, int classNumber) {
		super(name, id, salary);
		this.classNumber = classNumber;
	}

	@Override
	public String toString() {
		return "Teacher [classNumber=" + classNumber + ", salary=" + salary + ", name=" + name + ", id=" + id + "]";
	}

	public int getClassNumber() {
		return classNumber;
	}

	public void setClassNumber(int classNumber) {
		this.classNumber = classNumber;
	}
	
}

