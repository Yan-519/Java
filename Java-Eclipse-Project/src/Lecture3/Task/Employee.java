package Lecture3.Task;

public class Employee extends Person {
	protected int salary ;

	public Employee(String name, int id, int salary) {
		super(name, id);
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Employee [salary=" + salary + ", name=" + name + ", id=" + id + "]";
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	
}
