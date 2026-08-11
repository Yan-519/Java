package Lab1;

import java.util.Arrays;
import java.util.Scanner;

public class Q4 {
	// matrix size
	public static final int ROW = 5, COL = 5;
	
	// return dots with value num from given (startRow,  startCol) with moving (rowD, colD)
	public static int[][] getDotsInDerection(int[][] matrix, int num, int startRow, int startCol, int rowD, int colD, int max) {
		int curMax = 0;
		
		int[][] res = new int[5][2];
		// move by given direction while numbers == num and saving dots
		for(int i = startRow, j = startCol; i < ROW && j < COL && matrix[i][j] == num; i += rowD, j += colD) {
			res[curMax][0] = i;
			res[curMax][1] = j;
			curMax++;
		}
		
		return Arrays.copyOf(res, curMax);
	}

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		
		int[][] matrix = new int[ROW][COL];
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COL; j++) {
				System.out.print("Enter a number for ("+ i + ", " + j + "): ");
				matrix[i][j] = in.nextInt();
			}
		}
		
		System.out.print("Enter the num to find: ");
		int num = in.nextInt();
		
		in.close();
		
		int max = 0;
		int[][] res = new int[0][0], tmp;
		
		for(int i = 0; i < ROW; i++) {
			for(int j = 0; j < COL; j++) {
				// check path right
				tmp = getDotsInDerection(matrix, num, i, j, 0, 1, max);
				if(max < tmp.length) {
					max = tmp.length;
					res = tmp;
				}
				// check path down
				tmp = getDotsInDerection(matrix, num, i, j, 1, 0, max);
				if(max < tmp.length) {
					max = tmp.length;
					res = tmp;
				}
			}
		}
		
		if(max == 0) 
			System.out.println("No paths");
		
		else {
			for(int[] dot : res)
				System.out.println(dot[0] + ", " + dot[1]);
		}
		
	}

}
