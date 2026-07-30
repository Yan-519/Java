package Lecture3;

public class A {
	
	int a, b, s;
	String u;
	
	public A() {
		System.out.println("A");
	}
	
	public A(int a, int b, int s, String u) {
		this.a = a;
		this.b = b;
		this.s = s;
		this.u = u;
	}

	public String func() {
		return "A";
	}
}
