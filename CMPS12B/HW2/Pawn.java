//---------------------------------------------------------------------------------------
// Pawn.java
// A subclass of ChessPiece representing a pawn
//---------------------------------------------------------------------------------------

public class Pawn extends ChessPiece{
	
	//constructor creating a pawn with a color(0= black, 1=white) a, row x, column y
	public Pawn(int a, int x, int y){
		super(a, x, y);
	}
	
	//is self (a pawn) attacking the chesspiece c (the input)
	public boolean isAttacking(Chesspiece c){
		if(color == c.color){ //are they the same color? if so, not attacking
			return false;
		}else if((row+1 == c.row) && (col+1 == c.col)){ //check right diagonal (up one, right one)
			return true;
		}else if((row+1 == c.row) && (col-1 == c.col)){ //check left diagonal (up one, left one)
			return true;
		}else{
			return false;
		}
	}
}