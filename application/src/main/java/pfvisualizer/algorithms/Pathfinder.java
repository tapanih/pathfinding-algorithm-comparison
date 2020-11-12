package pfvisualizer.algorithms;

import pfvisualizer.util.Node;
import pfvisualizer.util.Result;

public interface Pathfinder {
  float STRAIGHT_DISTANCE = 1;
  float DIAGONAL_DISTANCE = 1.41421356237f;

  Result search(int[][] grid, int startCol, int startRow, int endCol, int endRow);

  /**
   * Draws the path found by the algorithm to the map provided.
   *
   * @param node The end node of the path
   * @param map The map to draw the path into
   */
  default void buildPath(Node node, int[][] map) {
    while (node != null) {
      map[node.getRow()][node.getCol()] = 4;
      node = node.getPrevious();
    }
  }
}
