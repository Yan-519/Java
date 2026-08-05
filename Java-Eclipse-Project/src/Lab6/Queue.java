package Lab6;

public class Queue<T> {
	private Node<T> first;
	private Node<T> last;

	public Queue() {
		this.first = null;
		this.last = null;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public void insert(T data) {
		Node<T> temp = new Node<>(data);
		if (first == null) {
			first = temp;
//last = temp;
		} else {
			last.setNext(temp);
//last = temp;
		}
		last = temp;
	}

	@Override
	public String toString() {
		String str = "[";
		Node<T> temp = first;
		if (temp != null) {
			while (temp != null) {
				str = str + temp.getData().toString();
				if (temp.getNext() != null) {
					str = str + ",";
				}
				temp = temp.getNext();
			}
			str = str + "]";
			return str;
		} else {
			return "[]";
		}
	}

	public T remove() {
		T data = first.getData();
		first = first.getNext();
		if (first == null) {
			last = null;
		}
		return data;
	}
	
	public T Head() {
		return first.getData();
	}
}