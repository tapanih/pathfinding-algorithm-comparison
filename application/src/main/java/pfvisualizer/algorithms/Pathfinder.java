package pfvisualizer.algorithms;

import pfvisualizer.util.Node;

import java.util.ArrayList;

public interface Pathfinder {
  ArrayList<Node> search(int[][] grid, int startCol, int startRow, int endCol, int endRow);

  default ArrayList<Node> buildPath(Node node) {
    ArrayList<Node> path = new ArrayList<>();
    while (node != null) {
      path.add(node);
      node = node.getPrevious();
    }
    return path;
  }
}
