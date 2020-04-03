//---------------------------------------------------------------------------------------
// ChessPiece.java
// A superclass representing chess pieces
//---------------------------------------------------------------------------------------


public class ChessPiece{
	public int color;
	public int row;
	public int col;
	public char type;
	

	//constructor creating a chess piece with a color(0= black, 1=white) a, column x, row y, type z
	public ChessPiece(int a, int x, int y, char z){
		color = a;
		col = x;
		row = y;
		type = z;
	}
	
	//placeholding(?) bool isAttacking
	public boolean isAttacking(ChessPiece c){
		return false;
	}
	
	//placeholding bool canMoveTo
	public boolean canMoveTo(int destc, int destr){
		return false;
	}
	
	//is self (a chess piece) equal to the the inputted chesspiece
	public boolean isEqual(ChessPiece c){
		if( col == c.col && row == c.row && color == c.color && type == c.type){
			return true;
		}else{
			return false;
		}
	}
}