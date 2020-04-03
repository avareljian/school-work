//---------------------------------------------------------------------------------------
// Node.java
// Alex Vareljian
// avarelji
// Constructor for a node that contains an Object, a reference to the previous Node in a list,
// and reference to the next Node in a list.
//---------------------------------------------------------------------------------------

public class Node{
	Object element;
	public Node prev;
	public Node next;
	
	/*---------- Constructor ----------*/
	public Node(Object x){
		element = x;
		prev = null;
		next = null;
	}
	
	/*---------- Access Funtions ----------*/
	
	// return previous Node
	Node prev(){
		return prev;
	}
	
	// return next entry
	Node next(){
		return next;
	}
	
	/*---------- Manipulation Functions ----------*/
	
	// Set the prev link
	public void linkPrev( Node p ){
		prev = p;
	}
	
	// Set the next link
	public void linkNext ( Node n ){
		next = n;
	}
	
	/*---------- Other Functions ----------*/
	
	// Overrides equals() in Object superclass
	public boolean equals(Object o){
		//Node x = (Node) o;
		
		if( this.element.equals(o) ){
			return true;
		}else{
			return false;
		}	
	}
	
	// overrides toString() in Object superclass
	public String toString(){
		return this.element.toString();
	}
}