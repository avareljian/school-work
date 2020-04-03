//---------------------------------------------------------------------------------------
// Lex.java
// Alex Vareljian
// avarelji
// Uses the list ADT to "sort" an array.
//---------------------------------------------------------------------------------------

import java.util.Scanner;
import java.io.*;

public class Lex{
	static String[] lines;
	static List zist = new List();
	
	public static void main(String[] args) throws IOException{
		
		//check for exactly 2 command line arguments
		if ( args.length != 2 ){
			System.out.println("Usage: java -jar Lex.jar <input file> <output file>");
			System.exit(1);
		}
		
		//open files
		Scanner inFile = new Scanner(new File(args[0]));
		PrintWriter outFile = new PrintWriter(new FileWriter(args[1]));
		int numberOfLines = 0;
		
		// figure out how many lines the file has
		while( inFile.hasNextLine() ){
			numberOfLines++;
			inFile.nextLine();
		}
		inFile.close();
		
		// Open the file again and fill an array with the lines of the file
		Scanner in = new Scanner(new File(args[0]));
		lines = new String[numberOfLines];
		for( int i = 0; i< lines.length; i++ ){
			lines[i] = in.nextLine();
		}
		in.close();

		
		// insert first index
		zist.append(0);
		
		// iterate through the lines of the file
		for( int i = 1; i < lines.length; i++ ){
			
			String mover = lines[i];
			String first = lines[zist.front()];
			String last = lines[zist.back()];
	
			// if the line is "smaller" than the first element, then just prepend its index to the list
			if( mover.compareTo(first) < 0 ){
				zist.prepend(i);
				continue;
			}
				
			// if line is "greater" than the last line, then just append its index to the list
			else if( mover.compareTo(last) > 0 ){
				zist.append(i);
				continue;
			}
			
			// otherwise, iterate through the list and find where to put it in the list
			for(zist.moveFront(); zist.index()>=0; zist.moveNext()){
				if( mover.compareTo(lines[zist.get()]) <= 0 ){
					zist.insertBefore(i);
					break;
				}
			}
		}

		// write the array in sorted order to the output file
		for(zist.moveFront(); zist.index()>=0; zist.moveNext()){
			outFile.println(lines[zist.get()]);
		}

		// close the files
		outFile.close();			
	}
}