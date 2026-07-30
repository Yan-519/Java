package Lecture5;

public class Student {

	private String name;
	private int Id;
	private double avg;
	public Student(String name, int id, double avg) {
		super();
		this.name = name;
		Id = id;
		this.avg = avg;
	}
	public String getName() {
		return name;
	}
	public int getId() {
		return Id;
	}
	public double getAvg() {
		return avg;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + ", Id=" + Id + ", avg=" + avg + "]";
	}
	
	
}
