//---------------------------------------------------------------------------------------
// Node.java
// Alex Vareljian
// avarelji
// Constructor for a node that contains an int, a reference to the previous Node in a list,
// and reference to the next Node in a list.
//---------------------------------------------------------------------------------------

public class Node{
	int element;
	public Node prev;
	public Node next;
	
	//constructor
	public Node(int x){
		element = x;
		prev = null;
		next = null;
	}
	
	// Set the prev link
	public void linkPrev( Node p ){
		prev = p;
	}
	
	// Set the next link
	public void linkNext ( Node n ){
		next = n;
	}
}