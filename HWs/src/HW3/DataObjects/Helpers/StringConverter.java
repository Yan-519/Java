package HW3.DataObjects.Helpers;

import java.util.ArrayList;

import HW3.DataObjects.Date;

public abstract class StringConverter<T> {
    public abstract ConvertorHolder<T> convert(String in) throws Exception;
    public abstract String convert() throws Exception;
    
    protected String joiner(Object...objects) {
    	StringBuilder stringBuilder = new StringBuilder();
    	for (Object object : objects) {
    		if(object instanceof ArrayList<?> lst)
    		{
    			boolean ifF = true;
    			for(Object obj : lst) {
    				if(!ifF)
    					stringBuilder.append(",");
    				stringBuilder.append(obj);
    			}
    		}
    		else if(object instanceof Date)
    			stringBuilder.append(object.toString() + " ");
    		else
    			stringBuilder.append(object.toString().replace(" ", "_") + " ");
    	}
    	
    	return stringBuilder.toString().trim();
    }
}
