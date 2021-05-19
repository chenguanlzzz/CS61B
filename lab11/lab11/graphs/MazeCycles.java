package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

/**
 * @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int[] allEdgeTo;


    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        int s = maze.xyTo1D(1, 1);
        allEdgeTo = new int[maze.V()];
        allEdgeTo[s] = s;
        distTo[s] = 0;

        Stack<Integer> vertexStack = new Stack<>();
        vertexStack.push(s);
        while (!vertexStack.isEmpty()) {
            int cur = vertexStack.pop();
            marked[cur] = true;
            announce();
            for (int w : maze.adj(cur)) {
                if (w == allEdgeTo[cur]) {
                    continue;
                }
                allEdgeTo[w] = cur;
                distTo[w] = distTo[cur] + 1;

                if (marked[w]) {
                    circle(w);
                    return;
                }
                vertexStack.push(w);

            }

        }

    }

    public void circle(int w) {
        int back1 = allEdgeTo[w];
        int back2 = allEdgeTo[back1];
        int back3 = allEdgeTo[back2];
        edgeTo[w] = back1;
        edgeTo[back1] = back2;
        edgeTo[back2] = back3;
        edgeTo[back3] = w;
        announce();
    }

    // Helper methods go here
}

