package Lab2;

import java.util.Arrays;

public class Library {
	private Book[] books;
	private int numOfBooks;
	
	public Library() {
		books = new Book[200];
		numOfBooks = 0;
	}
	
	// returns the cheapest book of the given author
	public Book cheapestByAuthor(String author) {
		Book book = null;
		
		for (int i = 0; i < numOfBooks; i++) {
			if(books[i].getAuthor().equalsIgnoreCase(author)) {
				if(book == null || books[i].getPrice() < book.getPrice()) {
					book = books[i];
				}
			}
		}
		return book;
	}
	
	// adds a book to the lib (if full -> dont add, if exist -> keeps the cheapest)
	public boolean addBook(Book b) {
		for (int i = 0; i < numOfBooks; i++) {
			if(books[i].isSame(b)) {
				if(b.getPrice() < books[i].getPrice())
					books[i].setPrice(b.getPrice());
				return true;
			}
		}
		if(numOfBooks < books.length) 
			books[numOfBooks] = b;
		
		return numOfBooks++ < books.length;
	}
	
	// getting all the authors (no duplicats)
	public String[] getAllAuthors() {
		String[] authors = new String[numOfBooks];
		
		int idx = 0;
		
		for (int i = 0; i < authors.length; i++) {
			String x = books[i].getAuthor();
			
			Boolean isIn = false;
			// for each number in arr find if it have been seen
			for (int j = 0; j < idx; j++) {
				if(authors[j].equalsIgnoreCase(x)) {
					isIn = true;
					break;
				}
			}
			
			if(!isIn)
				authors[idx ++] = x;
		}
		
		return Arrays.copyOf(authors, idx);
	}
}
