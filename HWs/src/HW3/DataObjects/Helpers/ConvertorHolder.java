package HW3.DataObjects.Helpers;

import java.util.ArrayList;

public class ConvertorHolder<T> {
	public final T output;
	public final ArrayList<Integer> codes;
	
	
	public ConvertorHolder(T outpu, ArrayList<Integer> codes) {
		super();
		this.output = outpu;
		this.codes = codes;
	}
	
	public ConvertorHolder(T outpu) {
		super();
		this.output = outpu;
		this.codes = new ArrayList<Integer>();
	}
}
