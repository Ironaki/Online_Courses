/**
 * Created by Armstrong on 6/11/17.
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.TreeSet;
import java.util.Set;

public class PointSET {

    private Set<Point2D> points;

   // construct an empty set of points
   public PointSET() {
        points = new TreeSet<>();
   }

   // is the set empty?
   public boolean isEmpty() {
       return points.isEmpty();
   }

   // number of points in the set
   public int size() {
        return points.size();
   }

   // add the point to the set (if it is not already in the set)
   public void insert(Point2D p) {
       if (p == null) {
           nullArgumentExceptionThrower();
       }
       points.add(p);
   }

   // does the set contain point p
   public boolean contains(Point2D p) {
        if (p == null) {
            nullArgumentExceptionThrower();
        }
        return points.contains(p);
   }

   // draw all points to standard draw
   public void draw() {
        for (Point2D p: points) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            p.draw();
        }
   }

   // all points that are inside the rectangle
   public Iterable<Point2D> range(RectHV rect) {
       if (rect == null) {
           nullArgumentExceptionThrower();
       }

       Set<Point2D> res = new TreeSet<>();

       for (Point2D p: points) {
           if (rect.contains(p)) {
               res.add(p);
           }
       }

       return res;
   }

   // a nearest neighbor in the set to point p; null if the set is empty
   public Point2D nearest(Point2D p) {
       Point2D res = null;

       for (Point2D point: points) {
           if (res == null) {
               res = point;
           } else if (point.distanceTo(p) < res.distanceTo(p)) {
               res = point;
           }
       }

       return res;
   }

   private void nullArgumentExceptionThrower() {
       throw new NullPointerException();
   }

   // unit testing of the methods (optional)
   public static void main(String[] args) {

   }
}
