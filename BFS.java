import java.util.*;

public class BFS {

    private Queue<SliderPuzzle> graph;
    private Set<SliderPuzzle> visited;
    private SliderPuzzle puzzle;
    private SliderPuzzle current;
    private int[][] goal;

    public BFS(SliderPuzzle puzzle, int[][] goal)
    {
        this.puzzle = puzzle;
        this.goal = goal;
    }

    /**
     * While there are still configurations in queue
     *         take the head configuration
     *         if the current configuration is the solution
     *             print out depth and time
     *         else
     *             find all valid configurations of moving tiles into the blank spot
     *             if
     *                 the configuration is in the visited set then do not add it to the graph
     *             else
     *                 add configuration to graph
     *         add current configuration to the visited set
     *      return it did not find it
     * @return if the goal was found
     */
    public boolean bfs()
    {
        graph = new LinkedList<>();
        visited = new HashSet<>();
        graph.add(puzzle);

        long startTime = System.nanoTime();

        while(!graph.isEmpty())
        {
            current = graph.poll();

            if(current.matches(goal))
            {
                long endTime = System.nanoTime();
                System.out.println("[BFS] Took " + (endTime - startTime) + " nanosecs to complete and a depth of " + current.getDepth() + "\n");
                return true;
            }

            findValidMoves(current.getBoard());

            visited.add(current);
        }
        System.out.println("Could not find path to goal from starting state");
        return false;
    }

    /**
     * for the each place on the board
     * if 0 (blank space) is found
     * find valid adjacent moves
     * Gets the x and y coordinate of the blank piece and gets all valid moves for the piece
     * @param board
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
     * If not out of range of the grid then swap blank space and number
     * Finds all valid moves which the current blank piece can be filled with, within the puzzle bounds
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
     * create a new puzzle based on the original
     * then swap the blank space and the number
     * check if it is already in the visited set
     * if not then add it to the graph and set the depth
     *
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

}
