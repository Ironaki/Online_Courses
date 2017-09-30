package hw3.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Armstrong on 5/5/17.
 */
public class Solver {

    private final WorldState initialWorld;

    private class searchNode implements Comparable<searchNode> {
        WorldState theWorld;
        int distance;
        searchNode prev;
        int totalDistance;

        public searchNode(WorldState theWorld, int distance, searchNode prev) {
            this.theWorld = theWorld;
            this.distance = distance;
            this.prev = prev;
            totalDistance = distance + theWorld.estimatedDistanceToGoal();
        }

        @Override
        public int compareTo(searchNode node) {
            return this.totalDistance - node.totalDistance;
        }
    }

    public Solver(WorldState initial) {
        initialWorld = initial;
    }

    public int moves() {

        MinPQ<searchNode> nodes = new MinPQ<>();
        nodes.insert(new searchNode(initialWorld, 0, null));

        while (!nodes.min().theWorld.isGoal()) {
            searchNode parent = nodes.delMin();
            for (WorldState singleWorld : parent.theWorld.neighbors()) {
                if (parent.prev == null) {
                    nodes.insert(new searchNode(singleWorld, parent.distance + 1, parent));
                }
                else if (!singleWorld.equals(parent.prev.theWorld)) {
                    nodes.insert(new searchNode(singleWorld, parent.distance + 1, parent));
                }
            }
        }
        return nodes.min().distance;
    }

    public Iterable<WorldState> solution() {

        MinPQ<searchNode> nodes = new MinPQ<>();
        nodes.insert(new searchNode(initialWorld, 0, null));

        while (!nodes.min().theWorld.isGoal()) {
            searchNode parent = nodes.delMin();
            for (WorldState singleWorld : parent.theWorld.neighbors()) {
                if (parent.prev == null) {
                    nodes.insert(new searchNode(singleWorld, parent.distance + 1, parent));
                }
                else if (!singleWorld.equals(parent.prev.theWorld)) {
                    nodes.insert(new searchNode(singleWorld, parent.distance + 1, parent));
                }
            }
        }

        List<WorldState> path = new ArrayList<>();
        searchNode des = nodes.min();
        while (des != null) {
            path.add(0, des.theWorld);
            des = des.prev;
        }

        return path;
    }

}


