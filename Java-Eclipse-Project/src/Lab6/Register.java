package Lab6;

public class Register {
	public int num;
	public Queue<Order> orders;
	
	
	public Register(int num, Queue<Order> orders) {
		super();
		this.num = num;
		this.orders = orders;
	}
	
	public int getNumOfSoldProducts() {
		Queue<Order> tmp = new Queue<Order>();
		int sum = 0;
		
		while(!orders.isEmpty()) {
			sum += orders.Head().getQty();
			tmp.insert(orders.remove());
		}
		
		while(!tmp.isEmpty())
			orders.insert(tmp.remove());
		
		return sum;
	}
	
	public int getTotalSum() {
		Queue<Order> tmp = new Queue<Order>();
		int sum = 0;
		
		while(!orders.isEmpty()) {
			sum += orders.Head().total();
			tmp.insert(orders.remove());
		}
		
		while(!tmp.isEmpty())
			orders.insert(tmp.remove());
		
		return sum;
	}
	
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Queue<Order> getOrders() {
		return orders;
	}
	public void setOrders(Queue<Order> orders) {
		this.orders = orders;
	}
	
	
}
