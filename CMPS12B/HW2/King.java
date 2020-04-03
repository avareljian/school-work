//----------------------------------------------------------------------------------------
// King.java
// A subclass of ChessPiece representing a king
//---------------------------------------------------------------------------------------

public class King extends ChessPiece{
	
	//constructor creating a king with a color(0= black, 1=white) a, column x, row y, type z
	public King(int a, int x, int y, char z){
		super(a, x, y, z);
	}
	
	//is self (a king) attacking a chesspiece c (the input)
	public boolean isAttacking(ChessPiece c){
		if(color == c.color){ // are the pieces the same color? if so, not attacking
			return false;
		}else if((c.row == row+1) && (c.col == col)){ //c is up 1 and in same col
			return true;
		}else if((c.row == row-1) && (c.col == col)){ //c is down 1 in same col
			return true;
		}else if((c.col == col-1) && (c.row == row)){ //c is left 1 and in same row
			return true;
		}else if((c.col == col+1) && (c.row == row)){ //c is right 1 and in same row
			return true;
		}else if((c.row == row+1) && (c.col == col-1)){ //c is up 1 AND left 1
			return true;
		}else if((c.row == row+1) && (c.col == col+1)){ //c is up 1 AND right 1
			return true;	
		}else if((c.row == row-1) && (c.col == col-1)){ //c is down 1 AND left 1
			return true;
		}else if((c.row == row-1) && (c.col == col+1)){ //c is down 1 AND left 1
			return true;
		}else{
			return false;
		}
	}
}
