package Lab6;

public class Store {
	public Stack<Register> regs;
	
	public Store(Stack<Register> regs) {
		super();
		this.regs = regs;
	}
	
	public int getNumOfSoldProducts() {
		Stack<Register> tmp1 = new Stack<Register>();
		
		int sum = 0;
		
		while(!regs.isEmpty()) {
			sum += regs.peek().getNumOfSoldProducts();
			tmp1.push(regs.pop());
		}
		
		while(!tmp1.isEmpty())
			regs.push(tmp1.pop());
		
		return sum;

	}
	
	public int getRegisterWithMaxTotal() {
		Stack<Register> tmp1 = new Stack<Register>();
		int total = 0;
		Register register = null;

		while(!regs.isEmpty()) {
			
			int cur = regs.peek().getTotalSum();
			if(register == null || total < cur) {
				register = regs.peek();
				total = cur;
			}
			
			tmp1.push(regs.pop());
		}
		
		while(!tmp1.isEmpty())
			regs.push(tmp1.pop());
		
		if(register == null) return -1;
		
		return register.getNum();
	}

	public Stack<Register> getRegs() {
		return regs;
	}

	public void setRegs(Stack<Register> regs) {
		this.regs = regs;
	}

	
}
