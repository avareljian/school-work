//---------------------------------------------------------------------------------------
// NQueens.java
// Solves the NQueens problem using a stack and backtracking instead of recurison
// Given an input file containing the size of the board (n), and the placement of the first queen(s),
// prints a valid solution to an output file in increasing order by column, or "No solution"
// Alexandra Vareljian
// avarelji
//---------------------------------------------------------------------------------------

import java.io.*;
import java.util.*;
import java.util.Scanner;
class NQueens{
    private static int board_size;  // board size
    private static Queen[] solution; // global variable containing the solution
	// !!! of length board_size+1 !!! solution[0] remains uninitialized
	
	// A convenient method to convert a string into an int, throwing an error message if not possible
    // Input: String num, that is supposed to be an int
    // Output: If num represents an int, return the int. Otherwise, throw error message
    private static int getInt(String num){
        int parsed_num = 0;
        try{
            parsed_num = Integer.parseInt(num); // try to parse num as an int
        }
        catch (NumberFormatException e){ // failure, so exception is caught
            System.out.print("Instance can only have integers"); // throw error
        }
        return parsed_num; // return int value of num
    }
	
	// Parse the problem into appropriate board size and queen positions.
    // Input: String array (will come from input file)
    // Output: Update board_size and solution appropriately. Return the number of queens placed in the arguments
    private static int parseProblem(String instance){
        String[] problem = instance.split(" "); //parse instance string by whitespace

        board_size = getInt(problem[0]); // set board_size
        if (board_size <= 0) // don't give bad board sizes
            System.out.print("Board size must be positive");

        if (problem.length%2 == 0) // even number of arguments, so there is problem. throw error
            System.out.print("Instance must have an odd number of integers. Check out following line input: "+problem);
        
        solution = new Queen[board_size+1]; // allocate references for solution

        for (int i=1; i < problem.length/2 + 1; i++){ // loop over remaining arguments in pairs
            solution[i] = new Queen(getInt(problem[2*i-1]),getInt(problem[2*i])); // create a new queen with every successive pair of ints    
        }
        return problem.length/2;
    }
	
	// Just checking if a partial setting of queens is valid.
    // Input: num_placed queens in solution
    // Output: true iff none attack each other
    private static boolean isValid(int num_placed){
        for (int i=1; i<= num_placed; i++) // double loop over queens that have been placed
            for (int j=1; j<i; j++)  
                if (solution[i].isAttacking(solution[j])) // if one queen attacks another, setting is not valid
                    return false;
        return true; // queens don't attack each other, so setting is valid
    }
	
	// Given the amount of queens left to place, tries to find a solution via maintaining a stack
	// Outputs boolean that is true iff there is a possible placement of queens. In addition, solution[] contains all queens of the solution
	// Note: prior to calling this funtion, solution[] should include the queens specified by the input file
	private static boolean PlaceQueens(int num_left){
		//System.out.println("In PlaceQueens("+num_left+")");
		Stack<Queen> s = new Stack<Queen>();
		Queen top;
        int num_placed = board_size - num_left;  //# of queens already placed


        while(num_placed < board_size+1){
			//System.out.println("In while("+num_placed+"<"+(board_size+1)+")");
			if(num_placed == board_size){          // base case, all queens were successfully placed
				break;
			}
			int free = -1;                          //tracking a "free" column, needs to be reset at the beginning of each iteration
			
			//Find next available column
			//Set free = to it
			boolean available = false; // tracking if some column is available
			for(int i=1; i < board_size+1; i++){ // iterate over all columns in chessboard
				available = true; // assume column i is available (no queen is in column i)
				for(int j=1; j <= num_placed; j++) // iterate over currently placed queens
					if(solution[j].col == i){ // if some queen is in column i, then i is not available
						available = false;
						break;
					}
				if(available){ // if "available" is true, then no queen in column i. so set "free" to i
					free = i;
					break;
				}
			}	
			if(free == -1){ // for error tracking. free should never be -1
				System.out.print("Error in PlaceQueens: free is -1"+": "+num_placed);
			}
			//System.out.println("Found next free column: "+free);
			
			//Initialize booleans that will be used in this section
			//Ones ending in -BT are used in the backtracking section
			boolean isPossible = true;
			boolean placed = false;
			boolean isPossibleBT = true;
			boolean placedBT = false;
			
			// We know that no queen is in column "free", so we'll try to place the next queen in this column.
			// We need to find some row to place the queen.
			for(int row=1; row < board_size+1; row++){  // iterate over all rows
				//System.out.println("Trying to find a free row in column: "+free);
				Queen next = new Queen(free, row); // create a queen at (free, row)
				isPossible = true;
				placed = false;

				// check if the queen at (free, row) isPossible
				// if not, set isPossible = false
				for(int i=1; i <= num_placed; i++){ // iterate over existing queens
					//System.out.println("Checking if a queen at ("+free+", "+row+") is possible");
					if(solution[i].isAttacking(next)){ // check if existing queen attacks next queen
						isPossible = false; // if some queen attacks next queen, then this position is not possible
						break;
					}
				}	
				if(isPossible){  //we can place the next queen at (free, row)
					//System.out.println("Queen at ("+free+", "+row+") is possible, now going to place it");
					//System.out.println();
					s.push(next);
					solution[num_placed+1] = next; // add next queen to solution
					num_placed++;
					placed = true;
					break; //and find the next free column (go back to beginning of while loop)
				}
				continue;
			}
			if(!placed){ // if at this point, no queen was placed in the "free" column, meaning we must backtrack by changing the position of the previous queen(s)
				//System.out.println("No queen was placed in column: "+free+", now must backtrack!!");
				while(!s.empty()){
					//System.out.println("In while(stack s !empty)");
					top = s.pop();     // top is the last queen that was placed
					num_placed--;
					
					//System.out.println("Popped the stack. Queen top was at ("+top.col+", "+top.row+")");

					for(int row=top.row+1; row < board_size+1; row++){  //try to find a new row for top
						top.row = row;                                  //changing top.row along the way
						isPossibleBT = true;                            //assume placement of top is possible
						placedBT = false;                               //needs to be reset at each beginning of the loop
						
						//System.out.println("Checking new position of top");
						for(int i=1; i<= num_placed; i++){              //iterate over existing queens
							//System.out.println("Checking top at ("+top.col+", "+top.row+")");
							if(solution[i].isAttacking(top)){            //check if existing queen attacks this new position of top
								isPossibleBT = false;                     //if yes, this position is not possible
								break;                                  //try a different row position
							}
						}
						if(isPossibleBT){        // if possible
							//System.out.println("Top at ("+top.col+", "+top.row+") is possible, now going to place it");
							//System.out.println();
							s.push(top);       // push the changed top back onto the stack
							solution[num_placed+1] = top;  //change it in the solution array
							num_placed++;
							placedBT = true;
							break;             //break out of iterating through rows loop
						}
						continue;       //continue iterating through rows
					}
					if(placedBT){         //if you are able to place top, break out of this backtracking while loop
						break;
					}else if(!placedBT && s.empty()){  //if no piece was placed AND the stack is empty, return false
						return false;
					}
					//At this point, a new position for "top" was not able to be found, which means the stack needs to be popped again
					//Continue with this loop
				}
				//At this point you may have backtracked and changed the position of some previous queen
				//Continue with the main while loop
				continue;
			}
		}
		//If you are here then you've found a solution!
		return true;
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
			//get the next string, call it line
			String line = inFile.nextLine();
			
			//set up board_size and solution[], and get the num_placed
			int num_placed = parseProblem(line);
			int num_left = board_size - num_placed; // number of queens left to place
			
			
			if(!isValid(num_placed)){ // check for validity of input placed
                outFile.println("No solution"); // If given queens are attacking eachother, there will never be a solution
                continue; // move on to next problem
            }
			
			if(PlaceQueens(num_left)){ //if there is a solution
				for(int col=1; col<solution.length; col++){ // print solution, looping over columns
                    int queen_row = 0; //variable for row of queen in col
                    for(int index=1; index<solution.length; index++){ // loop over queens to find out which one is the right column
                        if(solution[index].col == col){ // found the queen in column col
                            queen_row = solution[index].row;  // set the row
						}
                    }
                    //System.out.print(col + " " + queen_row + " "); // print out position of queen
                    outFile.print(col + " " + queen_row + " ");
                }
                //System.out.print("\n"); // print out new line
                outFile.print("\n");
			}else{
				//System.out.println("No solution");
				outFile.println("No solution");
			}
		}
		//close files
		inFile.close();
		outFile.close();
	}
}