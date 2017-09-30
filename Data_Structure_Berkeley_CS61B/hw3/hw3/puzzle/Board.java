package hw3.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState{

    private int[][] tiles;
    private int size;

    public Board(int[][] tiles) {
        size = tiles.length;
        int dimension = size;
        this.tiles = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i > size-1 || j < 0 || j > size-1) {
            throw new IndexOutOfBoundsException();
        }
        return tiles[i][j];
    }

    public int size() {
        return size;
    }

    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int ans = 0;

        for (int i = 1; i < size*size; i++) {
            int row = (i-1)/size;
            int col = (i-1)%size;
            if (this.tileAt(row, col) != i) {
                ans++;
            }
        }

        return ans;
    }

    public int manhattan() {
        int ans = 0;

        for (int i = 1; i < size*size; i++){
            int row = (i - 1) / size;
            int col = (i - 1) % size;
            int actual =  this.tileAt(row, col);
            if (actual != i) {
                int supRow = (actual-1)/size;
                int supCol = (actual-1)%size;
                ans += Math.abs(row-supRow) + Math.abs(col-supCol);
            }
        }

        return ans;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean isGoal() {

        for (int i = 1; i < size*size; i++) {
            int row = (i-1)/size;
            int col = (i-1)%size;
            if (this.tileAt(row, col) != i) {
                return false;
            }
        }

        if (this.tileAt(size-1, size-1) != 0) {
            return false;
        }

        return true;
    }

    public boolean equals(Object y) {

        Board yBoard;

        try{
            yBoard = (Board) y;
        } catch (Exception e) {
            return false;
        }

        if (size != yBoard.size) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] != yBoard.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
