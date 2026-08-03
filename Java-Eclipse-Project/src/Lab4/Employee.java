package Lab4;

public abstract class Employee implements SchoolsMethods {
	private static int numberOfEmployee = 0;
    protected int num;
    protected String name;

    public Employee(String name) {
        this.num = numberOfEmployee++;
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
    	if(0 <= num)
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee [Num=" + num + ", Name=" + name + "]";
    }
}
