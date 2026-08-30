package HW3.DataObjects.Helpers;

import java.util.List;

import HW3.DataObjects.Date;

public abstract class StringConvertertable<T> {
	public static final String emptyList = "none", nullObject = "null"; 
	
	// converting object to string and from it
    public abstract ConvertorHolder<? extends T> convert(String in) throws Exception;
    public abstract String convert() throws Exception;
    
    // joining object values into string
    protected String joiner(Object...objects) {
    	StringBuilder stringBuilder = new StringBuilder();
    	for (Object object : objects) {
    		if(object == null) {
    			stringBuilder.append(nullObject + " ");
    			continue;
    		}
    		
    		if(object instanceof List<?> lst)
    		{
    			if (!lst.isEmpty()) {
	    			boolean ifF = true;
	    			for(Object obj : lst) {
	    				if(!ifF)
	    					stringBuilder.append(",");
	    				stringBuilder.append(obj);
	    			}
    			}
    			else stringBuilder.append(emptyList + " ");
    		}
    		else if(object instanceof Date d)
    			stringBuilder.append(d.convert().replace(" ", "_") + " ");
    		
    		else
    			stringBuilder.append(object.toString().replace(" ", "_") + " ");
    	}
    	
    	return stringBuilder.toString().trim();
    }
}
