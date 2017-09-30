/**
 * Created by Armstrong on 6/11/17.
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Set;
import java.util.TreeSet;

public class KdTree {

    private Node root;
    private int size;

    private static class Node {
        Point2D p;
        RectHV rect;
        boolean vertical;
        Node leftChild, rightChild;

        public Node (Point2D p, RectHV rect, boolean vertical) {
            this.p = p;
            this.rect = rect;
            this.vertical = vertical;
        }

    }

    // construct an empty set of points
    public KdTree() {
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (contains(p)) {
            return;
        }
        size += 1;
        Node pointer = root;
        RectHV rect = new RectHV(0.0,0.0,1.0,1.0);
        if (root == null) {
            root = new Node(p, rect, true);
            return;
        }
        while (true) {
            boolean isVertical = pointer.vertical;
            if (isVertical) {
                if (p.x() < pointer.p.x()) {
                    if (pointer.leftChild == null) {
                        rect = new RectHV(pointer.rect.xmin(), pointer.rect.ymin(), pointer.p.x(), pointer.rect.ymax());
                        pointer.leftChild = new Node(p, rect, false);
                        return;
                    }
                    pointer = pointer.leftChild;
                } else {
                    if (pointer.rightChild == null) {
                        rect = new RectHV(pointer.p.x(), pointer.rect.ymin(), pointer.rect.xmax(), pointer.rect.ymax());
                        pointer.rightChild = new Node(p, rect, false);
                        return;
                    }
                    pointer = pointer.rightChild;
                }
            } else {
                if (p.y() < pointer.p.y()) {
                    if (pointer.leftChild == null) {
                        rect = new RectHV(pointer.rect.xmin(), pointer.rect.ymin(), pointer.rect.xmax(), pointer.p.y());
                        pointer.leftChild = new Node(p, rect, true);
                        return;
                    }
                    pointer = pointer.leftChild;
                } else {
                    if (pointer.rightChild == null) {
                        rect = new RectHV(pointer.rect.xmin(), pointer.p.y(), pointer.rect.xmax(), pointer.rect.ymax());
                        pointer.rightChild = new Node(p, rect, true);
                        return;
                    }
                    pointer = pointer.rightChild;
                }
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        Node pointer = root;
        while (true) {
            if (pointer == null) {
                return false;
            }
            if (pointer.p.equals(p)) {
                return true;
            }
            if (pointer.vertical) {
                if (p.x() < pointer.p.x()) {
                    pointer = pointer.leftChild;
                } else {
                    pointer = pointer.rightChild;
                }
            } else {
                if (p.y() < pointer.p.y()) {
                    pointer = pointer.leftChild;
                } else {
                    pointer = pointer.rightChild;
                }
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        drawHelper(root);
    }

    private void drawHelper(Node n) {
        if (n == null) {
            return;
        } else {
            drawHelper(n.leftChild);

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            n.p.draw();
            StdDraw.setPenRadius();
            if (n.vertical) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
            }

            drawHelper(n.rightChild);
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Set<Point2D> res = new TreeSet<>();

        Node pointer = root;

        rangeHelper(pointer, rect, res);

        return res;
    }

    private void rangeHelper(Node n, RectHV rect, Set<Point2D> res) {
        if (n == null) {
            return;
        }
        if (!n.rect.intersects(rect)) {
            return;
        }
        rangeHelper(n.leftChild, rect, res);

        if (rect.contains(n.p)) {
            res.add(n.p);
        }

        rangeHelper(n.rightChild, rect, res);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) {
            return null;
        }
        return nearestHelper(root, p, root.p);
    }

    private Point2D nearestHelper(Node n, Point2D p, Point2D min) {
        if (n == null) {
            return min;
        }
        if (n.rect.distanceTo(p) > p.distanceTo(min)) {
            return min;
        }
        if (n.p.distanceTo(p) < min.distanceTo(p)) {
            min = n.p;
        }
        if (n.leftChild != null && n.rightChild != null) {
            if (n.leftChild.rect.contains(p)) {
                min = nearestHelper(n.leftChild, p, min);
                min = nearestHelper(n.rightChild, p, min);
            } else {
                min = nearestHelper(n.rightChild, p, min);
                min = nearestHelper(n.leftChild, p, min);
            }
        } else if (n.leftChild != null) {
            min = nearestHelper(n.leftChild, p, min);
        } else if (n.rightChild != null) {
            min = nearestHelper(n.rightChild, p, min);
        }
        return min;
    }




    // unit testing of the methods (optional)
    public static void main(String[] args) {
        
    }
}
