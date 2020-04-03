//----------------------------------------------------------------------------------------
// Queen.java
// A subclass of ChessPiece representing a queen
//---------------------------------------------------------------------------------------

public class Queen extends ChessPiece{
	
	//constructor creating a queen with a color(0= black, 1=white) a, column x, row y, type z
	public Queen(int a, int x, int y, char z){
		super(a, x, y, z);
	}
	
	//Is self (a queen) attacking the chesspiece c (the input)
	public boolean isAttacking(ChessPiece c){
		if(color == c.color){ // are the pieces the same color? if so, not attacking
			return false;
		}else if(row == c.row || col == c.col){ //check if same row or col
			return true;
		}else if(Math.abs(row-c.row) == Math.abs(col-c.col)){ //check if same diagonal
			return true;
		}else{
			return false;
		}
	}
	
	//Is (destc, destr) in the range of self (a queen)
	public boolean canMoveTo(int destc, int destr){
		if(destr == row || destc == col){ //check if same row or col
			//System.out.println("Queen.canMoveTo is true");
			return true;
		}else if(Math.abs(destr-row) == Math.abs(destc-col)){ //check if same diagonal
			//System.out.println("Queen.canMoveTo is true");
			return true;
		}else{
			//System.out.println("Queen.canMoveTo is false");
			return false;
		}
	}	
}