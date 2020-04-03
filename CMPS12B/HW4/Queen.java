// Filename: Queen.java
// 
// Contains the class Queen that represents a queen chesspiece
// 
// C.Seshadhri, Dec 2016

class Queen {
    public int row;  // these store the position of the queen, as row and col (column)
    public int col;

    // Default constructor sets row and col to infeasible (negative) values
    public Queen()
    {
        col = -1;
        row = -1;
    }

    // Constructor creates Queen with col c and row r 
    public Queen(int c, int r) 
    {
        col = c;
        row = r;
    }

    // Boolean function that determines if self (which is a queen) is attacking another queen q, given as argument
    // Input: Queen q
    // Output: True if self is attacking q, false otherwise
    public boolean isAttacking(Queen q)  
    {
        if (row ==q.row || col == q.col) // if self has same row or column as q, self is attacking q
            return true;
        else if (Math.abs(row-q.row) == Math.abs(col - q.col)) // if self is on same diagonal as q, this is attack. we use absolute values to determine diagonal
            return true;
        else
            return false; // self is not attacking q
    }
}
