import java.util.List;
import java.util.ArrayList;

/**
 * Created by Armstrong on 6/6/17.
 */
public class BruteCollinearPoints {

    private Point[] points;
    private List<LineSegment> Lines = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        if (points == null) {
            throw new NullPointerException();
        }

        this.points = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new NullPointerException();
            } else {
                this.points[i] = points[i];
            }
            for (int j = 0; j < i; j++) {
                if (points[j].compareTo(points[i]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        for (int a = 0; a < this.points.length-3; a++) {
            Point A = points[a];
            for (int b = a+1; b < this.points.length-2; b++) {
                Point B = points[b];
                for (int c = b+1; c < this.points.length-1; c++) {
                    Point C = points[c];
                    for (int d = c+1; d < this.points.length; d++) {
                        Point D = points[d];

                        double SlopeAB = A.slopeTo(B);
                        double SlopeBC = B.slopeTo(C);
                        double SlopeCD = C.slopeTo(D);
                        if (SlopeAB == SlopeBC && SlopeBC == SlopeCD) {
                            Point low;
                            Point high;

                            if (A.compareTo(B) > 0) {
                                high = A;
                                low = B;
                            } else {
                                high = B;
                                low = A;
                            }

                            if (C.compareTo(low) < 0) {
                                low = C;
                            }
                            if (C.compareTo(high) > 0) {
                                high = C;
                            }

                            if (D.compareTo(low) < 0) {
                                low = D;
                            }
                            if (D.compareTo(high) > 0) {
                                high = D;
                            }

                            LineSegment newLine = new LineSegment(low, high);

                            Lines.add(newLine);
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
       return Lines.size();
    }


    // the line segments
    public LineSegment[] segments() {

        LineSegment[] res = new LineSegment[Lines.size()];

        int pointer = 0;
        for (LineSegment l: Lines) {
            res[pointer] = l;
            pointer += 1;
        }

        return res;
    }
}
