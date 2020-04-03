//-----------------------------------------------------------------------------------------
// Bard.java
// Takes two command line args: an input file of queries and and output file to write to.
// Upon running, parses the text file called shakespeare.txt that should be contained in
// HW5 folder. The input file contains pairs of integers, say x y. Print the word of 
// length x and of frequency y to the output file.
// Alexandra Vareljian
// avarelji
//-----------------------------------------------------------------------------------------

import java.io.*;
import java.util.*;


public class Bard{
	
	public static Hashtable<String, Integer> wordTable;
	public static ArrayList<ArrayList<Word>> solution;
	
	
	
	public static void addToSolution(ArrayList<ArrayList<Word>> solution, String key, int f){
		Word w = new Word(key, f);
		int length = w.word.length();
		while(solution.size() <= length){
			ArrayList<Word> inner = new ArrayList<Word>();
			solution.add(inner);
		}
		solution.get(length).add(new Word(key,f));
	}
	
	//print the solution (list of lists) to standard output
	public static void printSolution(){
		for(int i = 0; i<solution.size(); i++){
			System.out.println("Words of length "+i+" are: ");
			for(int j = 0; j<solution.get(i).size(); j++){
				System.out.println(solution.get(i).get(j).word+" "+solution.get(i).get(j).freq);
			}
		}
	}

	//read through a string array, put each string into the hashtable
	//with (key, val) being (string, frequency)
	//if key already exists, increment the frequency
	public static void hashText(String[] line){
		for(int i = 0; i < line.length; i++){
			if(wordTable.containsKey(line[i])){
				wordTable.put(line[i], wordTable.get(line[i]) + 1);
			}else{
				wordTable.put(line[i], 1);
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		// check for exactly 2 command line arguments
		if (args.length != 2 ){
			System.out.println("Usage: java -jar ChessBoard.jar <input file> <output file>");
			System.exit(1);
		}
		// open files
		Scanner inFile = new Scanner(new File(args[0]));
		PrintWriter outFile = new PrintWriter(new FileWriter(args[1]));
		Scanner text = new Scanner(new File("shakespeare.txt"));
		
		//initialize hashtable
		wordTable = new Hashtable<String, Integer>();
		
		//Parse the shakespeare text and store each word with its frequency in a hash table
		while(text.hasNextLine()){
			//get the next string, call it line
			String line = text.nextLine().trim() + " ";
			line = line.replace('?', ' ');   // replace ? with space
			line = line.replace(',', ' ');   // replace , with space
			line = line.replace('.', ' ');   // replace . with space
			line = line.replace('!', ' ');   // replace ! with space
			line = line.replace(':', ' ');   // replace : with space
			line = line.replace(';', ' ');   // replace ; with space
			line = line.replace('[', ' ');   // replace [ with space
			line = line.replace(']', ' ');   // replace ] with space
			
			// tokenize the line into a string array
			String[] tokens = line.trim().split("\\s+");
			// convert all tokens to lowercase
			for(int i = 0; i < tokens.length; i++){
				tokens[i] = tokens[i].toLowerCase();
			}
			
			//add all words from line into hashtable
			hashText(tokens);
		}
		
		//initialize solution as an arraylist of arraylists
		solution = new ArrayList<ArrayList<Word>>();
		
		//Parse through the hashtable and create a new word object for each key,val pair
		//Insert each word object into the 2D ArrayList
		Enumeration<String> keys = wordTable.keys();
		while(keys.hasMoreElements()){
			String key = keys.nextElement();
			int freq = wordTable.get(key);
			
			//System.out.println(key+" "+freq);
			
			//System.out.println();
			//System.out.println("Inserting the word: "+key);
			addToSolution(solution, key, freq);
			//System.out.println("Solution is now: ");
			//printSolution();
			//System.out.println();	
		}
		
		//printSolution();
		//System.out.println();
		//Iterate through the outer ArrayList, and sort each list
		for(int i = 0; i<solution.size(); i++){
			Collections.sort(solution.get(i));
			Collections.sort(solution.get(i), new Word());
		}
		//System.out.println("Now printing sorted list");
		//printSolution();
		
		//Now parse the input file
		while(inFile.hasNextLine()){
			//trim leading and trailing white space, adding a trailing space so split works on blank lines
			String line = inFile.nextLine().trim() + " ";
			
			//split String line around white space, assign col and row to the query space
			String[] tokens = line.split("\\s+");
			
			int length = Integer.parseInt(tokens[0]);
			int rank = Integer.parseInt(tokens[1]);
			
			if(length == 0){                               //no words of length 0
				outFile.println("-");
			}else if(length >= solution.size()){           //no words of length longer than the solution size
				outFile.println("-");
			}else if(solution.get(length).isEmpty()){      //make sure words of that length exist
				outFile.println("-");
			}else if(rank >= solution.get(length).size()){ //make sure words of that length and rank exist
				outFile.println("-");
			}else{
			outFile.println(solution.get(length).get(rank));
			}	
		}
		//close files
		inFile.close();
		outFile.close();
		text.close();
	}
}
