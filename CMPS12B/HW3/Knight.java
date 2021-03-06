//---------------------------------------------------------------------------------------
// Knight.java
// A subclass of ChessPiece representing a knight
//---------------------------------------------------------------------------------------

public class Knight extends ChessPiece{
	
	//constructor creating a knight with a color(0= black, 1=white) a, column x, row y, type z
	public Knight(int a, int x, int y, char z){
		super(a, x, y, z);
	}
	
	//is self (a Knight) attacking a chesspiece c (the input)
	public boolean isAttacking(ChessPiece c){
		if(color == c.color){ // are the pieces the same color? if so, not attacking
			return false;
		}else if((c.col == col-2) && (c.row == row+1)){ //c is left 2 AND up 1
			return true;
		}else if((c.col == col-2) && (c.row == row-1)){ //c is left 2 AND down 1
			return true;
		}else if((c.row == row+2) && (c.col == col-1)){ //c is up 2 AND left 1
			return true;
		}else if((c.row == row+2) && (c.col == col+1)){ //c is up 2 AND right 1
			return true;		
		}else if((c.col == col+2) && (c.row == row+1)){ //c is right 2 AND up 1
			return true;
		}else if((c.col == col+2) && (c.row == row-1)){ //c is right 2 AND down 1
			return true;
		}else if((c.row == row-2) && (c.col == col-1)){ //c is down 2 AND left 1
			return true;
		}else if((c.row == row-2) && (c.col == col+1)){ //c is down 2 AND right 1
			return true;
		}else{
			return false;
		}
	}
	
	//Is (destc, destr) in the range of self (a knight)
	public boolean canMoveTo(int destc, int destr){
		if((destc == col-2) && (destr == row+1)){ //c is left 2 AND up 1
			//System.out.println("Knight.canMoveTo is true");
			return true;
		}else if((destc == col-2) && (destr == row-1)){ //dest is left 2 AND down 1
			//System.out.println("Knight.canMoveTo is true");
			return true;
		}else if((destr == row+2) && (destc == col-1)){ //dest is up 2 AND left 1
			//System.out.println("Knight.canMoveTo is true");
			return true;
		}else if((destr == row+2) && (destc == col+1)){ //dest is up 2 AND right 1
			//System.out.println("Knight.canMoveTo is true");
			return true;		
		}else if((destc == col+2) && (destr == row+1)){ //dest is right 2 AND up 1
			//System.out.println("Knight.canMoveTo is true");
			return true;
		}else if((destc == col+2) && (destr == row-1)){ //dest is right 2 AND down 1
			//System.out.println("Knight.canMoveTo is true");
			return true;
		}else if((destr == row-2) && (destc == col-1)){ //dest is down 2 AND left 1
			//System.out.println("Knight.canMoveTo is true");
			return true;
		}else if((destr == row-2) && (destc == col+1)){ //dest is down 2 AND right 1
			//System.out.println("Knight.canMoveTo is true");
			return true;
		}else{
			//System.out.println("Knight.canMoveTo is false");
			return false;
		}
	}
}