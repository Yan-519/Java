package Lab4;

public class Teacher extends Employee {
	public static final String Senior = "Senior", Junior = "Junior";
    private String type;

    public Teacher(String name, String type) {
        super(name);
        this.type = Junior;
        setType(type);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
    	if(type.equalsIgnoreCase(Junior)|| type.equalsIgnoreCase(Senior))
        this.type = type;
    }
    
    @Override
    public boolean getByType() {
		return type.equalsIgnoreCase(Senior);
	}

    @Override
    public String toString() {
        return super.toString() + " | Teacher [Type=" + type + "]";
    }
}
