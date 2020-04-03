//---------------------------------------------------------------------------------------
//NQueens.java
//Reads in file containing board size and placement of first queen
//Prints the solution to an output file, or No Solution
//---------------------------------------------------------------------------------------
import java.io.*;
import java.util.Scanner;
import java.lang.*;
import java.util.Arrays;

class NQueens{
	//static int[][] solution;
	static int [] solution;
	
	public static void main( String[] args ){
		
		/*//checks for exactly two command line arguments
		if (args.length != 2 ){
			System.out.println("Usage: java -jar NQueens.jar <input file> <output file>");
			System.exit(1);
		}*/
		/*//open files
		Scanner inFile = new Scanner(new File(args[0]));
		PrintWriter outFile = new PrintWriter(new FileWriter(args[1]));
		
		//some code reading the file
		while( inFile.hasNextLine() ){
			int N = inFile.nextInt();
			int C = inFile.nextInt();
			int R = inFile.nextInt();
			//run the code with these numbers, then repeat 
		}*/
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter three integers <column> <row> <n>: ");
		int c = reader.nextInt();
		int r = reader.nextInt();
		int n = reader.nextInt();
		
		
		solve(c, r, n);
		printColRow(solution);
		
		
	}
	static void placeFirstQueen(int c, int r){
		if (c != 1){
			System.out.println("No solution");
		}
		solution[c] = r;
	}
	//can a queen be placed at c,r
	static boolean isSolution(int c, int r){
		//going through the columns
		for(int i = 0; i<c; i++){
			//check if any of the same rows
			if(solution[i] == r){
				return false;
			}
			//check for diagonals
			if((i-c)/(solution[i]-r) == 1 ||
			(i-c)/(solution[i]-r) == -1){
				return false;
			}
		}
		return true;
	}
	static void NQueens(int c, int n) {
		for (int i = 0; i < n; i++) {
			//check if queen at xth row can be placed at i-th column.
			if (isSolution(c, i)) {
				solution[c] = i; // place the queen at this position.
				if (c == n - 1) {
					printColRow(solution);
				}
				NQueens(c + 1, n);
			}
		}
	}

	
	
	
	
	
	static void printColRow(int [] board){
		
		for(int i=0; i<board.length; i++){
			System.out.print(i + " " + board[i] + " ");
		}
		System.out.println();
	}
	static void solve(int c, int r, int n){
		solution = new int[n];
		placeFirstQueen(c, r);
		NQueens(2, n);
	}
}
		
	
	
	
	
	
	
	
	
	
	
	
	/*//can a queen be placed in a two-d array [row][col]
	static boolean	isSolution(int[][] board, int row, int col){
		//check if any other queens in that row
		for(int j = 1; j<board.length; j++){
			if(board[row][j] == 1){
				return false;
			}
		}
		//check if any other queens in that col
		for(int i = 1; i<board.length; i++){
			if(board[i][col] == 1){
				return false;
			}
		}
		//check if queens in left/up diagonal
		for(int i = row-1, j = col-1; i>0 && j>0; i--, j--){
			if(board[i][j] == 1){
				return false;
			}
		}
		//check if queens in left/down diagonal
		for(int i = row+1, j = col-1; i<board.length && j>0; i++, j--){
			if (board[i][j] == 1){
				return false;
			}
		}
		//check if queens in right/up diagonal
		for(int i = row-1, j = col+1; i>0 && j<board.length; i--, j++){
			if (board[i][j] ==1){
				return false;
			}
		}
		//check if queens in right/down diagonal
		for(int i = row+1, j = col+1; i<board.length && j<board.length; i++, j++){
			if (board[i][j] == 1){
				return false;
			}
		}
		//if you are here, that means it is safe to put place a queen at row,col
		//or, isSolution is true
		return true;
	}
	
	//q is what # queen is being placed/which column we're in
	static boolean nQueens(int q, int[][] board){
		
		for(int row=1; row<board.length; row++){
			//if it is not attacking any other queens, place it
			if(isSolution(solution, row, q)){
				solution[row][q] = 1;
				
				//place next queen
				if(nQueens(q+1, solution)){
					return true;
				}
				//here means that placing this queen did not work and must backtrack
				solution[row][q]=0;
			}
		}
		if(q == board.length-1 ){
			return true;
		}
		//here means no solution
		return false;
	}
	
	//function to place the first queen
	public static void placeFirstQueen(int r, int c){
		solution[r][c] = 1;
		return;
	}
	
	public static void printBoard(int[][] board){
		for(int i = 1; i<board.length; i++){
			for(int j = 1; j<board.length; j++){
				System.out.print(" " + board[i][j]);
			}
			System.out.println();
		}
	}
	
	public static void solve(int c, int r, int n){
		solution = new int[n+1][n+1];
			for(int i=1; i<=n; i++){
				for (int j=1; j<=n; j++){
					solution[i][j] = 0;
				}
			}
		
		placeFirstQueen(r,c);
		if(nQueens(2, solution)){
			printBoard(solution);
		}else{
			System.out.println("No solution");
		}
	}
}
*/	
	
	
	
		
		
		
		
		
		
		

	
	
	
	
	
	
	

