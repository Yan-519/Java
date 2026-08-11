package Lab6;

public class Order {
	private int qty;
	private double price;
	
	public double total() { return qty*price; }
	
	
	public Order(int qty, double price) {
		super();
		this.qty = qty;
		this.price = price;
	}
	
	
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
