package Lecture4;

public class Student implements MaxThings{
	private int[] greads;
	private Course[] courses;
	
	
	
	public Student(int[] greads, Course[] courses) {
		super();
		this.greads = greads;
		this.courses = courses;
	}

	public int getMax() {
	    if (greads == null || greads.length == 0)
	        return -1;

	    int max = greads[0];

	    for (int i = 1; i < greads.length; i++) {
	        if (greads[i] > max)
	            max = greads[i];
	    }

	    return max;
	}

	public String getMaxName() {
	    if (greads == null || greads.length == 0 || courses == null)
	        return null;

	    int maxIndex = 0;

	    for (int i = 1; i < greads.length; i++) {
	        if (greads[i] > greads[maxIndex])
	            maxIndex = i;
	    }

	    return courses[maxIndex].getName();
	}}
