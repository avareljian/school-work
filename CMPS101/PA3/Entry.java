//---------------------------------------------------------------------------------------
// Entry.java
// Alex Vareljian
// avarelji
// Constructor for an entry of a matrix for sparce Matrix ADT. 
// Contains a double (value of the entry), and an int value (column of the entry).
//---------------------------------------------------------------------------------------

class Entry{
	int col;
	double value;
	
	/*---------- Constructor ----------*/
	Entry(int x, double y){
		col = x;
		value = y;
	}
	
	/*---------- Access Funtions ----------*/
	
	// return value
	double value(){
		return value;
	}
	
	// return column
	int col(){
		return col;
	}
	
	/*---------- Other Funtions ----------*/
	
	// Overrides equals() in Object superclass
	public boolean equals(Object o){
		Entry x = (Entry) o;
		
		if( (this.col == x.col) && (this.value == x.value) ){
			return true;
		}else{
			return false;
		}
	}
	
	// overrides toString() in Object superclass
	public String toString(){
		return ("("+col+", "+value+")");
	}
}