import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by Armstrong on 6/6/17.
 */
public class FastCollinearPoints {

    private Point[] points;
    private List<LineSegment> Lines = new ArrayList<>();
    private Map<Point, List<Double>> foundLines =  new TreeMap<>();


    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

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

        Arrays.sort(this.points);

        for (int i = 0; i < this.points.length; i++) {
            Point origin = points[i];

            Arrays.sort(this.points, origin.slopeOrder());

            int pointer = 1;
            while (pointer < this.points.length-2) {
                double slope = origin.slopeTo(this.points[pointer]);
                if (slope == origin.slopeTo(this.points[pointer+2])) {
                    int increment = 2;
                    while (pointer+increment < this.points.length && slope == origin.slopeTo(this.points[pointer+increment])) {
                        increment += 1;
                    }
                    increment -= 1;
                    Point low = this.points[pointer];
                    Point high = this.points[pointer+increment];
                    if (origin.compareTo(low) < 0) {
                        low = origin;
                    } else if (origin.compareTo(high) > 0) {
                        high = origin;
                    }


                    List<Double> slopeList = foundLines.get(low);
                    if (slopeList != null) {
                        if (!slopeList.contains(slope)) {
                            Lines.add(new LineSegment(low, high));
                            slopeList.add(slope);
                        }
                    } else {
                        Lines.add(new LineSegment(low, high));
                        List<Double> newSlopeList = new ArrayList<>();
                        newSlopeList.add(slope);
                        foundLines.put(low, newSlopeList);
                    }

                    pointer += increment;
                } else if (slope == origin.slopeTo(this.points[pointer+1])) {
                    pointer += 2;
                } else {
                    pointer += 1;
                }
            }
            Arrays.sort(this.points);
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
