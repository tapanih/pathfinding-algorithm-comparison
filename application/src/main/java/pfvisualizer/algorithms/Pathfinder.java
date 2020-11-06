package pfvisualizer.algorithms;

import java.util.ArrayList;
import pfvisualizer.util.Node;

public interface Pathfinder {
  float STRAIGHT_DISTANCE = 1;
  float DIAGONAL_DISTANCE = 1.41421356237f;

  ArrayList<Node> search(int[][] grid, int startCol, int startRow, int endCol, int endRow);

  /**
   * Reconstructs the path found by the pathfinding algorithm.
   *
   * @param node The end node of the path
   * @return the list of nodes that comprise the path
   *     (end node is the first element and start node is the last)
   */
  default ArrayList<Node> buildPath(Node node) {
    ArrayList<Node> path = new ArrayList<>();
    while (node != null) {
      path.add(node);
      node = node.getPrevious();
    }
    return path;
  }
}
