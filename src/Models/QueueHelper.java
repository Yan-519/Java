package Models;

public class QueueHelper<T> {
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

}
