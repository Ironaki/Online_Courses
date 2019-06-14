/***********************************************
 * Created by Armstrong 4/16/17
 * This is my favorite solution.
 ***********************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private boolean[] status;
    private int dimension;
    private WeightedQuickUnionUF systemEins;
    private WeightedQuickUnionUF systemZwei;
    private int numOfOpen;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        dimension = n;

        systemEins = new WeightedQuickUnionUF(dimension * dimension + 2);
        systemZwei = new WeightedQuickUnionUF(dimension * dimension + 1);

        status = new boolean[n*n];
        numOfOpen = 0;
    }

    // open site (row, col) if it is not open already
    public  void open(int row, int col) {
        if (row < 1 || row > dimension || col < 1 || col > dimension) {
            throw new IndexOutOfBoundsException();
        }

        // If it is open. We're done.
        if (isOpen(row, col)) {
            return;
        }

        // Open the closed site.
        int position = (row - 1) * dimension + col - 1;
        status[position] = true;
        numOfOpen++;
        // Attempt to connect in four cases
        if (col > 1) {
            if (status[position - 1]) {
                systemEins.union(position, position - 1);
                systemZwei.union(position, position - 1);
            }
        }
        if (col < dimension) {
            if (status[position + 1]) {
                systemEins.union(position, position + 1);
                systemZwei.union(position, position + 1);
            }
        }
        if (row > 1) {
            if (status[position - dimension]) {
                systemEins.union(position, position - dimension);
                systemZwei.union(position, position - dimension);
            }
        }
        if (row < dimension) {
            if (status[position + dimension]) {
                systemEins.union(position, position + dimension);
                systemZwei.union(position, position + dimension);
            }
        }
        if (row == dimension){
            systemEins.union(position, dimension*dimension+1);
        }
        if (row == 1){
            systemEins.union(position, dimension*dimension);
            systemZwei.union(position, dimension*dimension);
        }
    }

    // is site (row, col) open?
    public  boolean isOpen(int row, int col) {

        if (row < 1 || row > dimension || col < 1 || col > dimension) {
            throw new IndexOutOfBoundsException();
        }

        return status[(row - 1) * dimension + (col - 1)];
    }

    // is site (row, col) full?
    public  boolean isFull(int row, int col) {

        if (row < 1 || row > dimension || col < 1 || col > dimension) {
            throw new IndexOutOfBoundsException();
        }

        // If the site is closed, its not full.
        if (!isOpen(row, col)) {
            return false;
        }
        int position = (row - 1) * dimension + col- 1;
        return systemZwei.connected(position, dimension * dimension);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOfOpen;
    }


    // does the system percolate?
   public boolean percolates() {
//        for (int i = dimension * (dimension - 1); i < dimension * dimension; i++) {
//            if (isFull(dimension, i % dimension +1)) {
//                return true;
//            }
//        }
//        return false;

        return systemEins.connected(dimension*dimension,dimension*dimension+1);

    }


    // test client (optional)
//    public static void main(String[] args) {
//        Percolation test = new Percolation(3);
//        test.open(1, 2);
//        test.open(2, 2);
//        test.open(2, 3);
//        test.open(3, 3);
//        System.out.println(test.percolates());
//    }
}