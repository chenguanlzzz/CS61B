package lab11.graphs;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /**
     * Conducts a breadth first search of the maze starting at the source.
     */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        marked[s] = true;
        announce();
        Queue<Integer> fringe = new ArrayBlockingQueue<>(maze.V());
        fringe.add(s);
        while (!fringe.isEmpty()) {
            int currentNode = fringe.poll();
            for (int w : maze.adj(currentNode)) {
                if (!marked[w]) {
                    edgeTo[w] = currentNode;
                    announce();
                    distTo[w] = distTo[currentNode] + 1;
                    if (w == t) {
                        return;
                    }
                    marked[w] = true;
                    fringe.add(w);
                }
            }
        }

    }


    @Override
    public void solve() {
        bfs();
    }
}

