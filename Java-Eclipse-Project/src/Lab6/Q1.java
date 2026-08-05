package Lab6;

public class Q1 {
	// if no greads returns -1
	public static double calcAvg(Student s) {
		return s.calcAvg();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Student student = new Student("Avi");
		System.out.println(student);
		
		student.update(new Exam("DB", 1, 85));
		student.update(new Exam("OOP", 2, 73));
		student.update(new Exam("OOP", 1, 42));
		System.out.println(student);
		
		System.out.println(calcAvg(student));

		student.update(new Exam("OOP", 3, 100));
		System.out.println(student);

		student.update(new Exam("DB", 1, 100));
		System.out.println(student);

		student.update(new Exam("ALGORITHM", 1, 55));
		System.out.println(student);

	}

}
