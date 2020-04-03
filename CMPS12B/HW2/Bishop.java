//---------------------------------------------------------------------------------------
// Bishop.java
// A subclass of ChessPiece representing a Bishop
//---------------------------------------------------------------------------------------

public class Bishop extends ChessPiece{
	
	//constructor creating a bishop with a color(0= black, 1=white) a, column x, row y, type z
	public Bishop(int a, int x, int y, char z){
		super(a, x, y, z);
	}

	//is self (a bishop) attacking the chesspiece c (the input)
	public boolean isAttacking(ChessPiece c){
		if(color == c.color){ //are they the same color? if so, not attacking
			return false;
		}else if(Math.abs(row-c.row) == Math.abs(col-c.col)){ //check if on diagonal
			return true;
		}else{
			return false;
		}
	}
}