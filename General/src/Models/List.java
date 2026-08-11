package Models;

public class List<T> {
	private Node<T> head;

	public List() {
		this.setHead(null);// this.head=null;
	}

	public Node<T> getHead() {
		return head;
	}

	public void setHead(Node<T> head) {
		this.head = head;
	}

	public Node<T> insert(T data) { // insert first
		Node<T> N = new Node<>(data);
		N.setNext(head);
		this.head = N;
		return N;
	}

	public Node<T> insert(Node<T> pos, T data) {
		Node<T> N = new Node<>(data);
		if (pos == null) {
			N.setNext(head);
			this.head = N;
			return N;
		} else {
			N.setNext(pos.getNext());
			pos.setNext(N);
			return N;
		}
	}

	public int len() {
		int count = 0;
		for (Node<T> a = getHead(); a != null; a = a.getNext()) {
			count++;
		}
		return count;
	}

	public boolean isEmpty() {
		return head == null;
	}

	public void prtList() {
		Node<T> a = this.head;
		while (a != null) {
			System.out.println(a);
			a = a.getNext();
		}
	}

	public Node<T> remove(Node<T> a) {
		if (a == null) {
			return head;
		}
		if (this.head == a) {
			this.head = this.head.getNext(); // a.getNext();
		} else {
			Node<T> temp = head;
			while (temp.getNext() != a && temp != null) {
				temp = temp.getNext();
			}
			if (temp != null) {
				temp.setNext(a.getNext());
			}
		}
		return head;
	}

	public Node<T> findInList(T data) {
		Node<T> temp = head;
		while (temp != null) {
			if (temp.getData() == data) {
				return temp;
			}
			temp = temp.getNext();
		}
		return null;
	}

	public static List<String> copy(List<String> list) {
		List<String> ans = new List<>();
		if (list == null) {
			return null;
		}
		Node<String> pos = null;
		for (Node<String> p = list.getHead(); p != null; p = p.getNext()) {
			pos = ans.insert(pos, p.getData());
		}
		return ans;
	}

	public static List<String> append(List<String> list1, List<String> list2) {
		if (list1 == null || list1.isEmpty()) {
			return copy(list2);
		}
		if (list2 == null || list2.isEmpty()) {
			return copy(list1);
		}
		List<String> list3 = copy(list1);
		Node<String> p = list3.getHead();
		while (p.getNext() != null) {
			p = p.getNext();
		}
		p.setNext(copy(list2).getHead());
		return list3;
	}
}
