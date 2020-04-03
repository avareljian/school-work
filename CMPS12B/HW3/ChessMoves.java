//---------------------------------------------------------------------------------------
// ChessMoves.java
// A linked list of Nodes containing references to chess pieces
// Contains the funtion main() which reads an input file, creates a linked list of chess pieces
// from the information given from the input file. To the output file, it prints a given piece (if any)
// and if the piece is attacking any other pieces in the linked list.
//---------------------------------------------------------------------------------------

import java.io.*;
import java.util.Scanner;
public class ChessMoves{
	public static Node head;
	static int col;
	static int row;
	static int destCol;
	static int destRow;
	static int lastColor; //initialized at 0 (black) because first move must be made by white
	
	//constructor
	public ChessMoves(){
		head = null;
	}
	
	//insert a node containing a piece into the linked list
	public static void insert(ChessPiece newPiece){
		Node latest = new Node(newPiece);
		latest.next = head;
		head = latest;
	}
	
	//removes the node containing the ChessPiece to be removed
	public static void remove(ChessPiece toRemove){
		Node prev = null;
		Node curr = head;

		while(curr != null && ((curr.item.isEqual(toRemove) == false))){
			prev = curr;
			curr = curr.next;
		}
		if(prev == null){      //special case that the piece to be removed is head
			head = head.next;
		}else{
			prev.next = curr.next;
		}
		return;
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
				return current.item;
			}
			current = current.next;
		}
		return null;
	}
	
	//same as find() but returns boolean
	public static boolean doesContainPiece(int c, int r){
		Node current = head;
		while(current != null){
			if((current.item.col == c) && (current.item.row == r)){
				//System.out.print("Found: ");
				//System.out.println(current.item.color+" "+current.item.col+" "+current.item.row+" "+current.item.type);
				return true;
			}
			current = current.next;
		}
		return false;
	}
	
	// Is the path for a rook at (c,r) clear to go to (destc, destr)
	// DOES NOT INCLUDE THE SPACE(destc,destr) only the spaces inbetween
	// if the destination space is 1 move away (right next to c,r), PATH IS CLEAR
	public static boolean isRookClear(int c, int r, int destc, int destr){
		if((destc == c+1) || (destc == c+1) || (destr == r+1) || (destr == r-1)){ //case5, destination is right next to the piece
			//System.out.println("case5, path is clear");
			return true;                                                          //thus, path is clear
		}
		if(c > destc && r == destr){ //case1, destination is same row, to the left
			//System.out.println("case1");
			for(int i = c-1; i> destc; i--){
				if(doesContainPiece(i, destr)){
					//System.out.println("Rook path is blocked");
					return false;
				}
			}
			//System.out.println("Rook path is clear");
			return true;
		}
		if(c < destc && r == destr){ //case2, destination is same row, to the right
			//System.out.println("case2");
			for(int i = c+1; i < destc; i++){
				if(doesContainPiece(i, destr)){
					//System.out.println("Rook path is blocked");
					return false;
				}
			}
			//System.out.println("Rook path is clear");
			return true;
		}
		if(r > destr && c == destc){ //case3, destination is same col, down
			//System.out.println("case3");
			for(int i = r-1; i > destr; i--){
				if(doesContainPiece(destc, i)){
					//System.out.println("Rook path is blocked");
					return false;
				}
			}
			//System.out.println("Rook path is clear");
			return true;
		}
		if(r < destr && c == destc){ //case4, destination is same col, up
			//System.out.println("case4");
			for(int i = r+1; i < destr; i++){
				if(doesContainPiece(destc, i)){
					//System.out.println("Rook path is blocked");
					return false;
				}
			}
			//System.out.println("Rook path is clear");
			return true;
		}
		return false;
	}
	
	// Is the path from a bishop at (c,r) clear to go to destination (destc, destr)
	// DOES NOT INCLUDE THE SPACE (destc, destr)
	// if destination is 1 move away (right next to c,r) PATH IS CLEAR
	public static boolean isBishopClear(int c, int r, int destc, int destr){
		if(((destc == c-1) && (destr == r-1)) || ((destc == c-1) && (destr == r+1)) || 
		((destc == c+1) && (destr == r+1)) || ((destc == c+1) && (destr == r-1))){      //case5, destination is one move away, hence path is clear
			//System.out.println("case5, path is clear");
			return true;
		}
		if((c > destc) && (r > destr)){ //case1, destination is to the left, down
			for(int i = c-1, j = r-1; i >destc && j> destr; i--, j--){
				if(doesContainPiece(i, j)){
					//System.out.println("case1, path is not clear");
					return false;
				}
			}
			//System.out.println("case1, path is clear");
			return true;
		}
		if((c > destc) && (r < destr)){ //case2, destination is to the left, up
			for(int i = c-1, j = r+1; i >destc && j< destr; i--, j++){
				if((doesContainPiece(i, j))){
					//System.out.println("case2, path is not clear");
					return false;
				}
			}
			//System.out.println("case2, path is clear");
			return true;
		}
		if((c < destc) && (r < destr)){ //case3, destination is to the right, up
			for(int i = c+1, j = r+1; i< destc && j< destr; i++, j++){
				if(doesContainPiece(i, j)){
					//System.out.println("case3, path is not clear");
					return false;
				}
			}
			//System.out.println("case3, path is clear");
			return true;
		}
		if((c < destc) && (r > destr)){ //case4, destination is to the right, down
			for(int i = c+1, j = r-1; i< destc && j> destr; i++, j--){
				if(doesContainPiece(i, j)){
					//System.out.println("case4, path is not clear");
					return false;
				}
			}
			//System.out.println("case4, path is clear");
			return true;
		}
		return false;
	}
	
	// Is the path for a Queen at (c,r) clear to go to destination (destc, destr)
	// DOES NOT INCLUDE THE SPACE (destc, destr)
	// If destination is 1 move away (right next to c,r) PATH IS CLEAR
	public static boolean isQueenClear(int c, int r, int destc, int destr){
		if(isRookClear(c, r, destc, destr)){
			return true;
		}else if(isBishopClear(c, r, destc, destr)){
			return true;
		}else{
			return false;
		}
	}
	
	// First checks if destination is in the range of movement for piece c
	// If yes then checks if path to the destination is clear, depending on type of chesspiece c-1
	// RETURNS TRUE IF PATH IS CLEAR
	public static boolean checkPath(ChessPiece c, int destc, int destr){
		if(c.canMoveTo(destc, destr)){
			if(c.type == 'q' || c.type == 'Q'){
				if(isQueenClear(c.col, c.row, destc, destr)){
					return true;
				}
			}else if(c.type == 'r' || c.type == 'R'){
				if(isRookClear(c.col, c.row, destc, destr)){
					return true;
				}
			}else if(c.type == 'b' || c.type == 'B'){
				if(isBishopClear(c.col, c.row, destc, destr)){
					return true;
				}
			}else if(c.type == 'k' || c.type == 'K'){
				if(c.canMoveTo(destc, destr)){
					return true;
				}
			}else if(c.type == 'n' || c.type == 'N'){
				if(c.canMoveTo(destc, destr)){
					return true;
				}
			}
		}
		return false;
	}
	
	//traverse and print the list
	public static void traverse(){
		Node current = head;
		while(current != null){
			System.out.println(current.item.color+" "+current.item.col+" "+current.item.row+" "+current.item.type);
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
	
	// sets new coords for chesspiece c
	// after it moves the piece, changes lastColor to the color of the piece just moved
	public static void move(ChessPiece c, int destc, int destr){
		Node current = head;
		while(current != null){
			if(current.item.isEqual(c)){
				current.item.col = destc;
				current.item.row = destr;
			}
			current = current.next;
		}
		if(c.color == 0){
			lastColor = 0;
		}else if(c.color ==1){
			lastColor = 1;
		}
	}
	
	//returns true if the color of chesspiece c is different from lastColor
	//returns false if the color of chesspiece c is same as lastColor 
	public static boolean isOppColor(ChessPiece c){
		if(c.color != lastColor){
			return true;
		}else{
			return false;
		}
	}
	
	//Is the king of the inputted color in check
	//returns true if is in check
	public static boolean isInCheck(int color){
		Node current = head;
		//is a black king in check?
		try{
			if(color == 0){
			//traverse through list and find a black king
			ChessPiece blackKing = null;
			while(current != null){
				if(current.item.type == 'K'){
					blackKing = current.item;
					//System.out.println("Is black king in check? "+ blackKing.type+" "+blackKing.col+" "+blackKing.row);
				}
				current = current.next;
			}
			//traverse through list and see if any white pieces have a clear path to the black king
			current = head;
			while(current != null){
				if(current.item.color == 1){
					if(checkPath(current.item, blackKing.col, blackKing.row)){
						//System.out.println("Black king is in check by: "+current.item.type+" "+current.item.col+" "+current.item.row);
						return true;
					}
				}
				current = current.next;
			}
			//Black King is NOT in check
			//System.out.println("Black King not in check");
			return false;
			}
		//is a white king in check?
			else if(color == 1){
			//traverse through list and find a white king
			ChessPiece whiteKing = null;
			while(current != null){
				if(current.item.type == 'k'){
					whiteKing = current.item;
					//System.out.println("Is white king in check? "+whiteKing.type+" "+whiteKing.col+" "+whiteKing.row);
				}
				current = current.next;
			}
			//traverse through list and see if any black pieces have a clear path to the white king
			current = head;
			while(current != null){
				if(current.item.color == 0){
					if(checkPath(current.item, whiteKing.col, whiteKing.row)){
						//System.out.println("White king is in check by: "+current.item.type+" "+current.item.col+" "+current.item.row);
						return true;
					}
				}
				current = current.next;
			}
			//white king not in check
			//System.out.println("White king NOT in check");
			return false;
			}
		}catch(NullPointerException e1){
			//no kings were found
			//System.out.println("isInCheck is false: caught exception b/c no kings found");
			return false;
		}
		//System.out.println("did not try nor catch");
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
			
			//split String line around ":" to get the two parts of the input
			String[] tokens = line.split(":");
			
			//the two parts of the input as Strings
			//add a space and string to the end so splitting between spaces works
			String boardTemp = tokens[0].trim() + " cut";
			String instructionsTemp = tokens[1].trim() + " cut";
			
			//first part as a string array
			String[] board = boardTemp.split(" ");
			//second part as a string array
			String[] instructionsString = instructionsTemp.split(" ");
			//rewriting second part as an int array
			int[] instructions;
			instructions = new int[instructionsString.length];
			for(int i = 0; i<instructionsString.length-1; i++){
				instructions[i] = Integer.valueOf(instructionsString[i]);
			}
			
			
			// AT THIS POINT
			// board is a string array containing pieces TO BE PLACED
			// instructions is an int array
			
			ChessMoves z = new ChessMoves();
			for(int i = 0; i<board.length-1; i+=3){
				if(board[i].equals("Q")){
					Queen a = new Queen(0, Integer.parseInt(board[i+1]), Integer.parseInt(board[i+2]), 'Q');
					z.insert(a);
				}else if(board[i].equals("q")){
					Queen b = new Queen(1, Integer.parseInt(board[i+1]), Integer.parseInt(board[i+2]), 'q');
					z.insert(b);
				}else if(board[i].equals("K")){
					King c = new King(0, Integer.parseInt(board[i+1]), Integer.parseInt(board[i+2]), 'K');
					z.insert(c);
				}else if(board[i].equals("k")){
					King d = new King(1, Integer.parseInt(board[i+1]), Integer.parseInt(board[i+2]), 'k');
					z.insert(d);
				}else if(board[i].equals("R")){
					Rook e = new Rook(0, Integer.parseInt(board[i+1]), Integer.parseInt(board[i+2]), 'R');
					z.insert(e);
				}else if(board[i].equals("r")){
					Rook f = new Rook(1, Integer.parseInt(board[i+1]), Integer.parseInt(board[i+2]), 'r');
					z.insert(f);
				}else if(board[i].equals("B")){
					Bishop g = new Bishop(0, Integer.parseInt(board[i+1]), Integer.parseInt(board[i+2]), 'B');
					z.insert(g);
				}else if(board[i].equals("b")){
					Bishop h = new Bishop(1, Integer.parseInt(board[i+1]), Integer.parseInt(board[i+2]), 'b');
					z.insert(h);
				}else if(board[i].equals("N")){
					Knight j = new Knight(0, Integer.parseInt(board[i+1]), Integer.parseInt(board[i+2]), 'N');
					z.insert(j);
				}else if(board[i].equals("n")){
					Knight k = new Knight(1, Integer.parseInt(board[i+1]), Integer.parseInt(board[i+2]), 'n');
					z.insert(k);
				}
			}
			
			//System.out.println("Reading this line:");
			//System.out.println(boardTemp);
			//System.out.println(instructionsTemp);
			lastColor = 0;
			//find piece TO BE MOVED
			int y;
			for(y=0; y<instructions.length-1; y+=4){
				//System.out.println("y = "+y);
				if((find(instructions[y], instructions[y+1])) == null){
					//System.out.println("In if");
					outFile.println(instructions[y]+" "+instructions[y+1]+" "+instructions[y+2]+" "+instructions[y+3]+" illegal");
					break;
				}else{
					//System.out.println("In else");
					ChessPiece found;
					found = (find(instructions[y], instructions[y+1]));
					//System.out.println("Found: "+found.type+" "+found.col+" "+found.row);
					
					//is color different from lastColor? (alternating turns)
					if(isOppColor(found)){
						//System.out.println("Passes alternating turns test");
						try{ //try finding a piece at the query space
							//System.out.println("In try, trying to find a piece at the query square: "+instructions[y+2]+" "+instructions[y+3]);
							ChessPiece atQuery = null;
							atQuery = find(instructions[y+2], instructions[y+3]);
							
							//is atQuery different color than found?
							if(found.color != atQuery.color){
								//outFile.println("Found piece is different color than atQuery piece");
								//is atQuery within the range of motion of found? if yes, is the path clear?
								if(checkPath(found, atQuery.col, atQuery.row)){
									move(found, atQuery.col, atQuery.row); //moves the piece (updates the list) and updates lastColor
									remove(atQuery);
									//after making the move, is found's king in check?
									if(isInCheck(lastColor)){
										outFile.println(instructions[y]+" "+instructions[y+1]+" "+instructions[y+2]+" "+instructions[y+3]+" illegal");
										break; //King of color just moved was in check
									}
								}else{
									outFile.println(instructions[y]+" "+instructions[y+1]+" "+instructions[y+2]+" "+instructions[y+3]+" illegal");
									break; //Path was not clear, or destination was outside of range of movement
								}
							}else{
								outFile.println(instructions[y]+" "+instructions[y+1]+" "+instructions[y+2]+" "+instructions[y+3]+" illegal");
								break; //Destination contains a peice of the same color
							}
						}catch(NullPointerException e1){ //no piece was at the query space
							//outFile.println("In catch");
							//is path to the query space clear?
							if(checkPath(found, instructions[y+2], instructions[y+3])){
								//outFile.println("Path to query space is clear");
								move(found, instructions[y+2], instructions[y+3]); //moves the piece (updates list) and updates lastColor
								//outFile.println("found is at: " +found.col+" "+found.row);
								//outFile.println("lastColor is: "+lastColor);
								//after making the move, is found's king in check?
								if(isInCheck(lastColor)){
									outFile.println(instructions[y]+" "+instructions[y+1]+" "+instructions[y+2]+" "+instructions[y+3]+" illegal");
									break; // King of color just moved was in check
								}
							}else{
								outFile.println(instructions[y]+" "+instructions[y+1]+" "+instructions[y+2]+" "+instructions[y+3]+" illegal");
								break; //Path to query square was not clear, or not in the range of movement
							}
						}
					}else{
						outFile.println(instructions[y]+" "+instructions[y+1]+" "+instructions[y+2]+" "+instructions[y+3]+" illegal");
						break; //Moves were not alternating colors
					}
				}
				//IF YOU MADE IT HERE THE MOVE FROM instructions[y] to instructions[y+4] IS LEGAL
				if((y+4) == (instructions.length-1)){ //IF YOU WENT THROUGH THE WHOLE ARRAY THE SET OF MOVES IS LEGAL
					outFile.println("legal");
				}
			}
		}	
		//close files
		inFile.close();
		outFile.close();
	}
}