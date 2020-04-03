//---------------------------------------------------------------------------------------
// Rook.java
// A subclass of ChessPiece representing a Rook
//---------------------------------------------------------------------------------------

public class Rook extends ChessPiece{
	
	//constructor creating a rook with a color(0= black, 1=white) a, column x, row y, type z
	public Rook(int a, int x, int y, char z){
		super(a, x, y, z);
	}
	
	//is self (a rook) attacking the chesspiece c (the input)
	public boolean isAttacking(ChessPiece c){
		if(color == c.color){ // are the pieces the same color? if so, not attacking
			return false;
		}else if(row == c.row){ //check if same row
			return true;
		}else if(col == c.col){ //check if same col
			return true;
		}else{
			return false;
		}
	}
}