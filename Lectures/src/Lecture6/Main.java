package Lecture6;

import Lecture6.Moduls.*;

public class Main {

	public static Integer getLast(Node<Integer> node) {
		Node<Integer> curNode = node;
		for (; curNode.getNext() != null; curNode = curNode.getNext()) {

		}
		return curNode.getData();
	}

	public static List<Integer> Task1(Node<Node<Integer>> lst) {
		List<Integer> resNode = new List<>();

		for (Node<Node<Integer>> curNode = lst; curNode != null; curNode = curNode.getNext()) {
			resNode.insert(getLast(curNode.getData()));
		}

		return resNode;
	}
	
	public static int count(Queue<Integer> q, int a){
		Queue<Integer> tmp = new Queue<Integer>();
		int c = 0;
		
		while (!q.isEmpty()) {
			int cur = q.remove();
				tmp.insert(cur);

			if(cur == a)
				c++;
		}
		
		while (!tmp.isEmpty()) 
			q.insert(tmp.remove());
		
		return c;
	}
	
	public static boolean cheackBingo(Queue<Integer> q) {
		Queue<Integer> tmp = new Queue<Integer>();
		boolean res = true;
		
		while (!q.isEmpty()) {
			int cur = q.remove();
			tmp.insert(cur);
			res &= count(q, cur) % 2 == 1;
		}
		
		while (!tmp.isEmpty()) 
			q.insert(tmp.remove());
		
		return res;
	}
	
	public static int elementAt(Queue<Integer> q, int j) {
	    int size = 0;

	    // Count elements
	    while (!q.isEmpty()) {
	        int x = q.remove();
	        q.insert(x);
	        size++;
	        if (q.Head() == x)
	            break;
	    }

	    if (j < 0 || j >= size)
	        throw new IndexOutOfBoundsException();

	    int ans = 0;
	    for (int i = 0; i < size; i++) {
	        int x = q.remove();
	        if (i == j)
	            ans = x;
	        q.insert(x);
	    }

	    return ans;
	}
	
	public static void removeAt(Queue<Integer> q, int j) {
	    int size = 0;

	    // Count elements
	    while (!q.isEmpty()) {
	        int x = q.remove();
	        q.insert(x);
	        size++;
	        if (q.Head() == x)
	            break;
	    }

	    if (j < 0 || j >= size)
	        throw new IndexOutOfBoundsException();

	    for (int i = 0; i < size; i++) {
	        int x = q.remove();
	        if (i != j)
	            q.insert(x);
	    }
	}
	
	public static void bingo(Queue<Integer> q, int a, int b) {
		if(a == b) return;
		if(elementAt(q, b) == elementAt(q, a)) {
			removeAt(q, Math.max(a, b));
			removeAt(q, Math.min(a, b));
		}
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
