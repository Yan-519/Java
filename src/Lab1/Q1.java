package Lab1;

import java.util.Arrays;
import java.util.Random;

public class Q1 {
	// return int[] with no duplicates
	public static int[] removeDuplicate(int[] arr) {
		int[] res =new int[arr.length];
		int idx = 0;
		
		for(int x : arr) {
			Boolean isIn = false;
			// for each number in arr find if it have been seen
			for (int i = 0; i < idx; i++) {
				if(res[i] == x) {
					isIn = true;
					break;
				}
			}
			
			if(!isIn)
				res[idx ++] = x;
		}
		
		return  Arrays.copyOf(res, idx);
	}
	
	// an in place sort of int[]
	public static void bubbleSort(int[] array) {
        int n = array.length;
        boolean swapped;
        
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    
                    swapped = true; 
                }
            }
           
            if (!swapped) {
                break;
            }
        }
    }

	// order (n % 2 == 0) in sorted at start of int[] and than all the (n % 2 == 1) in reverse sort at the end
	public static int[] orderEvenOdd(int[] input) {
		int[] arr;
		int[] mod0 = new int[0];
		int[] mod1 = new int[0];
		
		for (int tmp : input) {
			if(tmp % 2 == 0) {
				mod0 = Arrays.copyOf(mod0, mod0.length + 1);
				mod0[mod0.length - 1] = tmp;
			}
			else {
				mod1 = Arrays.copyOf(mod1, mod1.length + 1);
				mod1[mod1.length - 1] = tmp;
			}
		}
		
		bubbleSort(mod0);
		bubbleSort(mod1);
		
		arr = Arrays.copyOf(mod0, mod1.length + mod0.length);
		// add the mod1 from end to start
		for (int i = mod0.length, j = mod1.length - 1; i < arr.length; i++, j--)
			arr[i] = mod1[j];
		
		return arr;
	}
	
	// show number that appears the most
	public static void showMax(int[] arr) {
		int[] count = new int[31];
		
		for (int x : arr)
			count[x]++;
		
		int max = count[1], idx = 1;
		
		for (int i = 0; i < count.length; i++) {
			if(count[i] > max) {
				max = count[i];
				idx =i;
			}
		}
		
		System.out.println("element with max appearence is " + idx + " that appears " + max);

	}
	

	public static void main(String[] args) {
		Random rnd = new Random();
		
		int[] arr = new int[15];
		// generate the random 15 integers
		for(int i = 0;  i < arr.length; i++)
			arr[i] = rnd.nextInt(1, 31);
		
		System.out.println(Arrays.toString(arr));
		
		int[] newArr = Arrays.copyOf(arr, arr.length); 
		
		newArr = removeDuplicate(newArr);
		
		newArr = orderEvenOdd(newArr);

		System.out.println(Arrays.toString(newArr));
		
		showMax(arr);
	}

}
