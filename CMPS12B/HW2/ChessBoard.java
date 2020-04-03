//---------------------------------------------------------------------------------------
// ChessBoard.java
// avarelji 1539102
// A linked list of Nodes containing references to chess pieces
// Contains the funtion main() which reads an input file, creates a linked list of chess pieces
// from the information given from the input file. To the output file, it prints a given piece (if any)
// and if the piece is attacking any other pieces in the linked list.
//---------------------------------------------------------------------------------------

import java.io.*;
import java.util.Scanner;
public class ChessBoard{
	public static Node head;
	static int col;
	static int row;
	
	//constructor
	public ChessBoard(){
		head = null;
	}
	
	//insert a node containing a piece into the linked list
	public static void insert(ChessPiece newPiece){
		Node latest = new Node(newPiece);
		latest.next = head;
		head = latest;
	}
	
	//returns true if the inputted chess piece is not placed on an already occupied square
	public static boolean isValid(){
		Node current = head;
		while(current != null){
			Node mover = current.next;
			while (mover != null){
				if((mover.item.col == current.item.col) && (mover.item.row == current.item.row)){
					//System.out.println("Invalid");
					return false;
				}
				mover = mover.next;
			}
			current = current.next;
		}
		//System.out.println("Valid");
		return true; //no two pieces are in the same position
	}
	
	//then looks for a Node containing a piece at c,r. If not found, returns prints Invalid and returns null
	public static ChessPiece find(int c, int r){
		Node current = head;
		while(current != null){
			if((current.item.col == c) && (current.item.row == r)){
				//System.out.print("Found: ");
				//System.out.println(current.item.color+" "+current.item.col+" "+current.item.row+" "+current.item.type);
				return current.item;
			}
			current = current.next;
		}
		return null;
	}
	
	//traverse and print the list
	public static void traverse(){
		Node current = head;
		while(current != null){
			//System.out.println(current.item.color+" "+current.item.col+" "+current.item.row);
			current = current.next;
		}
	}	
	
	//traverses through the linked list of chesspieces and determines if the inputted chesspiece attacks any of the
	//pieces already in the list
	public static boolean travIsAttacking(ChessPiece c){
		Node current = head;
		while(current != null){
			if(c.isAttacking(current.item)){
				return true;
			}
			current = current.next;
		}
		return false;
	}
	
	public static void main(String[] args) throws IOException{
		//check for exactly 2 command line arguments
		if (args.length != 2 ){
			System.out.println("Usage: java -jar ChessBoard.jar <input file> <output file>");
			System.exit(1);
		}
		//open files
		Scanner inFile = new Scanner(new File(args[0]));
		PrintWriter outFile = new PrintWriter(new FileWriter(args[1]));
		
		while(inFile.hasNextLine()){
			//trim leading and trailing white space, adding a trailing space so split works on blank lines
			String line = inFile.nextLine().trim() + " ";
			
			//split String line around white space, assign col and row to the query space
			String[] tokens = line.split("\\s+");
			
			col = Integer.parseInt(tokens[0]);
			row = Integer.parseInt(tokens[1].substring(0,1));
			
			ChessBoard z = new ChessBoard();
			for(int i = 2; i<tokens.length-1; i+=3){
				if(tokens[i].equals("Q")){
					Queen a = new Queen(0, Integer.parseInt(tokens[i+1]), Integer.parseInt(tokens[i+2]), 'Q');
					z.insert(a);
				}else if(tokens[i].equals("q")){
					Queen b = new Queen(1, Integer.parseInt(tokens[i+1]), Integer.parseInt(tokens[i+2]), 'q');
					z.insert(b);
				}else if(tokens[i].equals("K")){
					King c = new King(0, Integer.parseInt(tokens[i+1]), Integer.parseInt(tokens[i+2]), 'K');
					z.insert(c);
				}else if(tokens[i].equals("k")){
					King d = new King(1, Integer.parseInt(tokens[i+1]), Integer.parseInt(tokens[i+2]), 'k');
					z.insert(d);
				}else if(tokens[i].equals("R")){
					Rook e = new Rook(0, Integer.parseInt(tokens[i+1]), Integer.parseInt(tokens[i+2]), 'R');
					z.insert(e);
				}else if(tokens[i].equals("r")){
					Rook f = new Rook(1, Integer.parseInt(tokens[i+1]), Integer.parseInt(tokens[i+2]), 'r');
					z.insert(f);
				}else if(tokens[i].equals("B")){
					Bishop g = new Bishop(0, Integer.parseInt(tokens[i+1]), Integer.parseInt(tokens[i+2]), 'B');
					z.insert(g);
				}else if(tokens[i].equals("b")){
					Bishop h = new Bishop(1, Integer.parseInt(tokens[i+1]), Integer.parseInt(tokens[i+2]), 'b');
					z.insert(h);
				}else if(tokens[i].equals("N")){
					Knight j = new Knight(0, Integer.parseInt(tokens[i+1]), Integer.parseInt(tokens[i+2]), 'N');
					z.insert(j);
				}else if(tokens[i].equals("n")){
					Knight k = new Knight(1, Integer.parseInt(tokens[i+1]), Integer.parseInt(tokens[i+2]), 'n');
					z.insert(k);
				}
			}
			
			ChessPiece found;
			found = find(col, row);
			//traverse();
			//System.out.println();
			if(isValid()){
				try{
					if((travIsAttacking(found))){
						outFile.print(found.type+" ");
						outFile.println("y");
					}else{
						outFile.print(found.type+" ");
						outFile.println("n");
					}
				//If catches a null pointer exception, that means that the query piece was not found!!!
				}catch(NullPointerException e1){
					outFile.println("-");
					continue;
				}
			}else{
				outFile.println("Invalid");
			}
		}
		//close files
		inFile.close();
		outFile.close();
	}
}