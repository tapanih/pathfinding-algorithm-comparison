package pfvisualizer.algorithms;

import pfvisualizer.data.BinaryHeap;
import pfvisualizer.data.Heap;
import pfvisualizer.util.Node;
import pfvisualizer.util.Result;

public class JumpPointSearch extends AStar {
  private int height;
  private int width;
  private int directionCount = 0;

  @Override
  public Result search(int[][] grid, int startCol, int startRow, int endCol, int endRow) {
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

      int[][] directions = getDirections(node, grid);
      // loop through all the neighbors
      for (int i = 0; i < directionCount; i++) {
        int newRow = node.getRow() + directions[i][0];
        int newCol = node.getCol() + directions[i][1];

        // check that we are not moving off the map
        if (newRow < 0 || newRow >= height || newCol < 0 || newCol >= width) {
          continue;
        }

        // check that the square is passable and unvisited
        if (map[newRow][newCol] != UNVISITED) {
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

  private int[][] getDirections(Node node, int[][] grid) {
    Node parent = node.getPrevious();
    if (parent == null) {
      directionCount = 8;
      return directions;
    }
    int[][] dirs = new int[5][2];
    directionCount = 0;
    int row = node.getRow();
    int col = node.getCol();
    int deltaRow = row - parent.getRow();
    int deltaCol = col - parent.getCol();

    // diagonals
    if (deltaRow != 0 && deltaCol != 0) {
      if (row + deltaRow >= 0 && row + deltaRow < height
          && grid[row + deltaRow][col] != WALL) {
        dirs[directionCount][0] = deltaRow;
        dirs[directionCount][1] = 0;
        directionCount++;
      }
      if (col + deltaCol >= 0 && col + deltaCol < width
          && grid[row][col + deltaCol] != WALL) {
        dirs[directionCount][0] = 0;
        dirs[directionCount][1] = deltaCol;
        directionCount++;
      }
      if (row + deltaRow >= 0 && row + deltaRow < height
          && col + deltaCol >= 0 && col + deltaCol < width
          && grid[row + deltaRow][col + deltaCol] != WALL) {
        dirs[directionCount][0] = deltaRow;
        dirs[directionCount][1] = deltaCol;
        directionCount++;
      }
      if (row - deltaRow >= 0 && row - deltaRow < height
          && col + deltaCol >= 0 && col + deltaCol < width
          && grid[row - deltaRow][col] == WALL) {
        dirs[directionCount][0] = -deltaRow;
        dirs[directionCount][1] = deltaCol;
        directionCount++;
      }
      if (row + deltaRow >= 0 && row + deltaRow < height
          && col - deltaCol >= 0 && col - deltaCol < width
          && grid[row - deltaRow][col] == WALL) {
        dirs[directionCount][0] = deltaRow;
        dirs[directionCount][1] = -deltaCol;
        directionCount++;
      }
    } else {
      // horizontal
      if (deltaRow == 0 && col + deltaCol >= 0 && col + deltaCol < width) {
        if (grid[row][col + deltaCol] != WALL) {
          dirs[directionCount][0] = 0;
          dirs[directionCount][1] = deltaCol;
          directionCount++;
        }
        if (row + 1 < width && grid[row + 1][col] == WALL) {
          dirs[directionCount][0] = 1;
          dirs[directionCount][1] = deltaCol;
          directionCount++;
        }
        if (row - 1 >= 0 && grid[row - 1][col] == WALL) {
          dirs[directionCount][0] = - 1;
          dirs[directionCount][1] = deltaCol;
          directionCount++;
        }
      }
      // vertical
      else if (row + deltaRow >= 0 && row + deltaRow < height) {
        if (grid[row + deltaRow][col] != WALL) {
          dirs[directionCount][0] = deltaRow;
          dirs[directionCount][1] = 0;
          directionCount++;
        }
        if (col + 1 < height && grid[row][col + 1] == WALL) {
          dirs[directionCount][0] = deltaRow;
          dirs[directionCount][1] = 1;
          directionCount++;
        }
        if (col - 1 >= 0 && grid[row][col - 1] == WALL) {
          dirs[directionCount][0] = deltaRow;
          dirs[directionCount][1] = -1;
          directionCount++;
        }
      }
    }
    return dirs;
  }
}
