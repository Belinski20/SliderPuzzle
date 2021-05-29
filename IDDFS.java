import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class IDDFS {

    private Stack<SliderPuzzle> graph;
    private Set<SliderPuzzle> visited;
    private SliderPuzzle puzzle;
    private SliderPuzzle current;
    private int[][] goal;

    public IDDFS()
    {
        graph = new Stack<>();
        visited = new HashSet<>();
    }

    /**
     * while the goal is not found
     *         add the original puzzle to the graph
     *         found = dls(depth)
     *         increase depth
     *         clear visited set
     *      if found
     *         print failed message
     *      else
     *         print depth and time taken
     * @param puzzle
     * @param goal
     */
    public void dfid(SliderPuzzle puzzle, int[][] goal)
    {
        this.puzzle = puzzle;
        this.goal = goal;

        boolean found = false;
        int depth = 0;
        long startTime = System.nanoTime();
        while(!found)
        {
            graph.add(puzzle);
            found = dls(depth);
            depth++;
            visited.clear();
        }
        if(!found)
            System.out.println("Could not find path to goal from starting state");
        long endTime = System.nanoTime();
        System.out.println("[IDDFS] Took " + (endTime - startTime) + " nanosecs to complete and a depth of " + current.getDepth() + "\n");
    }

    /**
     * while graph is not empty
     *         get current config from graph
     *         if current config depth is less than or equal to max depth
     *             if current config is the goal
     *                 return true
     *             else
     *                 find all valid moves
     *         add current config to visited set
     *     return false
     * @param max_depth
     * @return if the goal was found
     */
    public boolean dls(int max_depth)
    {
        while(!graph.isEmpty())
        {
            current = graph.pop();

            if(current.getDepth() <= max_depth)
            {
                if(current.matches(goal))
                {
                    return true;
                }

                findValidMoves(current.getBoard());
            }
            visited.add(current);
        }
        return false;
    }

    /**
     * for the each place on the board
     * if 0 (blank space) is found
     * find valid adjacent moves
     * Gets the x and y coordinate of the blank piece and gets all valid moves for the piece
     * @param board current puzzle board configuration
     */
    private void findValidMoves(int[][] board)
    {
        int x, y;
        for(x = 0; x < board.length; x++)
        {
            for(y = 0; y < board.length; y++)
            {
                if(board[x][y] == 0)
                {
                    findValidAdjacentMoves(board, x, y);
                }
            }
        }
    }

    /**
     * for the each place on the board
     * if 0 (blank space) is found
     * find valid adjacent moves
     * Gets the x and y coordinate of the blank piece and gets all valid moves for the piece
     * @param x x location of current blank piece
     * @param y y location of current blank piece
     */
    private void findValidAdjacentMoves(int[][] board, int x, int y)
    {
        if(x-1 < 0)
            swapPosition(board, x, y, board.length-1, y);
        if(x-1 >= 0)
            swapPosition(board, x, y, x-1, y);
        if(x+1 >= 3)
            swapPosition(board, x, y, 0, y);
        if(x+1 < 3)
            swapPosition(board, x, y, x+1, y);
        if(y-1 < 0)
            swapPosition(board, x, y, x, board.length-1);
        if(y-1 >= 0)
            swapPosition(board, x, y, x, y-1);
        if(y+1 >= 3)
            swapPosition(board, x, y, x, 0);
        if(y+1 < 3)
            swapPosition(board, x, y, x, y+1);

    }

    /**
     * If not out of range of the grid then swap blank space and number
     * Swaps a given piece with the blank space and checks if the given puzzle has already
     * been looked at from another path, also increments the depth of the new puzzle
     * @param bpx blank piece x location
     * @param bpy blank piece y location
     * @param spx swap piece x location
     * @param spy swap piece y location
     */
    private void swapPosition(int[][] board, int bpx, int bpy, int spx, int spy)
    {
        SliderPuzzle newPuzzle = new SliderPuzzle(board);
        int[][] newBoard = newPuzzle.getBoard();
        newBoard[bpx][bpy] = newBoard[spx][spy];
        newBoard[spx][spy] = 0;

        newPuzzle.setBoard(newBoard);
        boolean matches = false;
        if(visited.contains(newPuzzle))
            matches = true;

        if(!matches)
        {
            newPuzzle.setDepth(current.getDepth() + 1);
            graph.add(newPuzzle);
        }
    }

    /**
     * for all items in the board print them
     * Prints the current puzzle state which is being looked at
     * @param board current puzzle board configuration
     */
    private void printGraph(int[][] board)
    {
        for(int x = 0; x < board.length; x++)
        {
            System.out.println();
            for(int y = 0; y < board.length; y++)
            {
                System.out.print(board[x][y] + " ");
            }
        }
        System.out.println();
    }
}
