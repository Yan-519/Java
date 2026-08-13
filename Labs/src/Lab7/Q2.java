package Lab7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q2 {
	
	public static void printCourses(List<? extends Course> courses) {

		courses.forEach(Course::display);
		System.out.println();
	}
	
	public static void addProgrammingCourses( List<? super ProgrammingCourse> list) {
		try {
			list.add(new ProgrammingCourse(201, "Programming", 22, "C++"));
			list.add(new ProgrammingCourse(202, "Programming", 28, "JavaScript"));
		} catch (InvalidEnrollmentException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		List<Course> courses;
		try {
			courses = new ArrayList<Course>(Arrays.asList(
			new ProgrammingCourse(101, "Programming", 25, "Java"),
			new DatabaseCourse(102, "Database Design", 30),
			new AIcourse(103, "Artificial Intelligence", 15),
			new ProgrammingCourse(104, "Programming", 20, "Python"),
			new DatabaseCourse(105, "SQL Advanced", 28),
			new AIcourse(106, "Machine Learning", 18)
			));
		} catch (InvalidEnrollmentException e) {
			System.out.println(e.getMessage());
			System.out.println("Courses list is empty");
			courses = new ArrayList<Course>();
		}
		

		printCourses(courses);
		
		courses.sort((o1,o2) -> Integer.compare(o1.getCount(), o2.getCount()));
		printCourses(courses);
		
		courses.sort((o1,o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
		printCourses(courses);
		
		courses.sort((o1,o2) -> Integer.compare(o1.getCode(), o2.getCode()));
		printCourses(courses);
		
		addProgrammingCourses(courses);
		printCourses(courses);
	}

}