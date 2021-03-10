package hw2;
import java.lang.*;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    double[] numArr;
    double mean;
    double stddev;
    double confidenceLow;
    double confidenHigh;
    int N;
    int T;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        this.N = N;
        this.T = T;
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T must be larger than 0");
        }

        // Array of the number of each percolation
        numArr = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation percoSim = pf.make(N);
            while (!percoSim.percolates()) {
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                percoSim.open(row, col);
            }
            numArr[i] = (double) percoSim.numberOfOpenSites() / (N * N);
        }
        mean = mean();
        stddev = stddev();
        confidenceLow = confidenceLow();
        confidenHigh = confidenceHigh();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(numArr);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(numArr);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }

    public static void main (String[] args) {
        PercolationStats x = new PercolationStats(100, 30, new PercolationFactory());
        System.out.println(x.mean());
    }
}
