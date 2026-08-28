package Lab8;

public class Employee implements Comparable<Employee> {
	private int ID;
	private String fullName;
	
	
	public Employee(int iD, String fullName) {
		super();
		ID = iD;
		this.fullName = fullName;
	}
	
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
	@Override
	public String toString() {
		return "Employee [ID=" + ID + ", fullName=" + fullName + "]";
	}


	@Override
	public int compareTo(Employee o) {
		return Integer.compare(ID, o.getID());
	}
}
