package lab11.graphs;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private PriorityQueue<Integer> fringe;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        fringe = new PriorityQueue<>(maze.V(), new disComparator());
        fringe.add(s);

    }

    /**
     * Estimate of the distance from v to the target.
     */
    private int h(int v) {
        int vX = maze.toX(v);
        int vY = maze.toY(v);
        int tX = maze.toX(t);
        int tY = maze.toY(t);
        return Math.abs(vX - tX) + Math.abs(vY - tY);
    }

    /**
     * Finds vertex estimated to be closest to target.
     */
    private int findMinimumUnmarked() {

        /* You do not have to use this method. */
        return fringe.poll();
    }

    /**
     * Performs an A star search from vertex s.
     */
    private void astar(int s) {
        // TODO
        while (!fringe.isEmpty()) {
            int cur = findMinimumUnmarked();
            marked[cur] = true;
            if (cur == t) {
                announce();
                return;
            }

            for (int w : maze.adj(cur)) {
                if (!marked[w]) {
                    edgeTo[w] = cur;
                    distTo[w] = distTo[cur] + 1;
                    announce();
                    fringe.add(w);
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

    private class disComparator implements Comparator<Integer> {
        public int compare(Integer x, Integer y) {
            int comp = h(x) - h(y) + distTo[x] - distTo[y];
            return comp;
        }
    }

}

