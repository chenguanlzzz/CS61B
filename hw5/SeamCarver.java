import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    Picture pic;
    int width;
    int height;
    double[][] energy;

    public SeamCarver(Picture picture) {
        pic = picture;
        width = picture.width();
        height = picture.height();
        energy = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                energy[i][j] = energy(j, i);
            }
        }
    }

    // current picture
    public Picture picture() {
        return pic;
    }

    // width of current picture
    public int width() {
        return width;
    }

    // height of current picture
    public int height() {
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        double xGrad = gradientX(x, y, width);
        double yGrad = gradientY(x, y, height);
        return xGrad + yGrad;
    }

    private double gradientX(int x, int y, int width) {
        int xR, xG, xB;
        if (x != 0 && x != width - 1) {
            xR = pic.get(x - 1, y).getRed() - pic.get(x + 1, y).getRed();
            xG = pic.get(x - 1, y).getGreen() - pic.get(x + 1, y).getGreen();
            xB = pic.get(x - 1, y).getBlue() - pic.get(x + 1, y).getBlue();
        } else if (x == 0) {
            xR = pic.get(width - 1, y).getRed() - pic.get(x + 1, y).getRed();
            xG = pic.get(width - 1, y).getGreen() - pic.get(x + 1, y).getGreen();
            xB = pic.get(width - 1, y).getBlue() - pic.get(x + 1, y).getBlue();
        } else {
            xR = pic.get(x - 1, y).getRed() - pic.get(0, y).getRed();
            xG = pic.get(x - 1, y).getGreen() - pic.get(0, y).getGreen();
            xB = pic.get(x - 1, y).getBlue() - pic.get(0, y).getBlue();
        }
        return xR * xR + xG * xG + xB * xB;
    }

    private double gradientY(int x, int y, int height) {
        int yR, yG, yB;
        if (y != 0 && y != height - 1) {
            yR = pic.get(x, y - 1).getRed() - pic.get(x, y + 1).getRed();
            yG = pic.get(x, y - 1).getGreen() - pic.get(x, y + 1).getGreen();
            yB = pic.get(x, y - 1).getBlue() - pic.get(x, y + 1).getBlue();
        } else if (y == 0) {
            yR = pic.get(x, height - 1).getRed() - pic.get(x, y + 1).getRed();
            yG = pic.get(x, height - 1).getGreen() - pic.get(x, y + 1).getGreen();
            yB = pic.get(x, height - 1).getBlue() - pic.get(x, y + 1).getBlue();
        } else {
            yR = pic.get(x, y - 1).getRed() - pic.get(x, 0).getRed();
            yG = pic.get(x, y - 1).getGreen() - pic.get(x, 0).getGreen();
            yB = pic.get(x, y - 1).getBlue() - pic.get(x, 0).getBlue();
        }
        return yR * yR + yG * yG + yB * yB;
    }

    private void transpose() {
        // swap width and height
        int tmp = width;
        width = height;
        height = tmp;

        // Transpose energy
        double[][] energyTmp = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                energyTmp[i][j] = energy[j][i];
            }
        }
        energy = energyTmp;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        transpose();
        int[] ans = findVerticalSeam();
        transpose();
        return ans;
    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] energyCost = new double[height][width];
        energyCost[0] = energy[0];
        for (int i = 1; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.println(i);
                System.out.println(j);
                energyCost[i][j] = findMin(i, j, energyCost);
            }
        }
        return findShortestPath(energyCost);
    }

    private double findMin(int x, int y, double[][] energyCost) {
        MinPQ<Double> energyPQ = new MinPQ<>();
        energyPQ.insert(energyCost[x - 1][y]);
        if (y == 0) {
            energyPQ.insert(energyCost[x - 1][y + 1]);
        } else if (y == width - 1) {
            energyPQ.insert(energyCost[x - 1][y - 1]);
        } else {
            energyPQ.insert(energyCost[x - 1][y + 1]);
            energyPQ.insert(energyCost[x - 1][y - 1]);
        }
        return energyPQ.min() + energy[x][y];
    }

    private int[] findShortestPath(double[][] energyCost) {
        double min = energyCost[height - 1][0];
        int minIndex = 0;
        int[] ans = new int[height];

        for (int i = 1; i < width; i++) {
            if (energyCost[height - 1][i] < min) {
                min = energyCost[height - 1][i];
                minIndex = i;
            }
            ans[height - 1] = minIndex;
        }
        int nextMin = 0;
        for (int i = height - 2; i >= 0; i--) {
            nextMin = minIndex;
            min = energyCost[i][nextMin];
            if (nextMin == 0) {
                if (min > energyCost[i][nextMin + 1]) {
                    min = energyCost[i][nextMin + 1];
                    minIndex = nextMin + 1;
                }
            } else if (nextMin == width - 1) {
                if (min > energyCost[i][nextMin - 1]) {
                    min = energyCost[i][nextMin - 1];
                    minIndex = nextMin - 1;
                }
            } else {
                if (min > energyCost[i][nextMin + 1]) {
                    min = energyCost[i][nextMin + 1];
                    minIndex = nextMin + 1;
                }
                if (min > energyCost[i][nextMin - 1]) {
                    min = energyCost[i][nextMin - 1];
                    minIndex = nextMin - 1;
                }
            }
            ans[i] = minIndex;
        }
        return ans;
    }

    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        SeamRemover.removeHorizontalSeam(pic, findHorizontalSeam());
        height--;
    }

    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        SeamRemover.removeVerticalSeam(pic, findVerticalSeam());
        width--;
    }

    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);
        SeamCarver carver = new SeamCarver(picture);
        int[] x = carver.findVerticalSeam();
    }
}
