//---------------------------------------------------------------------------------------
// List.java
// Alex Vareljian
// avarelji
// Implementation of a doubly linked list ADT. Includes inplementation of a cursor and
// functions to move the cursor.
// This is a list of Entry objects, because this List ADT will be used to implement a
// Matrix ADT.
//---------------------------------------------------------------------------------------

public class List{
	
	private Node head; // Pointer to start of list
	private Node tail; // Pointer to end of list
	private Node cursor; // Pointer to "current" Entry. Not necessarily defined.
	private int index; // Index of the cursor from 0-(length-1)
	private int length; // Number of elements in the list
	
	// Constructor
	List(){
		head = null;
		tail = null;
		cursor = null;
		index = -1;
		length = 0;
	}
	
	/* ----- Access Functions ----- */
	// Returns the length of elements in the list
	public int length(){
		return length;
	}
	
	// If cursor is defined, returns the index of the cursor element. Otherwise, returns -1.
	public int index(){
		if( index > length-1 || index < 0 ){
			index = -1;
		}
		return index;
	}
	
	// Returns the front Entry
	public Object front(){
		if( length == 0 ){
			throw new RuntimeException("Error: front() was called on empty list");
		}
		return head;
	}
	
	// Returns the back element
	public Object back(){
		if( length == 0 ){
			throw new RuntimeException("Error: back() was called on empty list");
		}
		return tail;
	}
	
	// Returns cursor element
	public Object get(){
		if( length == 0 ){
			throw new RuntimeException("Error: get() was called on empty list");
		}
		if( cursor == null ){
			throw new RuntimeException("Error: get() was called on an undefined cursor");
		}
		return cursor.element;
	}
	
	// Returns true if and only if this List and L are the same Entry sequence.
	// The states of the cursors in the two Lists are not used to determine equality.
	public boolean equals( Object O ){
		List L = (List) O;
		
		this.moveFront();
		L.moveFront();
		
		if( this.length() == L.length() ){
			while ( (this.index() >= 0) && (L.index() >= 0) ){
				Node A = (Node) this.front();
				Node B = (Node) L.front();
				
				if( !(A.element.equals(B.element)) ){
					return false;
				}
				this.moveNext();
				L.moveNext();
			}
			return true;
		}else{
			return false;
		}
	}

	
	/* ----- Manupulation Procedures ----- */
	// Resets the List to its original empty list
	void clear(){
		head = null;
		tail = null;
		cursor = null;
		index = -1;
		length = 0;
	}
	
	// If List is non-empty, places the cursor under the front element. Otherwise does nothing.
	void moveFront(){
		if( length > 0 ){
			cursor = head;
			index = 0;
		}
	}
	
	// If List is non-empty, places the cursor under the back element,
	// otherwise does nothing.
	void moveBack(){
		if( length > 0){
			cursor = tail;
			index = length-1;
		}
	}
	
	// If cursor is defined and not at front, moves cursor one step toward
	// front of this List, if cursor is defined and at front, cursor becomes
	// undefined, if cursor is undefined does nothing.	
	void movePrev(){
		// If cursor is defined and not at head
		if( (index() > 0) && (index() < length()) ){
			cursor = cursor.prev;
			index--;
		}else{
			cursor = null;
			index = -1;
		}
	}		

	// If cursor is defined and not at back, moves cursor one step toward
	// back of this List, if cursor is defined and at back, cursor becomes
	// undefined, if cursor is undefined does nothing.
	void moveNext(){
		// If cursor is defined and not at tail
		if( (index() > -1) && (index() < length()-1) ){
			cursor = cursor.next;
			index++;
		}else{
			cursor = null;
			index = -1;
		}
	}

	
	// Insert new element into this List. If List is non-empty,
	// insertion takes place before front element.
	void prepend(Object x){
		Node latest = new Node(x);
		
		if( head == null ){ // if the list was empty
			head = latest;
			tail = head;
			length++;
		}else{
			head.linkPrev(latest);
			latest.linkNext(head);
			head = latest;
			index++;
			length++;
			
		}
	}
	
	// Insert new element into this List. If List is non-empty,
	// insertion takes place after back element.
	void append(Object x){
		Node latest = new Node(x);
		
		if (length == 0 ){ // if the list was empty;
			head = latest;
			tail = head;
		}else{
			latest.linkPrev(tail);
			tail.linkNext(latest);
			tail = latest;
		}
		length++;
	}
	
	// Insert new element before cursor.
	// Pre: length()>0, index()>=0
	void insertBefore(Object x){
		if( length()<=0 || index()< 0 ){
			throw new RuntimeException("Error: insertBefore was called on an empty list, or an undefined cursor");
		}else{
			if( index == 0 ){
				prepend(x);
				return;
			}
			
			Node temp = cursor.prev;
			Node latest = new Node(x);
			
			latest.linkPrev(temp);
			latest.linkNext(cursor);
			
			temp.linkNext(latest);
			cursor.linkPrev(latest);
		
			index++;
			length++;
		}
	}	
	
	// Inserts new element after cursor.
	// Pre: length()>0, index()>=0
	void insertAfter(Object x){
		if( length()<=0 || index()<0 ){
			throw new RuntimeException("Error: insertAfter was called on an empty list, or an undefined cursor");
		}else{
			if( index == length()-1 ){
				append(x);
				return;
			}else{
				Node temp = cursor.next;
				Node latest = new Node(x);
				
				latest.linkPrev(cursor);
				latest.linkNext(temp);
		
				temp.linkPrev(latest);
				cursor.linkNext(latest);
		
				length++;	
			}
		}
	}
	
	// Deletes the front element. Pre: length()>0
	void deleteFront(){
		if( length == 0 ){
			throw new RuntimeException("Error: deleteFront() called on empty list");
		}
		// If cursor is at head
		if( index == 0 ){
			head = head.next;
			cursor = null;
			index = -1;
			length--;
		}else if( length == 1 ){
			tail = head = null;
			length = 0;
		}else{
			head = head.next;
			index--;
			length--;
		}
	}
	
	// Deletes the back element. Pre: length()>0
	void deleteBack(){
		if( length == 0 ){
			throw new RuntimeException("Error: deleteBack() called on empty list");
		}
		// If cursor is at tail
		if( index == length-1 ){
			tail = tail.prev;
			cursor = null;
			index = -1;
			length--;
		}else if( length == 1 ){
			tail = head = null;
			length = 0;
		}else{
			tail = tail.prev;
			tail.linkNext(null);
			length--;
		}
	}
	
	// Deletes cursor element, making cursor undefined.
	// Pre: length()>0, index()>=0
	void delete(){
		// if cursor is defined
		if( length()<=0 || index()<0 ){
			throw new RuntimeException("Error: delete() called on an empty list or cursor is undefined");
		}else{
			if( length() == 1 ){
				head = tail = cursor = null;
				length = 0;
				index = -1;
			}
			
			// If cursor is at head
			else if( index == 0 ){
				head = head.next;
				head.linkPrev(null);
				cursor = null;
				index = -1;
				length--;
			}
			
			// If cursor is at tail
			else if( index == length-1 ){
				tail = tail.prev;
				cursor = null;
				index = -1;
				length--;
			}
			
			
			else{
				Node before = cursor.prev;
				Node after = cursor.next;
			
				before.next = after.prev;
				after.prev = before.next;
			
				cursor = null;
				index = -1;
				length--;
			}
		}
	}
	
	// Overrides Object's toString method. Returns a String
	// representation of this List consisting of a space
	// separated sequence of integers, with front on left.
	public String toString(){
		String str = "";
		Node current = head;
		
		while( current != null ){
			str += current.toString()+" ";
			current = current.next;
		}
		
		return str;
	}
}