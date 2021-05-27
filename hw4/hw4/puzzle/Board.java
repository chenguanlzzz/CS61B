package hw4.puzzle;

import java.util.HashSet;
import java.util.Set;

public class Board implements WorldState {
    private int[] boardCur;
    private int[] boardGoal;
    private int size;

    /**
     * /*Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     */
    public Board(int[][] tiles) {
        size = tiles[0].length;
        boardCur = new int[size * size];
        boardGoal = new int[size * size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardGoal[i * size + j] = i * size + j + 1;
                boardCur[i * size + j] = tiles[i][j];
            }
        }
        int missingValue = findMissingValue(boardCur);
        boardGoal[missingValue] = 0;
    }

    /*Returns value of tile at row i, column j (or 0 if blank)*/
    public int tileAt(int i, int j) {
        return boardCur[i * size + j];
    }

    /* Returns the board size N*/
    public int size() {
        return size;
    }


    private int[][] to2D(int[] arr1D) {
        int length = (int) Math.sqrt(arr1D.length);
        int[][] arr2D = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                arr2D[i][j] = arr1D[i * size + j];
            }
        }
        return arr2D;
    }

    private int findMissingValue(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (i == j) {
                    continue;
                }
                if (j == arr.length - 1) {
                    return j;
                }
            }
        }
        return 0;
    }

    private int findMissingIndex(int[] arr) {
        int i = 0;
        for (; i < arr.length; i++) {
            if (arr[i] == 0) {
                break;
            }
        }
        return i;
    }


    /* Returns the neighbors of the current board*/
    public Iterable<WorldState> neighbors() {
        Set<WorldState> neighbs = new HashSet<>();
        int missingIndex = findMissingIndex(boardCur);

        // Left Change only if the blank is not the left tiles
        if (missingIndex % size != 0) {
            int[] boardLeft = new int[size * size];
            System.arraycopy(boardCur, 0, boardLeft, 0, size * size);
            int tmp = boardLeft[missingIndex - 1];
            boardLeft[missingIndex - 1] = 0;
            boardLeft[missingIndex] = tmp;
            int[][] boardLeft2D = to2D(boardLeft);
            neighbs.add(new Board(boardLeft2D));
        }

        // Right Change only if the blank is not the right tiles
        if (missingIndex % size != size - 1) {
            int[] boardRight = new int[size * size];
            System.arraycopy(boardCur, 0, boardRight, 0, size * size);
            int tmp = boardRight[missingIndex + 1];
            boardRight[missingIndex + 1] = 0;
            boardRight[missingIndex] = tmp;
            int[][] boardRight2D = to2D(boardRight);
            neighbs.add(new Board(boardRight2D));
        }

        // Top Change only if the blank is not the top tiles
        if (missingIndex >= size) {
            int[] boardTop = new int[size * size];
            System.arraycopy(boardCur, 0, boardTop, 0, size * size);
            int tmp = boardTop[missingIndex - size];
            boardTop[missingIndex - size] = 0;
            boardTop[missingIndex] = tmp;
            int[][] boardTop2D = to2D(boardTop);
            neighbs.add(new Board(boardTop2D));
        }

        // Down Change only if the blank is not the down tiles
        if (size * size - missingIndex > size) {
            int[] boardDown = new int[size * size];
            System.arraycopy(boardCur, 0, boardDown, 0, size * size);
            int tmp = boardDown[missingIndex + size];
            boardDown[missingIndex + size] = 0;
            boardDown[missingIndex] = tmp;
            int[][] boardDown2D = to2D(boardDown);
            neighbs.add(new Board(boardDown2D));
        }
        return neighbs;
    }

    /*Hamming estimate described below*/
    public int hamming() {
        int error = 0;
        for (int i = 0; i < size * size; i++) {
            if (boardGoal[i] == 0) {
                continue;
            }
            if (boardCur[i] != boardGoal[i]) {
                error++;
            }
        }
        return error;
    }

    /*Manhattan estimate described below*/
    public int manhattan() {
        int error = 0;
        int[][] board2D = to2D(boardCur);
        int[][] goal2D = to2D(boardGoal);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (goal2D[i][j] == 0) {
                    continue;
                }
                if (goal2D[i][j] != board2D[i][j]) {
                    error += errorAdd(goal2D[i][j], i, j, board2D);
                }
            }
        }
        return error;
    }

    private int errorAdd(int goalValue, int i, int j, int[][] board2D) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (board2D[x][y] == goalValue) {
                    return Math.abs(i - x) + Math.abs(j - y);
                }
            }
        }
        return 0;
    }

    /*Estimated distance to goal. This method should
              simply return the results of manhattan() when submitted to
              Gradescope.*/
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /*Returns true if this board's tile values are the same
              position as y's*/
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board boardY = (Board) y;
        if (boardY.size() != size) {
            return false;
        }
        int[] boardY1D = boardY.boardCur;
        for (int i = 0; i < size * size; i++) {
            if (boardY1D[i] != boardCur[i]) {
                return false;
            }
        }
        return true;
    }


    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */

    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}

