package Lecture6.Moduls;

public class Stack<T> {
	private Node<T> first;

	public Stack() {
		this.first = null;
	}

	public void push(T data) {
		first = new Node<T>(data, first);
	}

	public void push(Node<T> node) {
		if (node != null) {
			node.setNext(first);
			first = node;
		}
	}

	public boolean isEmpty() {
		return first == null;
	}

	public T pop() {
		T data = first.getData();
		first = first.getNext();
		return data;
	}

	public T peek() { // top
		return first.getData();
	}

	@Override
	public String toString() {
		Node<T> temp = first;
		if (temp != null) {
			String str = "[";
			while (temp.getNext() != null) {
				str = str + temp.getData().toString();
				str = str + " , ";
				temp = temp.getNext();
			}
			str = str + temp.getData().toString();
			str = str + "]";
			return str;
		} else {
			return "[]";
		}
	}
}