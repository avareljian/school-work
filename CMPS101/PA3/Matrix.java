//-----------------------------------------------------------------------------------------------------------
// Matrix.java
// Alex Vareljian
// avarelji
// Implementation of a sparce Matrix ADT. Only stores non-zero entries, aka NNZ. Maintains 
// an array of Lists (list of Entry objects.) Rows of the array correspond to the rows of the entries.
//-----------------------------------------------------------------------------------------------------------

public class Matrix{
	List[] row;
	int size;
	int nnz;
	
	/*---------- Constructor ----------*/
	// Makes a new n x n zero Matrix. pre: n>=1
	Matrix(int n){
		row = new List[n+1];
		size = n;
		nnz = 0;
		
		// Fill the matrix with empty lists
		for(int i = 0; i<=size; i++){
			row[i] = new List();
		}
	}
	
	
	/*---------- Access Functions ----------*/
	// Returns n, the number of rows and columns of this Matrix
	int getSize(){
		return this.size;
	}
	
	// Returns the number of non-zero entries in this Matrix
	int getNNZ(){
		return this.nnz;
	}
	
	/*---------- Manipulation procedures ----------*/
	
	// sets this Matrix to the zero state
	void makeZero(){
		if( nnz == 0){
			throw new RuntimeException("Matrix error: called makeZero() on a matrix that is already zero");
		}
		for( int i = 1; i<= size; i++ ){
			row[i].clear();
		}
		nnz = 0;
	}
	
	// returns a new Matrix having the same entries as this Matrix
	Matrix copy(){
		Matrix Q = new Matrix(size);
		
		for(int i = 1; i<= size; i++ ){
			if( row[i].length() !=0 ){
				row[i].moveFront();
				while( row[i].index() >= 0 ){
					Entry x = (Entry) row[i].get();
					x = new Entry(x.col, x.value);
					
					Q.row[i].append(x);
					row[i].moveNext();
				}
			}
		}
		
		Q.nnz = this.nnz;
		return Q;
	}
	
	// changes ith row, jth column of this Matrix to x
	// pre: 1<=i<=getSize(), 1<=j<=getSize()
	void changeEntry(int i, int j, double x){
		Entry latest = new Entry(j, x);
		
		// two cases when the list at row i is empty
		if( row[i].length() == 0 ){
			if( x != 0 ){
				row[i].append(latest);
				nnz++;
				return;
			}
			if( x == 0 ){
				return;
			}
		}
		
		row[i].moveFront();
		
		// in the cases that the latest entry != 0
		// must insert latest entry at correct position, sorted by column
		if( x != 0 ){
			Node tmp1 = (Node) row[i].front();
			Node tmp2 = (Node) row[i].back();
			Entry head = (Entry) tmp1.element;
			Entry tail = (Entry) tmp2.element;
			
			if( head.col() > j ){
				row[i].prepend(latest);
				nnz++;
				return;
			}
			if( tail.col() < j ){
				row[i].append(latest);
				nnz++;
				return;
			}
			while( row[i].index() >= 0 ){
				Entry current = (Entry) row[i].get();
				if(current.col() == j ){
					current.value = x;
					return;
				}
				if( current.col() > j ){
					row[i].insertBefore(latest);
					nnz++;
				}
				row[i].moveNext();
			}
		}
		// in the case that x == 0
		if( x == 0 ){
			row[i].moveFront();
			while( row[i].index() >= 0 ){
				Entry current = (Entry) row[i].get();
				
				if( current.col() == j ){
					row[i].delete();
					nnz--;
				}
				row[i].moveNext();
			}
		}			
	}

	
	// returns a new Matrix that is the scalar product of this Matrix with x
	Matrix scalarMult(double x){
		Matrix M = new Matrix(size);
		
		for( int i = 1; i <= M.size; i++ ){
			if( row[i].length() == 0 ){
				continue;
			}else{
				row[i].moveFront();
				while( row[i].index() >= 0 ){
					Entry current = (Entry) row[i].get();
					current = new Entry(current.col, current.value*x);
					
					M.row[i].append(current);
					row[i].moveNext();
				}
			}
		}
		
		M.nnz = this.nnz;
		return M;
	}

	
	// returns a new Matrix that is the sum of this Matrix with M
	// pre: getSize()==M.getSize()
	Matrix add(Matrix M){
		if ( this.size != M.size ) {
	      throw new RuntimeException("Matrix Error: add() called on Matrices of different sizes");
		}
		
		if( this.equals(M) ){
			return this.scalarMult(2.0);
		}
		
		Matrix P = new Matrix(size);
		
		
		for( int i = 1; i<= size; i++){ // iterate through the rows
			if( (this.row[i].length() == 0) && (M.row[i].length() == 0) ){ // if both rows are rows of zeros i.e. both lists are empty
				continue;
			}
			if( (this.row[i].length() == 0) && (M.row[i].length() > 0) ){ // if this row is zeros but M row has nnz entries, copy M.row[i] to P.row[i]
				M.row[i].moveFront();
				while( M.row[i].index() >= 0 ){
					Entry current = (Entry) M.row[i].get();
					current = new Entry( current.col, current.value );
					
					P.row[i].append(current);
					P.nnz++;
					M.row[i].moveNext();
				}
			}
			if( (this.row[i].length() > 0) && (M.row[i].length() == 0) ){ // if this row has nnz entries but M row is zeros, copy this.row[i] to P.row[i]
				this.row[i].moveFront();
				while( this.row[i].index() >= 0 ){
					Entry current = (Entry) this.row[i].get();
					current = new Entry (current.col, current.value );
					
					P.row[i].append(current);
					P.nnz++;
					this.row[i].moveNext();
				}
			}
			if( (this.row[i].length() > 0 ) && M.row[i].length() > 0 ){ // both rows have nnz entries
				this.row[i].moveFront();
				M.row[i].moveFront();
				
				Entry A = (Entry) this.row[i].get();
				Entry B = (Entry) M.row[i].get();
				
				while( (this.row[i].index() >= 0) && (M.row[i].index() >= 0) ){ // iterate through the lists
					A = (Entry) this.row[i].get();
					B = (Entry) M.row[i].get();
					
					if( A.col == B.col ){
						//System.out.println("in if");
						Entry C = new Entry(A.col, (A.value + B.value));
						if(C.value != 0 ){
							//System.out.println("in second if");
							P.row[i].append(C);
							P.nnz++;
						}
						this.row[i].moveNext();
						M.row[i].moveNext();
						continue;
					}
					else if( A.col < B.col ){
						Entry C = new Entry(A.col, A.value);
						P.row[i].append(C);
						P.nnz++;
						
						this.row[i].moveNext();
						continue;
					}
					else if( B.col < A.col ){
						Entry C = new Entry(B.col, B.value);
						P.row[i].append(C);
						P.nnz++;
						
						M.row[i].moveNext();
						continue;
					}
				}
				// At this point, this.row or M.row might have some nnz elements left over, if the lists weren't the same length
				// Append the potentially left over elements to the new matrix in row i
				while( this.row[i].index() >= 0 ){
					Entry C = new Entry(A.col, A.value);
					P.row[i].append(C);
					P.nnz++;
					this.row[i].moveNext();
				}
				while( M.row[i].index() >= 0 ){
					Entry C = new Entry(B.col, B.value);
					P.row[i].append(C);
					P.nnz++;
					M.row[i].moveNext();
				}
			}
		}
		return P;
	}
	
	// returns a new Matrix that is the difference of this Matrix with M
	// pre: getSize()==M.getSize()
	Matrix sub(Matrix M){
		if ( this.size != M.size ) {
	      throw new RuntimeException("Matrix Error: sub() called on Matrices of different sizes");
		}
		
		Matrix P = new Matrix(size);
		
		if( this.equals(M) ){
			return P;
		}
		
		for( int i = 1; i<= size; i++){ // iterate through the rows
			if( (this.row[i].length() == 0) && (M.row[i].length() == 0) ){ // if both rows are rows of zeros i.e. both lists are empty
				continue;
			}
			if( (this.row[i].length() == 0) && (M.row[i].length() > 0) ){ // if this row is zeros but M row has nnz entries, copy (-1)M.row[i] to P.row[i]
				M.row[i].moveFront();
				while( M.row[i].index() >= 0 ){
					Entry current = (Entry) M.row[i].get();
					current = new Entry( current.col, (current.value)*(-1.0) );
					
					P.row[i].append(current);
					P.nnz++;
					M.row[i].moveNext();
				}
			}
			if( (this.row[i].length() > 0) && (M.row[i].length() == 0) ){ // if this row has nnz entries but M row is zeros, copy this.row[i] to P.row[i]
				this.row[i].moveFront();
				while( this.row[i].index() >= 0 ){
					Entry current = (Entry) this.row[i].get();
					current = new Entry (current.col, current.value );
					
					P.row[i].append(current);
					P.nnz++;
					this.row[i].moveNext();
				}
			}
			if( (this.row[i].length() > 0 ) && M.row[i].length() > 0 ){ // both rows have nnz entries
				this.row[i].moveFront();
				M.row[i].moveFront();
				
				Entry A = (Entry) this.row[i].get();
				Entry B = (Entry) M.row[i].get();
				
				while( (this.row[i].index() >= 0) && (M.row[i].index() >= 0) ){ // iterate through the lists
					A = (Entry) this.row[i].get();
					B = (Entry) M.row[i].get();
					
					if( A.col == B.col ){
						Entry C = new Entry(A.col, (A.value - B.value));
						if(C.value != 0 ){
							P.row[i].append(C);
							P.nnz++;
						}	
						this.row[i].moveNext();
						M.row[i].moveNext();
						continue;
					}
					if( A.col < B.col ){
						Entry C = new Entry(A.col, A.value);
						P.row[i].append(C);
						P.nnz++;
						
						this.row[i].moveNext();
						continue;
					}
					if( B.col < A.col ){
						Entry C = new Entry(B.col, (B.value)*(-1.0));
						P.row[i].append(C);
						P.nnz++;
						
						M.row[i].moveNext();
						continue;
					}
				}
				// At this point, this.row or M.row might have some nnz elements left over, if the lists weren't the same length
				// Append the potentially left over elements to the new matrix in row i
				while( this.row[i].index() >= 0 ){
					Entry C = new Entry(A.col, A.value);
					P.row[i].append(C);
					P.nnz++;
					this.row[i].moveNext();
				}
				while( M.row[i].index() >= 0 ){
					Entry C = new Entry(B.col, (B.value)*(-1.0));
					P.row[i].append(C);
					P.nnz++;
					M.row[i].moveNext();
				}
			}
		}
		return P;
	}
	
	// returns a new Matrix that is the transpose of this Matrix
	Matrix transpose(){
		Matrix P = new Matrix(size);
		
		for( int i = 1; i<= size; i++ ){
			if(this.row[i].length() == 0 ){
				continue;
			}else{
				
				this.row[i].moveFront();
				
				for( int q = 1; q<= this.row[i].length(); q++ ){
					Entry C = (Entry) this.row[i].get();
					C = new Entry(C.col, C.value);
					
					P.changeEntry(C.col, i, C.value);
					
					this.row[i].moveNext();
				}
			}
		}
		
		P.nnz = this.nnz;
		return P;
	}
	
	// returns a new Matrix that is the product of this Matrix with M
	// pre: getSize()==M.getSize()
Matrix mult(Matrix M){
		if ( this.size != M.size ) {
	      throw new RuntimeException("Matrix Error: mult() called on Matrices of different sizes");
		}
		M = M.transpose();
		Matrix P = new Matrix(size);
		
		List thisRow;
		List MRow;
		List PRow;
		double runningSum = 0;
		int j;
		
		for( int i = 1; i<= size; i++ ){
			thisRow = this.row[i];
			PRow = P.row[i];
			runningSum = 0;
			
			if( thisRow.length() == 0 ){
				continue;
			}
			
			for( j = 1; j<=size; j++ ){
				MRow = M.row[j];
				runningSum = 0;
				
				if( (this.row[i].length() == 0) || (M.row[j].length() == 0 ) ){ // if either (or both) of the rows are rows of zeros i.e. lists are empty
					continue;
				}else{
					
					thisRow.moveFront();
					MRow.moveFront();
				
					Entry A = (Entry) thisRow.get();
					Entry B = (Entry) MRow.get();
				
					while( (thisRow.index() >= 0) && (MRow.index() >= 0) ){ // iterate through both lists
						A = (Entry) thisRow.get();
						B = (Entry) MRow.get();
					
						if( A.col == B.col ){
							runningSum += (A.value)*(B.value);
						
							thisRow.moveNext();
							MRow.moveNext();
						}
						if( A.col < B.col ){
						thisRow.moveNext();
						}
						if( B.col < A.col ){
							MRow.moveNext();
						}
					}
				}
				if( runningSum != 0 ){
					Entry C = new Entry(j, runningSum);
					PRow.append(C);
					P.nnz++;
				}
			}
		}
		return P;
	}
	
	/*---------- Other Functions ----------*/
	// overrides Object's equals() method
	public boolean equals(Object x){
		Matrix M = (Matrix) x;
		
		if( (this.nnz != M.nnz ) || (this.size != M.size) ){
			return false;
		}
		
		List thisRow;
		List MRow;
		for( int i = 1; i<=size; i++ ){
			thisRow = this.row[i];
			MRow = M.row[i];
			
			if( !(thisRow.equals(MRow)) ){
				return false;
			}
		}
		return true;
	}
	
	// overrides Object's toString() method
	public String toString(){
		Entry current;
		String toPrint = "";
		
		for( int i = 1; i<=size; i++ ){
			row[i].moveFront();
			
			if( row[i].length() == 0 ){
				continue;
			}
			
			toPrint += i+": ";
			while( row[i].index() >= 0 ){
				current = (Entry) row[i].get();
				toPrint += current.toString()+" ";
				row[i].moveNext();
			}
			toPrint += "\n";
		}
		return toPrint;	
	}
}