package Lab6;

import Models.Node;

public class Student {
	private String name;
	private Node<Exam> grades;
	
	
	
	public Student(String name) {
		super();
		this.name = name;
		
	}

	private Node<Exam> findBuyName(String course){
		Node<Exam> node = grades;
		if(node == null || node.getData().getCourse().equalsIgnoreCase(course))
			return node;
		
		while(node.getNext() != null && !node.getNext().getData().getCourse().equalsIgnoreCase(course))
			node = node.getNext();
		
		return node;
	}

	public void update(Exam exam) {
		
		for(Node<Exam> node = grades; node != null; node = node.getNext()) {
			if(node.getData().equals(exam)) {
				node.getData().setGrade(exam.getGrade());
				return;
			}
		}
		
		Node<Exam> node = findBuyName(exam.getCourse());
		
		if(node == null || node.getData().getCourse().equalsIgnoreCase(exam.getCourse()) && node.getData().getMoed() < exam.getMoed()) {
			grades= new Node<Exam>(exam, grades);
			return;
		}
			
		while(node.getNext() != null && node.getNext().getData().getCourse().equalsIgnoreCase(exam.getCourse()) && node.getNext().getData().getMoed() > exam.getMoed())
			node = node.getNext();
		
		Node<Exam> tmpNode = new Node<Exam>(exam, node.getNext());
		node.setNext(tmpNode);
	}
	
	public double calcAvg() {
		
		if(grades == null) return -1;

		int count = 1;
		double sum = grades.getData().getGrade();

		for(Node<Exam> node = grades; node.getNext() != null; node = node.getNext()) {
			if(!node.getData().getCourse().equalsIgnoreCase(node.getNext().getData().getCourse())) {
				count++;
				sum += node.getNext().getData().getGrade();
			}
		}
		
		return sum / count;
	}

	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		String str = name + ": ";
		
		for(Node<Exam> a = grades; a != null; a = a.getNext())
			str += a + ", ";
		
		return str;
	}
	
	
}
