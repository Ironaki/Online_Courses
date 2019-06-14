import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Armstrong on 5/5/17.
 */
public class Solver {

    private final Board initialWorld;
    private final Board twinBoard;
    private boolean solvable;
    private searchNode finalNode;


    private class searchNode implements Comparable<searchNode> {
        Board theWorld;
        int distance;
        searchNode prev;
        int totalDistance;

        public searchNode(Board theWorld, int distance, searchNode prev) {
            this.theWorld = theWorld;
            this.distance = distance;
            this.prev = prev;
            totalDistance = distance + theWorld.manhattan();
        }

        @Override
        public int compareTo(searchNode node) {
            return this.totalDistance - node.totalDistance;
        }
    }

    public Solver(Board initial) {

        if (initial == null) {
            throw new NullPointerException();
        }

        initialWorld = initial;
        twinBoard = initialWorld.twin();

        MinPQ<searchNode> nodes = new MinPQ<>();
        MinPQ<searchNode> t_nodes = new MinPQ<>();

        nodes.insert(new searchNode(initialWorld, 0, null));
        t_nodes.insert(new searchNode(twinBoard, 0, null));

        while (!nodes.min().theWorld.isGoal() && !t_nodes.min().theWorld.isGoal()) {
            searchNode parent = nodes.delMin();
            searchNode t_parent = t_nodes.delMin();
            for (Board singleWorld : parent.theWorld.neighbors()) {
                if (parent.prev == null) {
                    nodes.insert(new searchNode(singleWorld, parent.distance + 1, parent));
                }
                else if (!singleWorld.equals(parent.prev.theWorld)) {
                    nodes.insert(new searchNode(singleWorld, parent.distance + 1, parent));
                }
            }
            for (Board singleWorld : t_parent.theWorld.neighbors()) {
                if (t_parent.prev == null) {
                    t_nodes.insert(new searchNode(singleWorld, t_parent.distance + 1, t_parent));
                }
                else if (!singleWorld.equals(t_parent.prev.theWorld)) {
                    t_nodes.insert(new searchNode(singleWorld, t_parent.distance + 1, t_parent));
                }
            }
        }

        if (nodes.min().theWorld.isGoal()) {
            finalNode = nodes.delMin();
            solvable = true;
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {

        if (solvable) {
            return finalNode.distance;
        } else {
            return -1;
        }
    }



    public Iterable<Board> solution() {

        if (solvable) {
            List<Board> path = new ArrayList<>();
            searchNode des = finalNode;
            while (des != null) {
                path.add(0, des.theWorld);
                des = des.prev;
            }

            return path;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}


