public class Main {
    public static void main(String[] args) {

        int[][] board ={{ 6, 4, 0 },
                        { 3, 7, 8 },
                        { 5, 1, 2 }};

        int[][] board2 ={{ 0, 2, 3 },
                         { 1, 7, 5 },
                         { 8, 4, 6 }};

        int[][] board3 ={{ 1, 6, 2 },
                         { 8, 7, 3 },
                         { 0, 5, 4 }};

        int[][] board4 ={{ 1, 2, 6 },
                         { 4, 0, 8 },
                         { 7, 5, 3 }};

        int[][] board5 ={{ 0, 8, 2 },
                         { 6, 4, 7 },
                         { 5, 3, 1 }};

        int[][] goal = {{ 1, 2, 3 },
                        { 4, 5, 6 },
                        { 7, 8, 0 }};

        SliderPuzzle puzzle = new SliderPuzzle();
        puzzle.setBoard(board4);
        puzzle.setDepth(0);

        IDDFS iddfs = new IDDFS();
        iddfs.dfid(puzzle, goal);

        BFS bfs = new BFS(puzzle, goal);
        bfs.bfs();

        ASTAR astar = new ASTAR(puzzle, goal);
        astar.solve();

    }
}
