package Lecture3.Task;

import java.util.Arrays;

public class Manager extends Employee {
	private int officeNumber;
	
	private Teacher[] teachers;

	public Manager(String name, int id, int salary, int officeNumber, Teacher[] teachers) {
		super(name, id, salary);
		this.officeNumber = officeNumber;
		this.teachers = teachers;
	}

	public int getOfficeNumber() {
		return officeNumber;
	}

	public void setOfficeNumber(int officeNumber) {
		this.officeNumber = officeNumber;
	}
	
	public Teacher[] getTeachers() {
		return teachers;
	}

	public void setTeachers(Teacher[] teachers) {
		this.teachers = teachers;
	}

	@Override
	public String toString() {
		return "Manager [officeNumber=" + officeNumber + ", teachers=" + Arrays.toString(teachers) + ", salary="
				+ salary + ", name=" + name + ", id=" + id + "]";
	}

	
	
}
