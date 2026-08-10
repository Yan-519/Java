package Lab7;

import java.util.ArrayList;
import java.util.List;

public class Q2 {
	
	public static void printCourses (List<? extends Course> courses) {

		courses.forEach(System.out::println);
		System.out.println();
	}
	
	public static void addProgrammingCourses( List<? super ProgrammingCourse> list) {
		try {
			list.add(new ProgrammingCourse(201, "C++ Programming", 22));
			list.add(new ProgrammingCourse(202, "JavaScript Programming", 28));
			list.add(new ProgrammingCourse(203, "Go Programming", 16));
		} catch (InvalidEnrollmentException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		List<Course> courses = new ArrayList<Course>();
		try {
			courses.add(new ProgrammingCourse(101, "Java Programming", 25));
			courses.add(new DatabaseCourse(102, "Database Design", 30));
			courses.add(new AIcourse(103, "Artificial Intelligence", 15));
			courses.add(new ProgrammingCourse(104, "Python Programming", 20));
			courses.add(new DatabaseCourse(105, "SQL Advanced", 28));
			courses.add(new AIcourse(106, "Machine Learning", 18));
		} catch (InvalidEnrollmentException e) {
			System.out.println(e.getMessage());
		}
		

		printCourses(courses);
		
		courses.sort((o1,o2) -> Integer.compare(o1.getCount(), o2.getCount()));
		printCourses(courses);
		
		courses.sort((o1,o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
		printCourses(courses);
		
		courses.sort((o1,o2) -> Integer.compare(o1.getCode(), o2.getCode()));
		printCourses(courses);
	}

}