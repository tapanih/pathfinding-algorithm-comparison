package pfvisualizer.algorithms;

import pfvisualizer.data.BinaryHeap;
import pfvisualizer.data.Heap;
import pfvisualizer.util.Node;
import pfvisualizer.util.Result;

public class Dijkstra implements Pathfinder {
  protected int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1},
                                  {0, 1}, {1, -1}, {1, 0}, {1, 1}};

  protected float heuristic(Node node, Node end) {
    return 0;
  }

  @Override
  public Result search(int[][] grid, int startCol, int startRow, int endCol, int endRow) {
    Node end = new Node(endRow, endCol, null);
    Node start = new Node(startRow, startCol, null);
    start.setHeuristic(heuristic(start, end));
    int height = grid.length;
    int width = grid[0].length;

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
      for (int[] direction : directions) {
        int newRow = node.getRow() + direction[0];
        int newCol = node.getCol() + direction[1];

        // check that we are not moving off the map
        if (newRow < 0 || newRow >= height || newCol < 0 || newCol >= width) {
          continue;
        }

        // check that the square is passable and unvisited
        if (grid[newRow][newCol] != UNVISITED) {
          continue;
        }

        float edgeLength = STRAIGHT_DISTANCE;

        // check if we are moving diagonally
        if (node.getRow() != newRow && node.getCol() != newCol) {
          // if we are moving diagonally, check that we are not cutting corners
          if (grid[newRow][node.getCol()] == WALL || grid[node.getRow()][newCol] == WALL) {
            continue;
          }
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
}
