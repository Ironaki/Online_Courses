import java.util.*;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a LinkedList of <code>Long</code>s representing the shortest path from st to dest, 
     * where the longs are node IDs.
     */



    public static LinkedList<Long> shortestPath(GraphDB g, double stlon, double stlat, double destlon, double destlat) {

        class node implements Comparable<node> {
            long id;
            node prev;
            double distToDest;
            double distToSource;
            double priorityDist;

            public node(long id, node prev) {
                this.id = id;
                this.prev = prev;
            }

            public void setDistToSource(double distToSource) {
                this.distToSource = distToSource;
            }

            public void setDistToDest(double distToDest) {
                this.distToDest = distToDest;
            }

            public void setPriorityDist() {
                priorityDist = distToDest+distToSource;
            }

            @Override
            public int compareTo(node n) {
                if (this.priorityDist-n.priorityDist > 0.0) {
                    return 1;
                } else if (this.priorityDist-n.priorityDist < 0.0) {
                    return -1;
                } else {
                    return 0;
                }
            }

        }

        long source = g.closest(stlon, stlat);
        long dest = g.closest(destlon, destlat);

        PriorityQueue<node> fringe = new PriorityQueue<>();
        Set<Long> marked = new HashSet<>();
        node sourceNode = new node(source, null);
        sourceNode.setDistToSource(0.0);
        double totalDist = g.distance(source, dest);
        sourceNode.setDistToDest(totalDist);
        sourceNode.setPriorityDist();
        fringe.add(sourceNode);

        while(fringe.peek().id != dest) {
            node parent = fringe.poll();
            if (marked.contains(parent.id)) {
                continue;
            } else {
                marked.add(parent.id);
            }
            for (long adjID: g.adjacent(parent.id)) {
                if (parent.prev == null) {
                    node adjNode = new node(adjID, parent);
                    double distAdjToParent = g.distance(parent.id, adjID);
                    adjNode.setDistToSource(parent.distToSource + distAdjToParent);
                    double distAdjToDest = g.distance(adjID, dest);
                    adjNode.setDistToDest(distAdjToDest);
                    adjNode.setPriorityDist();
                    fringe.add(adjNode);
                } else if (adjID != parent.prev.id) {
                    node adjNode = new node(adjID, parent);
                    double distAdjToParent = g.distance(parent.id, adjID);
                    adjNode.setDistToSource(parent.distToSource + distAdjToParent);
                    double distAdjToDest = g.distance(adjID, dest);
                    adjNode.setDistToDest(distAdjToDest);
                    adjNode.setPriorityDist();
                    fringe.add(adjNode);
                }
            }
        }

        LinkedList<Long> res = new LinkedList<>();
        node traceBack = fringe.peek();
        while (traceBack != null) {
            res.addFirst(traceBack.id);
            traceBack = traceBack.prev;
        }

        return res;
    }
}
