package Lab6;

public class Q3 {
	
	public static boolean contains(Queue<Integer> q, int n) {
		Queue<Integer> tmp = new Queue<Integer>();
		boolean isIn = false;
		
		while(!q.isEmpty()) {
			isIn |= q.Head() == n;
			tmp.insert(q.remove());
		}
		
		while(!tmp.isEmpty())
			q.insert(tmp.remove());
		
		return isIn;
	}
	
	// if q, buffer contains two numbers so their sum is n
	public static boolean containsPairToSum(Queue<Integer> q, Queue<Integer> buffer, int n) {
		Queue<Integer> tmp = new Queue<Integer>();
		boolean isGood = false;
		
		while(!q.isEmpty()) {
			
			int current = q.Head();
			isGood |= contains(buffer, n - current);
			isGood |= contains(tmp, n - current);
			
			tmp.insert(q.remove());
		}
		
		while(!tmp.isEmpty())
			q.insert(tmp.remove());
		
		return isGood;
	}
	
	public static boolean validateQueue(Queue<Integer> q) {
		if(q.isEmpty()) return true;
		
		Queue<Integer> tmp = new Queue<Integer>();
		boolean isGood = true;
		
		while(!q.isEmpty()) {
			if( 100 <= q.Head() && q.Head() <= 999)
				isGood &= containsPairToSum(q, tmp, q.Head());
			
			tmp.insert(q.remove());
		}
		
		while(!tmp.isEmpty())
			q.insert(tmp.remove());
		
		return isGood;
	}


	public static void main(String[] args) {
		Queue<Integer> q = new Queue<Integer>();
		q.insert(22);
		q.insert(150);
		q.insert(74);
		q.insert(120);
		q.insert(76);
		q.insert(40);
		q.insert(80);
		q.insert(35);
		
		System.out.println(validateQueue(q));
		
		q = new Queue<Integer>();
		q.insert(22);
		q.insert(150);
		q.insert(4);
		q.insert(120);
		q.insert(76);
		q.insert(33);
		q.insert(14);
		q.insert(35);
		
		System.out.println(validateQueue(q));
	}

}
