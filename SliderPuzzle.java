import java.util.Arrays;

public class SliderPuzzle {
    private int[][] board;
    private int depth = 0;
    private int gCost = 0;
    private int hCost = 0;
    private SliderPuzzle parent;

    public SliderPuzzle()
    {
        board = new int[3][3];
    }

    /**
     * Creates a Slider Puzzle based on a given board
     * @param board
     */
    public SliderPuzzle(int[][] board)
    {
        this.board = new int[3][3];
        for(int x = 0; x < board.length; x++)
        {
            for(int y = 0; y < board.length; y++)
            {
                this.board[x][y] = board[x][y];
            }
        }
    }

    /**
     * Sets the depth of the puzzle
     * @param depth
     */
    public void setDepth(int depth)
    {
        this.depth = depth;
    }

    /**
     * Gets the depth of the puzzle
     * @return
     */
    public int getDepth()
    {
        return depth;
    }

    /**
     * Sets the board
     * @param board
     */
    public void setBoard(int[][] board)
    {
        this.board = board;
    }

    /**
     * Gets the board
     * @return
     */
    public int[][] getBoard()
    {
        return board;
    }

    /**
     * Checks if the current board and the given board match
     * @param board
     * @return
     */
    public boolean matches(int[][] board)
    {
        for(int x = 0; x < board.length; x++)
        {
            for(int y = 0; y < board.length; y++)
            {
                if(this.board[x][y] != board[x][y])
                    return false;
            }
        }
        return true;
    }

    /**
     * Serts the G cost
     * @param gCost
     */
    public void setGCost(int gCost)
    {
        this.gCost = gCost;
    }

    /**
     * Find the x and y of each item in both boards then calculate the distance from their positions
     *
     * Sets the H cost
     * @param goal
     */
    public void setHCost(int[][] goal)
    {
        for(int x = 0; x < board.length; x++)
        {
            for(int y = 0; y < board.length; y++)
            {
                int value = this.board[x][y];
                for(int i = 0; i < board.length; i++)
                {
                    for(int j = 0; j < board.length; j++)
                    {
                        if(value == goal[i][j])
                        {
                            hCost += Math.abs(i - x) + Math.abs(j - y);
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets the G cost
     * @return
     */
    public int getGCost()
    {
        return gCost;
    }

    /**
     * Gets the F cost which is G cost + H Cost
     * @return
     */
    public int getFCost()
    {
        return gCost + hCost;
    }

    /**
     * Sets the parent of the puzzle
     * @param current
     */
    public void setParent(SliderPuzzle current)
    {
        this.parent = current;
    }

    /**
     * Gets the parent of the puzzle
     * @return
     */
    public SliderPuzzle getParent()
    {
        return parent;
    }

    /**
     * Sets the hashcode of the puzzle and is used when searching the hash set
     * @return
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(board);
        return result;
    }

    /**
     * checks if the current board and obj board are equal
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj)
    {
        if(obj == null)
            return false;
        SliderPuzzle other = (SliderPuzzle) obj;
        return matches(other.getBoard());
    }

}
