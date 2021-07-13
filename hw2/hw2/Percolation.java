package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.HashSet;
import java.util.Set;

public class Percolation {
    boolean[][] percoMatrix;
    int n;
    WeightedQuickUnionUF percoUF;
    int openSize;
    Set<Integer> openTop, openBot;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must > 0");
        }
        n = N;
        openSize = 0;
        // true for open, false for blocked;
        percoMatrix = new boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                percoMatrix[i][j] = false;
            }
        }

        // the second to last is the secret node for top, last node is secret node for bottom;
        percoUF = new WeightedQuickUnionUF(N * N + 2);
        //int top = N * N;
        //int bot = N * N + 1;
        //for (int i = 0; i < N; i++) {
        //  percoUF.union(i, top);
        //percoUF.union(top - 1 - i, bot);
        //}
        openTop = new HashSet<>();
        openBot = new HashSet<>();
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row > n || col > n) {
            throw new IndexOutOfBoundsException("Out of index.");
        }
        if (isOpen(row, col)) {
            return;
        }
        percoMatrix[row][col] = true;
        openSize += 1;
        int cur = xyTo1D(row, col);
        // Check the surrounding is fall or not, if it is, make this element fall
        if (row > 0 && isOpen(row - 1, col)) {
            percoUF.union(cur, xyTo1D(row - 1, col));
        }
        if (row < n - 1 && isOpen(row + 1, col)) {
            percoUF.union(cur, xyTo1D(row + 1, col));
        }
        if (col > 0 && isOpen(row, col - 1)) {
            percoUF.union(cur, xyTo1D(row, col - 1));
        }
        if (col < n - 1 && isOpen(row, col + 1)) {
            percoUF.union(cur, xyTo1D(row, col + 1));
        }
        if (row == n - 1) {
            if (!percolates()) {
                percoUF.union(n * n + 1, cur);
            } else {
                if (percoUF.connected(cur, n * n)) {
                    percoUF.union(n * n + 1, cur);
                } else {
                    boolean notPer = true;
                    for (int top : openTop) {
                        if (percoUF.connected(cur, top)) {
                            percoUF.union(n * n + 1, cur);
                            openTop.remove(top);
                            notPer = false;
                            break;
                        }
                    }
                    if (notPer) {
                        openBot.add(cur);
                    }
                }
            }
        }
        if (row == 0) {
            if (!percolates()) {
                percoUF.union(n * n, cur);
            } else {
                if (percoUF.connected(cur, n * n + 1)) {
                    percoUF.union(n * n, cur);
                } else {
                    boolean notPer = true;
                    for (int bot : openBot) {
                        if (percoUF.connected(cur, bot)) {
                            percoUF.union(n * n + 1, cur);
                            openBot.remove(bot);
                            notPer = false;
                            break;
                        }
                    }
                    if (notPer) {
                        openTop.add(cur);
                    }
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row > n || col > n) {
            throw new IndexOutOfBoundsException("Out of index.");
        }
        return percoMatrix[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > n || col > n) {
            throw new IndexOutOfBoundsException("Out of index.");
        }
        return isOpen(row, col) && percoUF.connected(xyTo1D(row, col), n * n)
                && percoUF.connected(xyTo1D(row, col), n * n + 1);

    }

    // change the xy correspond to 1D number
    private int xyTo1D(int x, int y) {
        return x * n + y;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSize;
    }

    // does the system percolate?
    public boolean percolates() {
        return percoUF.connected(n * n, n * n + 1);
    }

    // use for unit testing (not required)
    public static void main(String[] args) {
        Percolation x = new Percolation(3);
        x.open(0, 0);
        //System.out.println(x.isOpen(0, 0));
        //System.out.println(x.isFull(0, 0));
        //System.out.println(x.isFull(1, 0));
        //System.out.println(x.isOpen(1, 0));
        x.open(1, 0);
        //System.out.println(x.isOpen(1, 0));
        //System.out.println(x.isFull(1, 0));

        x.open(2, 0);
        System.out.println(x.percolates());
        x.open(2, 0);
        x.open(2, 2);
        System.out.println(x.isFull(2, 2));
        System.out.println(x.numberOfOpenSites());

    }


}
