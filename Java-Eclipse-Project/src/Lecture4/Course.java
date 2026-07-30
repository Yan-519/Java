package Lecture4;

public class Course {
	private int greade;
	private String name;
	
	
	public Course(int greade, String name) {
		super();
		this.greade = greade;
		this.name = name;
	}
	public int getGreade() {
		return greade;
	}
	public void setGreade(int greade) {
		this.greade = greade;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
