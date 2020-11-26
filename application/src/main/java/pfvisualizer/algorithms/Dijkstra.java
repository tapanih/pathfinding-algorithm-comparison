package pfvisualizer.algorithms;

import java.util.Arrays;
import pfvisualizer.data.BinaryHeap;
import pfvisualizer.data.Heap;
import pfvisualizer.util.Node;
import pfvisualizer.util.Result;

public class Dijkstra implements Pathfinder {
  protected int height;
  protected int width;
  protected Node end;
  protected int[][] grid;
  protected int[][] map;
  protected int[][] directions = {{-1, -1},  {1, -1}, {-1, 1}, {1, 1},
                                  {0, -1}, {-1, 0}, {0, 1}, {1, 0}};

  protected float heuristic(Node node, Node end) {
    return 0;
  }

  @Override
  public Result search(int[][] grid, int startCol, int startRow, int endCol, int endRow) {
    this.grid = grid;
    this.end = new Node(endRow, endCol, null);
    Node start = new Node(startRow, startCol, null);
    start.setHeuristic(heuristic(start, end));
    height = grid.length;
    width = grid[0].length;

    float[][] dist = new float[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        dist[row][col] = Float.POSITIVE_INFINITY;
      }
    }

    this.map = new int[height][width];
    for (int row = 0; row < height; row++) {
      System.arraycopy(grid[row], 0, map[row], 0, width);
    }

    dist[start.getRow()][start.getCol()] = 0;

    Heap heap = new BinaryHeap();
    heap.insert(start);
    while (!heap.isEmpty()) {
      Node node = heap.extractMin();
      if (node.getCol() == end.getCol() && node.getRow() == end.getRow()) {
        buildPath(node, map);
        return new Result(map, node.getHeuristic(), true);
      }
      map[node.getRow()][node.getCol()] = VISITED;

      // loop through all the neighbors
      for (Node neighbor : getSuccessors(node)) {
        int newRow = neighbor.getRow();
        int newCol = neighbor.getCol();

        float edgeLength = getDistanceBetween(node, neighbor);

        float newDistance = dist[node.getRow()][node.getCol()] + edgeLength;
        if (dist[newRow][newCol] > newDistance) {
          dist[newRow][newCol] = newDistance;
          Node newNode = new Node(newRow, newCol, node);
          newNode.setHeuristic(newDistance + heuristic(newNode, end));
          heap.insert(newNode);
        }
      }
    }
    return new Result(map, -1f, false);
  }

  /**
   * Calculates the distance between two nodes.
   *
   * @return distance between nodes
   */
  protected float getDistanceBetween(Node first, Node second) {
    if (first.getCol() != second.getCol() && first.getRow() != second.getRow()) {
      return DIAGONAL_DISTANCE;
    }
    return STRAIGHT_DISTANCE;
  }

  /**
   * Returns a list of nodes that can be moved into from the given parent node.
   */
  protected Node[] getSuccessors(Node node) {
    int row = node.getRow();
    int col = node.getCol();
    Node[] neighbors = new Node[8];
    int i = 0;
    for (int[] direction : directions) {
      int deltaRow = direction[0];
      int deltaCol = direction[1];

      if (isBlocked(row + deltaRow, col + deltaCol)) {
        continue;
      }

      // if we are moving diagonally, check that we are not cutting corners
      if (deltaRow != 0 && deltaCol != 0) {
        if (grid[row + deltaRow][col] == WALL || grid[row][col + deltaCol] == WALL) {
          continue;
        }
      }

      neighbors[i++] = new Node(node.getRow() + deltaRow, node.getCol() + deltaCol, node);
    }
    return Arrays.copyOf(neighbors, i);
  }

  protected boolean isBlocked(int row, int col) {
    // check boundaries
    if (row < 0 || row >= height || col < 0 || col >= width) {
      return true;
    }
    return grid[row][col] == WALL;
  }
}
