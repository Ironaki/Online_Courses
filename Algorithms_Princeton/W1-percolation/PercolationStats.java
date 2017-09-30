/**
 * Created by Armstrong on 4/6/17.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static double nintyFiveCon = 1.96;
    private double[] trialResuslts;


    // Perform T independent experiment on an N-by-N grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        trialResuslts = new double[trials];
        int[] toBeFilled = StdRandom.permutation(n*n);

        for (int i = 0; i < trials; i++) {
            Percolation trial = new Percolation(n);
            int j = 0;
            while (!trial.percolates()) {
                int row = toBeFilled[j] / n;
                int col = toBeFilled[j] % n;
                trial.open(row + 1, col + 1);
                j++;
            }
            trialResuslts[i] = trial.numberOfOpenSites() / (double) (n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialResuslts);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialResuslts);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double conLow = mean()-stddev()*nintyFiveCon/Math.sqrt(trialResuslts.length);
        return conLow;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double conHigh = mean()+stddev()*nintyFiveCon/Math.sqrt(trialResuslts.length);
        return conHigh;
    }

    public static void main(String[] args) {
        PercolationStats experiment = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " +experiment.mean());
        System.out.println("stddev                  = " +experiment.stddev());
        System.out.println("95% confidence interval = [" +experiment.confidenceLo()+", "+experiment.confidenceHi()+"]");
    }
}
