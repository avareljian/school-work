//---------------------------------------------------------------------------------------
// Nodes.java
// constructor for a node that contains a reference to a chesspiece and
// a reference to the next node.
//---------------------------------------------------------------------------------------

public class Node{
	ChessPiece item;
	public Node next;
	
	//constructor
	public Node(ChessPiece newItem){
		item = newItem;
		next = null;
	}
}