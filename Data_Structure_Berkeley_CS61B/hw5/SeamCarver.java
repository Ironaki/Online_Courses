import edu.princeton.cs.algs4.Picture;
import java.awt.Color;
import java.lang.reflect.Array;
import java.util.PriorityQueue;
import java.util.Arrays;

/**
 * Created by Armstrong on 5/17/17.
 */
public class SeamCarver {

    double[][] energy;
    Color[][] colors;
    Picture picture;
    int w;
    int h;


    public SeamCarver(Picture picture) {
        this.picture = picture;
        w = picture.width();
        h = picture.height();
        energy = new double[h][w];
        colors = new Color[h][w];

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                colors[row][col] = picture.get(col, row);
            }
        }

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                int above = row-1;
                int below = row+1;
                int left = col-1;
                int right = col+1;

                if (above == -1) {
                    above = h-1;
                }
                if (below == h) {
                    below = 0;
                }
                if (left == -1) {
                    left = w-1;
                }
                if (right == w) {
                    right = 0;
                }

                Color aboveColor = colors[above][col];
                Color belowColor = colors[below][col];
                Color leftColor = colors[row][left];
                Color rightColor = colors[row][right];

                double diffX = gradientDiff(aboveColor, belowColor);
                double diffY = gradientDiff(leftColor, rightColor);
                double thisEnergy = diffX+diffY;

                energy[row][col] = thisEnergy;
            }
        }
    }

    private double gradientDiff(Color c1, Color c2) {
        int redDiffS = (c1.getRed()-c2.getRed())*(c1.getRed()-c2.getRed());
        int greenDiffS = (c1.getGreen()-c2.getGreen())*(c1.getGreen()-c2.getGreen());
        int blueDiffS = (c1.getBlue()-c2.getBlue())*(c1.getBlue()-c2.getBlue());

        double res = redDiffS+greenDiffS+blueDiffS;

        return res;
    }


    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return w;
    }

    // height of current picture
    public int height() {
        return  h;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0|| x > w-1 || y < 0 || y > h-1) {
            throw new IndexOutOfBoundsException();
        } else {
            return energy[y][x];
        }
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transpose();
        int[] res = findVerticalSeam();
        transpose();
        return res;
    }

    private void transpose() {

        Color[][] colorsT = new Color[w][h];
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                colorsT[col][row] = colors[row][col];
            }
        }
        colors = colorsT;

        double[][] energyT = new double[w][h];
        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                energyT[col][row] = energy[row][col];
            }
        }
        energy = energyT;

        int temp = h;
        h = w;
        w = temp;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam()  {

        int[] res = new int[h];
        double[][] accumulatedEnergy = new double[h][w];

        for (int col = 0; col < w; col++) {
            accumulatedEnergy[0][col] = energy[0][col];
        }

        for (int row = 1; row < h; row++) {
            for (int col = 0; col < w; col++) {
                double min = accumulatedEnergy[row-1][col];

                if (col != 0) {
                    double left = accumulatedEnergy[row-1][col-1];
                    if (left < min) {
                        min = left;
                    }
                }

                if (col != w-1) {
                    double right = accumulatedEnergy[row-1][col+1];
                    if (right < min) {
                        min = right;
                    }
                }

                accumulatedEnergy[row][col] = min+energy[row][col];
            }
        }


        double minAccEnergy = accumulatedEnergy[h-1][0];
        int lastIndex = 0;
        for (int col = 0; col < w; col++) {
            double tEnergy = accumulatedEnergy[h-1][col];
            if (tEnergy <  minAccEnergy) {
                minAccEnergy = tEnergy;
                lastIndex = col;
            }
        }

        res[h-1] = lastIndex;
        for (int row = h-2; row > -1; row--) {
            int midIndex = lastIndex;
            double min = accumulatedEnergy[row][midIndex];
            if (midIndex != 0) {
                if (accumulatedEnergy[row][midIndex-1] < min) {
                    min = accumulatedEnergy[row][midIndex-1];
                    lastIndex = midIndex-1;
                }
            }
            if (midIndex != w-1) {
                if (accumulatedEnergy[row][midIndex+1] < min) {
                    lastIndex = midIndex+1;
                }
            }
            res[row] = lastIndex;
        }

        return res;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != w) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length-1; i++) {
            if (seam[i]-seam[i+1] < -1 || seam[i]-seam[i+1] > 1)  {
                throw new IllegalArgumentException();
            }
        }


        picture = SeamRemover.removeHorizontalSeam(picture, seam);

        h -= 1;
        energy = new double[h][w];
        colors = new Color[h][w];

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                colors[row][col] = picture.get(col, row);
            }
        }

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                int above = row - 1;
                int below = row + 1;
                int left = col - 1;
                int right = col + 1;

                if (above == -1) {
                    above = h - 1;
                }
                if (below == h) {
                    below = 0;
                }
                if (left == -1) {
                    left = w - 1;
                }
                if (right == w) {
                    right = 0;
                }

                Color aboveColor = colors[above][col];
                Color belowColor = colors[below][col];
                Color leftColor = colors[row][left];
                Color rightColor = colors[row][right];

                double diffX = gradientDiff(aboveColor, belowColor);
                double diffY = gradientDiff(leftColor, rightColor);
                double thisEnergy = diffX + diffY;

                energy[row][col] = thisEnergy;
            }
        }
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        if (seam.length != h) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < seam.length-1; i++) {
            if (seam[i]-seam[i+1] < -1 || seam[i]-seam[i+1] > 1)  {
                throw new IllegalArgumentException();
            }
        }

        picture = SeamRemover.removeVerticalSeam(picture, seam);

        w -= 1;
        energy = new double[h][w];
        colors = new Color[h][w];

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                colors[row][col] = picture.get(col, row);
            }
        }

        for (int row = 0; row < h; row++) {
            for (int col = 0; col < w; col++) {
                int above = row - 1;
                int below = row + 1;
                int left = col - 1;
                int right = col + 1;

                if (above == -1) {
                    above = h - 1;
                }
                if (below == h) {
                    below = 0;
                }
                if (left == -1) {
                    left = w - 1;
                }
                if (right == w) {
                    right = 0;
                }

                Color aboveColor = colors[above][col];
                Color belowColor = colors[below][col];
                Color leftColor = colors[row][left];
                Color rightColor = colors[row][right];

                double diffX = gradientDiff(aboveColor, belowColor);
                double diffY = gradientDiff(leftColor, rightColor);
                double thisEnergy = diffX + diffY;

                energy[row][col] = thisEnergy;
            }
        }
    }

}
