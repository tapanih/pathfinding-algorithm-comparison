package pfvisualizer.algorithms;

import pfvisualizer.util.Node;
import pfvisualizer.util.Result;

/**
 * An interface for path-finding algorithms.
 */
public interface Pathfinder {
  int UNVISITED = 0;
  int WALL = 1;
  int VISITED = 2;    // nodes added to the heap
  int CHECKED = 3;    // nodes checked by JPS but not added to the heap (processed fast)
  int PATH = 4;       // nodes that form the optimal path
  float STRAIGHT_DISTANCE = 1;
  float DIAGONAL_DISTANCE = 1.41421356237f; // sqrt(2)


  /**
   * Performs a search for a path from given starting coordinates to the given end coordinates
   * on the given grid.
   *
   * @param grid map to perform the search on
   * @param startCol start column
   * @param startRow start row
   * @param endCol end column
   * @param endRow  end row
   * @return Result object which contains information such as the length of the optimal path
   */
  Result search(int[][] grid, int startCol, int startRow, int endCol, int endRow);

  /**
   * Draws the path found by the algorithm to the map provided.
   *
   * @param node The end node of the path
   * @param map The map to draw the path into
   */
  default void buildPath(Node node, int[][] map) {
    while (node != null) {
      map[node.getRow()][node.getCol()] = PATH;
      node = node.getPrevious();
    }
  }
}
