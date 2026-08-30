package HW3.DataObjects.Helpers;

import java.util.ArrayList;

public class ConvertorHolder<T> {
	// the output object
	public final T output;
	// the codes it depends on
	public final ArrayList<Integer> codes;
	// for rider current order code and order restaurant
	public final Integer single;
	
	public ConvertorHolder(T outpu, ArrayList<Integer> codes) {
		this.output = outpu;
		this.codes = codes;
		single = null;
	}
	
	public ConvertorHolder(T outpu) {
		this.output = outpu;
		this.codes = new ArrayList<Integer>();
		single = null;
	}

	public ConvertorHolder(T output, ArrayList<Integer> codes, Integer single) {
		this.output = output;
		this.codes = codes;
		this.single = single;
	}
	
	public ConvertorHolder(T output, Integer single) {
		this.output = output;
		this.codes = null;
		this.single = single;
	}
}
