import java.util.*;

public class ASTAR {

    private PriorityQueue<SliderPuzzle> front;
    private Set<SliderPuzzle> closedSet;
    private int[][] goal;
    private SliderPuzzle current;
    private Stack<SliderPuzzle> parents = new Stack<>();

    public ASTAR(SliderPuzzle puzzle, int[][] goal)
    {
        this.goal = goal;
        front = new PriorityQueue<>(new AStarComparator());
        puzzle.setGCost(0);
        puzzle.setHCost(goal);
        front.add(puzzle);
        closedSet = new HashSet<>();
    }

    /**
     * while the front is not empty
     *         get the current config from the front
     *         if the current config is the goal
     *            print out the time and depth
     *            return true
     *         else
     *             add the current config to the closed set
     *             find all valid moves for the current board configuration
     *      print out failed to find goal message
     *      return false
     * @return if the goal was found
     */
    public boolean solve()
    {
        long startTime = System.nanoTime();
        while(!front.isEmpty()) {

            current = front.poll();

            if (current.matches(goal))
            {
                long endTime = System.nanoTime();
                int totaldepth = current.getDepth();
                while(current.getParent() != null)
                {
                    parents.push(current);
                    current = current.getParent();
                }
                /*while(!parents.isEmpty())
                {
                     printGraph(parents.pop().getBoard());
                }*/
                System.out.println("[ASTAR] Took " + (endTime - startTime) + " nanosecs to complete and a depth of " + totaldepth + "\n");
                return true;
            }

            closedSet.add(current);

            findValidMoves(current.getBoard());
        }
        System.out.println("Could not find path to goal from starting state");
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
     * If not out of range of the grid then swap blank space and number
     * For all neighbors if the puzzle is not null
     * then check if the current gcost + 1 is less than the new gcost
     * if it is then update the gcost of the puzzle and add it to the puzzle if it is not already in it
     *
     * Finds all valid moves which the current blank piece can be filled with, within the puzzle bounds
     * @param x x location of current blank piece
     * @param y y location of current blank piece
     */
    private void findValidAdjacentMoves(int[][] board, int x, int y)
    {
        ArrayList<SliderPuzzle> neighbors = new ArrayList<>();
        if(x-1 < 0)
            neighbors.add(swapPosition(board, x, y, board.length-1, y));
        if(x-1 >= 0)
            neighbors.add(swapPosition(board, x, y, x-1, y));
        if(x+1 >= 3)
            neighbors.add(swapPosition(board, x, y, 0, y));
        if(x+1 < 3)
            neighbors.add(swapPosition(board, x, y, x+1, y));
        if(y-1 < 0)
            neighbors.add(swapPosition(board, x, y, x, board.length-1));
        if(y-1 >= 0)
            neighbors.add(swapPosition(board, x, y, x, y-1));
        if(y+1 >= 3)
            neighbors.add(swapPosition(board, x, y, x, 0));
        if(y+1 < 3)
            neighbors.add(swapPosition(board, x, y, x, y+1));

        for(SliderPuzzle puzzle : neighbors)
        {
            if(puzzle == null)
                continue;
            if(current.getGCost() + 1 < puzzle.getGCost() || !front.contains(puzzle))
            {
                puzzle.setGCost(current.getGCost() + 1);
                puzzle.setHCost(goal);
                puzzle.setParent(current);

                if(!front.contains(puzzle))
                {
                    front.add(puzzle);
                }
            }
        }
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
    private SliderPuzzle swapPosition(int[][] board, int bpx, int bpy, int spx, int spy)
    {
        SliderPuzzle newPuzzle = new SliderPuzzle(board);
        int[][] newBoard = newPuzzle.getBoard();
        newBoard[bpx][bpy] = newBoard[spx][spy];
        newBoard[spx][spy] = 0;

        boolean matches = false;
        for(SliderPuzzle puzzle : closedSet)
        {
            if(puzzle.matches(newBoard))
            {
                matches = true;
                break;
            }
        }

        if(!matches)
        {
            newPuzzle.setBoard(newBoard);
            newPuzzle.setDepth(current.getDepth() + 1);
            newPuzzle.setParent(current);
            return newPuzzle;
        }
        return null;
    }

    /**
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

    class AStarComparator implements Comparator<SliderPuzzle>
    {

        @Override
        public int compare(SliderPuzzle o1, SliderPuzzle o2) {
            if(o1.getFCost() > o2.getFCost())
                return 1;
            else if(o1.getFCost() < o2.getFCost())
                return -1;
            return 0;
        }
    }
}
