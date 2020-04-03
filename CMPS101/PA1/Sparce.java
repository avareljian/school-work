//-----------------------------------------------------------------------------------------------------------
// Sparse.java
// Alex Vareljian
// avarelji
// A client of the sparse Matrix ADT. Takes in two commant line arguments given the input and output files.
// Calculates and prints the following operations on matrices A and B:
// print A, print B, (1.5)A, A+B, A+A, B-A, A-A, A^T, AB, B^2
//-----------------------------------------------------------------------------------------------------------
import java.util.Scanner;
import java.io.*;

public class Sparce{
	
	public static void main(String[] args) throws IOException{
		
		//check for exactly 2 command line arguments
		if ( args.length != 2 ){
			System.out.println("Usage: java -jar Sparce.jar <input file> <output file>");
			System.exit(1);
		}
		
		//open files
		Scanner inFile = new Scanner(new File(args[0]));
		PrintWriter outFile = new PrintWriter(new FileWriter(args[1]));
		
		// variables for all the matrices
		Matrix A, B, Ascalar, AplusB, AplusA, BsubA, AsubA, Atranspose, AmultB, BmultB;
		
		// variables for reading in file
		int n, a, b = 0;
		int lineNumber = 0;
		
		/*---------- read in file ----------*/
		while(inFile.hasNextLine()){
			lineNumber++;
			//trim leading and trailing white space, adding a trailing space so split works on blank lines
			String line = inFile.nextLine().trim() + " ";
			// tokenize the line by splitting around white space
			String[] tokens = line.split("\\s");
			
			// the first line of the file contains information to initialize the matrices
			if( lineNumber == 1 ){
				n = Integer.parseInt(tokens[0]);
				a = Integer.parseInt(tokens[1]);
				b = Integer.parseInt(tokens[2]);
				
				A = new Matrix(n);
				B = new Matrix(n);
				//A.nnz = a;
				//B.nnz = b;
			}
			
			// Populate Matrix A
			if( (lineNumber > 2) && (lineNumber<= 2+a) ){ // lines corresponding to matrix A
				int i = Integer.parseInt(tokens[0]);
				int j = Integer.parseInt(tokens[1]);
				double x = Double.parseDouble(tokens[2]);
				
				A.changeEntry(i, j, x);
			}
			
			// Populate Matrix B
			if(lineNumber > 3+a){ // lines corresponding to matrix B
				int i = Integer.parseInt(tokens[0]);
				int j = Integer.parseInt(tokens[1]);
				double x = Double.parseDouble(tokens[2]);
				
				B.changeEntry(i, j, x);
			}
		}
		
		// Double check the in file has been parsed correctly/the matrices were populated correctly
		if( (A.nnz != a) || (B.nnz != b) ){
			throw new RuntimeException("Sparce.jave Error: Matrices were not initialized with correct number of non-zero values");
		}
		
		/*---------- perform the operations ----------*/
		//Ascalar = A.scalar(1.5);
		AplusB = A.add(B);
		AplusA = A.add(A);
		BsubA = B.sub(A);
		AsubA = A.sub(A);
		Atranspose = A.transpose();
		AmultB = A.mult(B);
		BmultB = B.mult(B);
		/*---------- Perform operations and write to the out file ----------*/
		
		outFile.println("A has"+A.nzz+" non-zero entries:");
		outFile.println(A);
		
		outFile.println("B has"+B.nzz+" non-zero entries:");
		outFile.println(B);
		
		outFile.println("(1.5)*A =");
		//Ascalar = A.scalar(1.5);
		outFile.println(A.scalar(1.5););
		
		outFile.println("A+B =");
		outFile.println(AplusB);
		
		outFile.println("A+A =");
		outFile.println(AplusA);
		
		outFile.println("B-A =");
		outFile.println(BsubA);
		
		outFile.println("A-A =");
		
		outFile.println("Transpose(A) =");
		outFile.println(Atranspose);
		
		outFile.println("A*B =");
		outFile.println(AmultB);
		
		outFile.println("B*B =");
		outFile.println(BmultB);
		
		// close files
		//close files
		inFile.close();
		outFile.close();
	}
}	
	