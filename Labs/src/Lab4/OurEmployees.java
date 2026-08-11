package Lab4;

import java.util.Arrays;

public class OurEmployees {
	private Employee[] arr;
    private int current;

    public OurEmployees() {
        this.arr = new Employee[500];
        this.current = 0;
    }

    public Employee[] getArr() {
        return arr;
    }

    public void setArr(Employee[] arr) {
    	if(arr != null && arr.length <= 500)
    		this.arr = arr;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
    	if(0 <= current && current < arr.length)
        this.current = current;
    }
    
    public Supervisor[] numSupervisor() {
        Supervisor[] supervisors = new Supervisor[current];
        int index = 0;
        for (int i = 0; i < current; i++) 
            if (arr[i] instanceof Supervisor) 
                supervisors[index++] = (Supervisor) arr[i];

        return Arrays.copyOf(supervisors, index);
    }
    
    public EducationalCounselor getEducational_counselor(String specialization) {
        EducationalCounselor latestCounselor = null;

        for (int i = 0; i < current; i++) 
            if (arr[i] instanceof EducationalCounselor counselor && counselor.getSpecialization().equals(specialization)) 
            	if (latestCounselor == null || counselor.getNum() > latestCounselor.getNum()) 
            		latestCounselor = counselor;

        return latestCounselor;
    }

    public void addToArray(Employee emp) {
        if (emp == null || current >= arr.length) 
            return;
        
        for (int i = 0; i < current; i++)
            if (arr[i].getNum() == emp.getNum()) 
            	return;

        arr[current++] = emp;
    }

	@Override
	public String toString() {
		return "OurEmployees [arr=" + Arrays.toString(arr) + ", current=" + current + "]";
	}
}
