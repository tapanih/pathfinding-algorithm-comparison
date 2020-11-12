package pfvisualizer.algorithms;

import pfvisualizer.data.BinaryHeap;
import pfvisualizer.data.Heap;
import pfvisualizer.util.Node;
import pfvisualizer.util.Result;

public class Dijkstra implements Pathfinder {
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
      for (int col = 0; col < width; col++) {
        map[row][col] = grid[row][col];
      }
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
      if (map[node.getRow()][node.getCol()] == 2) {
        continue;
      }
      map[node.getRow()][node.getCol()] = 2;

      // loop through all the neighbors
      for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
        for (int colOffset = -1; colOffset <= 1; colOffset++) {
          if (rowOffset == 0 && colOffset == 0) {
            continue;
          }
          int newRow = node.getRow() + rowOffset;
          int newCol = node.getCol() + colOffset;

          // check that we are not moving off the map
          if (newRow < 0 || newRow >= height || newCol < 0 || newCol >= width) {
            continue;
          }

          // check that the square is passable
          if (grid[newRow][newCol] == 1) {
            continue;
          }

          float edgeLength = STRAIGHT_DISTANCE;

          // check if we are moving diagonally
          if (node.getRow() != newRow && node.getCol() != newCol) {
            // if we are moving diagonally, check that we are not cutting corners
            if (grid[newRow][node.getCol()] == 1 || grid[node.getRow()][newCol] == 1) {
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
    }
    return null;
  }
}
