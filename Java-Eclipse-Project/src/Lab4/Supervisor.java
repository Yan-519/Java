package Lab4;

public class Supervisor extends Teacher {
    private Employee[] arr;
    private int current;

    public Supervisor(String name, String type) {
        super(name, type);
        this.arr = new Employee[0];
        this.current = 0;
    }

    public Employee[] getArr() {
        return arr;
    }

    public void setArr(Employee[] arr) {
        this.arr = arr;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
    	if(0 <= current && current < arr.length)
        this.current = current;
    }

    @Override
    public boolean getByType() {
        if (current == 0) return true;

        for (int i = 0; i < current; i++) {
            if (arr[i] instanceof Teacher v) {
                if (v.getByType()) 
                    return false;
                
            } else return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " | Supervisor [Team size=" + current + "]";
    }
}