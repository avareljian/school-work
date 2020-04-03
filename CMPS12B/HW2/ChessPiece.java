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
}