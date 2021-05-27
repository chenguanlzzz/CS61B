package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


public class Solver {
    private SearchNode node;
    private MinPQ<SearchNode> nodeQueue;
    private LinkedList<WorldState> path;
    private int totalMove;
    private Set<WorldState> searched;
    private int numQue;

    private class SearchNode {
        private WorldState current;
        private int move;
        private int estDis;
        private SearchNode previous;

        private SearchNode(WorldState WS, int m, SearchNode p) {
            current = WS;
            move = m;
            previous = p;
            estDis = WS.estimatedDistanceToGoal();
        }
    }

    public Solver(WorldState initial) {
        node = new SearchNode(initial, 0, null);
        searched = new HashSet<>();
        nodeQueue = new MinPQ<>(new disComparator());
        nodeQueue.insert(node);
        numQue = 0;
        while (!nodeQueue.isEmpty()) {
            SearchNode nextNode = nodeQueue.delMin();
            node = nextNode;
            searched.add(node.current);
            if (nextNode.current.isGoal()) {
                break;
            }
            for (WorldState word : nextNode.current.neighbors()) {
                if (!searched.contains(word)) {
                    nodeQueue.insert(new SearchNode(word, node.move + 1, node));
                    numQue++;
                }
            }
        }
        totalMove = node.move;

    }

    private class disComparator implements Comparator<SearchNode> {
        public int compare(SearchNode w, SearchNode g) {
            int disW = w.move + w.estDis;
            int disG = g.move + g.estDis;
            return disW - disG;
        }
    }

    public int moves() {
        return totalMove;
    }

    public Iterable<WorldState> solution() {
        SearchNode tmp = node;
        path = new LinkedList<>();
        while (tmp != null) {
            path.addFirst(tmp.current);
            tmp = tmp.previous;
        }
        return path;
    }

    public int numQue() {
        return numQue;
    }
}
