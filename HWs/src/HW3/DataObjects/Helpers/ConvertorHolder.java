package HW3.DataObjects.Helpers;

import java.util.ArrayList;

public class ConvertorHolder<T> {
	// the output object
	public final T output;
	// the codes it depends on
	public final ArrayList<Integer> codes;
	// for rider current order code
	public final Integer additional;
	
	public ConvertorHolder(T outpu, ArrayList<Integer> codes) {
		this.output = outpu;
		this.codes = codes;
		additional = null;
	}
	
	public ConvertorHolder(T outpu) {
		this.output = outpu;
		this.codes = new ArrayList<Integer>();
		additional = null;
	}

	public ConvertorHolder(T output, ArrayList<Integer> codes, Integer additional) {
		this.output = output;
		this.codes = codes;
		this.additional = additional;
	}
}
