package pfvisualizer.algorithms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import pfvisualizer.util.Node;

public class Dijkstra implements Pathfinder {

  @Override
  public ArrayList<Node> search(int[][] grid, int startCol, int startRow, int endCol, int endRow) {
    Node start = new Node(startRow, startCol, 0, null);
    Node end = new Node(endRow, endCol, Float.POSITIVE_INFINITY, null);
    int height = grid.length;
    int width = grid[0].length;

    float[][] dist = new float[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        dist[row][col] = Float.POSITIVE_INFINITY;
      }
    }
    dist[start.row][start.col] = 0;

    boolean[][] visited = new boolean[height][width];
    PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(Node::getDistance));
    queue.add(start);
    while (!queue.isEmpty()) {
      Node node = queue.poll();
      if (node.equals(end)) {
        return buildPath(node);
      }
      if (visited[node.row][node.col]) {
        continue;
      }
      visited[node.row][node.col] = true;

      // loop through all the neighbors
      for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
        for (int colOffset = -1; colOffset <= 1; colOffset++) {
          if (rowOffset == 0 && colOffset == 0) {
            continue;
          }
          int newRow = node.row + rowOffset;
          int newCol = node.col + colOffset;

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
          if (node.row != newRow && node.col != newCol) {
            // if we are moving diagonally, check that we are not cutting corners
            if (grid[newRow][node.col] == 1 || grid[node.row][newCol] == 1) {
              continue;
            }
            edgeLength = DIAGONAL_DISTANCE;
          }

          float newDistance = node.getDistance() + edgeLength;
          if (dist[newRow][newCol] > newDistance) {
            dist[newRow][newCol] = newDistance;
            queue.add(new Node(newRow, newCol, newDistance, node));
          }
        }
      }
    }
    return null;
  }
}
