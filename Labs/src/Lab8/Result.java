package Lab8;

public class Result {

	private int month, number, result;

	public Result(int month, int number, int result) {
		super();
		this.month = month;
		this.number = number;
		this.result = result;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Result [month=" + month + ", number=" + number + ", result=" + result + "]";
	}
	
	
}
