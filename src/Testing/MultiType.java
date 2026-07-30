package Testing;

public class MultiType<T>  {
	private T valT;
	
	public MultiType(T val) {
		valT = val;
	}
	
	public MultiType(){
		
	}
	
	@Override
	public String toString() {
		return valT.toString();
	}

	@Override
	public boolean equals(Object otheType) {
		return otheType instanceof MultiType tmpMultiType && tmpMultiType.geT() == valT;
	}
	
	public T geT() {
		return valT;
	}
}
