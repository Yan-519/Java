package HW3.DataObjects;

public abstract class StringConverter<T> {
    public abstract T convert(String in);
    public abstract String convert();
    
    protected String joiner(Object...objects) {
    	StringBuilder stringBuilder = new StringBuilder();
    	for (Object object : objects)	
    		stringBuilder.append(object.toString() + "\n");
    	
    	return stringBuilder.toString();
    }
}
