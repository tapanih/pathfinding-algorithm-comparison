package pfvisualizer.algorithms;

import pfvisualizer.data.BinaryHeap;
import pfvisualizer.data.Heap;
import pfvisualizer.util.Node;
import pfvisualizer.util.Result;

import java.util.Arrays;

public class Dijkstra implements Pathfinder {
  protected int height;
  protected int width;
  protected int[][] grid;
  protected int[][] directions = {{-1, -1},  {1, -1}, {-1, 1}, {1, 1},
                                  {0, -1}, {-1, 0}, {0, 1}, {1, 0}};

  protected float heuristic(Node node, Node end) {
    return 0;
  }

  @Override
  public Result search(int[][] grid, int startCol, int startRow, int endCol, int endRow) {
    this.grid = grid;
    Node end = new Node(endRow, endCol, null);
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

    int[][] map = new int[height][width];
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
        return new Result(map, node.getHeuristic());
      }
      map[node.getRow()][node.getCol()] = VISITED;

      // loop through all the neighbors
      for (Node neighbor : getNeighbors(node)) {
        int newRow = neighbor.getRow();
        int newCol = neighbor.getCol();

        // check that the square is passable and unvisited
        if (map[newRow][newCol] != UNVISITED) {
          continue;
        }

        float edgeLength = STRAIGHT_DISTANCE;

        // check if we are moving diagonally
        if (node.getRow() != newRow && node.getCol() != newCol) {
          edgeLength = DIAGONAL_DISTANCE;
        }

        float newDistance = dist[node.getRow()][node.getCol()] + edgeLength;
        if (dist[newRow][newCol] > newDistance) {
          dist[newRow][newCol] = newDistance;
          Node newNode = new Node(newRow, newCol, node);
          newNode.setHeuristic(newDistance + heuristic(newNode, end));
          heap.insert(newNode);
        }
      }
    }
    return null;
  }

  /**
   * Returns a list of adjacent nodes that can be moved into from the given parent node.
   */
  protected Node[] getNeighbors(Node node) {
    int row = node.getRow();
    int col = node.getCol();
    Node[] neighbors = new Node[8];
    int i = 0;
    for (int[] direction: directions) {
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
    if (grid[row][col] == WALL) {
      return true;
    }
    return false;
  }
}
