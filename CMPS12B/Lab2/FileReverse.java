//----------------------------------------------------------------------------------
// FileReverse.java
// Takes two command line arguments, an input and output file.
// Prints each token from the input file backwards on a line by itself to the output
// Alexandra Vareljian
// avarelji
//-----------------------------------------------------------------------------
import java.io.*;
import java.util.Scanner;
import java.lang.*;

class FileReverse{
	public static void main( String args[] ) throws IOException{
		
		//checks for exactly two command line arguments
		if (args.length != 2 ){
			System.out.println("Usage: java -jar FileReverse.jar <input file> <output file>");
			System.exit(1);
		}
		
		//open files
		Scanner in = new Scanner(new File(args[0]));
		PrintWriter out = new PrintWriter(new FileWriter(args[1]));
		
		//reads lines from in, extract tokens, reverse each token, prints tokens
		while( in.hasNextLine() ){
           // trim leading and trailing spaces, then add one trailing space so 
           // split works on blank lines
           String line = in.nextLine().trim() + " "; 

           // split line around white space 
           String[] token = line.split("\\s+");
		
		   //reverse each individual token
		   for(int i=0; i<=token.length-1; i++){
			  token[i] = stringReverse(token[i]);
		   }
		 
		   // print out the now reversed tokens
           for(int i=0; i<=token.length-1; i++){
              out.println(token[i]);
           }
		}
		//close files
		in.close();
		out.close();
	}
      		
	//reverses string by making a character array,
	//switching leftmost and rightmost indexes,
	//moving towards the middle
	public static String stringReverse(String s){
		int l, r = 0;
		char temp;
		//makes the string into an array of chars
		char[] shoobie = s.toCharArray();
		r = shoobie.length-1;
		
		//reverses the chars
		for(l=0; l<r; l++, r--){
			temp = shoobie[l];
			shoobie[l] = shoobie[r];
			shoobie[r] = temp;
		}
		//makes the array of chars back into a string
		s = String.valueOf(shoobie);
		return s;
	}
}
	