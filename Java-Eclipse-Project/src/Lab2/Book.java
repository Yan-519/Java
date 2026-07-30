package Lab2;

public class Book {
	private int bookId;
	private String title;
	private String author;
	private int ages;
	private double price;
	
	
	public Book(int bookId, String title, String author, int ages, double price) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.ages = Math.max(ages, 0);
		this.price = Math.max(price, 0);
	}
	
	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getAges() {
		return ages;
	}
	public void setAges(int ages) {
		if( 0<= ages)
		this.ages = ages;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		if(0 <= price)
		this.price = price;
	}
	
	// returns true if the given book is more expensive than the current
	public boolean isCheaper(Book other) {
		return price < other.getPrice();
	}
	
	// returns true if the given book is the same (without cheacking the price) as the current
	public boolean isSame(Book other) {
		return bookId == other.getBookId() &&
				other.getTitle().equalsIgnoreCase(title) &&
				other.getAuthor().equalsIgnoreCase(author) &&
				ages == other.getAges();
	}


	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", title=" + title + ", author=" + author + ", ages=" + ages + ", price="
				+ price + "]";
	}
	
	
}
