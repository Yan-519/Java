package Lecture5;

import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Hashtable<Integer, Student> students = new Hashtable<Integer, Student>();

        while (true) {
            System.out.print("Enter student name (-1 to stop): ");
            String name = in.nextLine();
            if (name.equals("-1"))
                break;

            System.out.print("Enter student ID (-1 to stop): ");
            int id = Integer.parseInt(in.nextLine());
            if (id == -1)
                break;

            System.out.print("Enter student average (-1 to stop): ");
            double avg = Double.parseDouble(in.nextLine());
            if (avg == -1)
                break;

            students.put(id, new Student(name, id, avg));
        }
        
        double sum = 0;
        int id = 0;
        double avg = -1;

        for (Map.Entry<Integer, Student> entry : students.entrySet()) {
            Student student = entry.getValue();
            sum += student.getAvg();

            if (avg == -1 || student.getAvg() > avg) {
            	id = entry.getKey();
            	avg = student.getAvg();
            }
        }

        if (!students.isEmpty()) {
            System.out.println("Class Average: " + (sum / students.size()));
            System.out.println("Highest Average Student: " + id);
        } else {
            System.out.println("No students were entered.");
        }   
        in.close();
    }
}