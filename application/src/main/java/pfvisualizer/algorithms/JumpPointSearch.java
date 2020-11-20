package pfvisualizer.algorithms;

import pfvisualizer.data.BinaryHeap;
import pfvisualizer.data.Heap;
import pfvisualizer.util.Node;
import pfvisualizer.util.Result;

import java.util.Arrays;

public class JumpPointSearch extends AStar {
  private Node end;

  @Override
  public Result search(int[][] grid, int startCol, int startRow, int endCol, int endRow) {
    this.grid = grid;
    end = new Node(endRow, endCol, null);
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
      for (Node neighbor : identifySuccessors(node)) {
        int newRow = neighbor.getRow();
        int newCol = neighbor.getCol();
        int rowDist = Math.abs(neighbor.getRow() - node.getRow());
        int colDist = Math.abs(neighbor.getCol() - node.getCol());

        // check that the square is passable and unvisited
        if (map[newRow][newCol] != UNVISITED) {
          continue;
        }

        float edgeLength = STRAIGHT_DISTANCE * (rowDist + colDist) + DIAGONAL_DISTANCE * Math.min(rowDist, colDist);

        float newDistance = dist[node.getRow()][node.getCol()] + edgeLength;
        if (dist[newRow][newCol] > newDistance) {
          dist[newRow][newCol] = newDistance;
          neighbor.setHeuristic(newDistance + heuristic(neighbor, end));
          heap.insert(neighbor);
        }
      }
    }
    return null;
  }

  private Node jump(Node node, int deltaRow, int deltaCol) {
    int row = node.getRow();
    int col = node.getCol();

    if (isBlocked(row, col)) {
      return null;
    }

    if (col == end.getCol() && row == end.getRow()) {
      return node;
    }

    // diagonal jumps
    if (deltaRow != 0 && deltaCol != 0) {
      if (isBlocked(row + deltaRow, col) || isBlocked(row, col + deltaCol)) {
        return node;
      }
      // scan vertically and horizontally for jump points
      if (jump(new Node(row + deltaRow, col, node), deltaRow, 0) != null
              || jump(new Node(row, col + deltaCol, node), 0, deltaCol) != null) {
        return node;
      }
    } else {
      // horizontal jumps
      if (deltaRow == 0 && !isBlocked(row, col + deltaCol)) {
        if (isBlocked(row + 1, col) && !isBlocked(row + 1, col + deltaCol)) {
          return new Node(row, col + deltaCol, node.getPrevious());
        }
        if (isBlocked(row - 1, col) && !isBlocked(row - 1, col + deltaCol)) {
          return new Node(row, col + deltaCol, node.getPrevious());
        }
        // vertical jumps
      } else if (!isBlocked(row + deltaRow, col)) {
        if (isBlocked(row, col + 1) && !isBlocked(row + deltaRow, col + 1)) {
          return new Node(row + deltaRow, col, node.getPrevious());
        }
        if (isBlocked(row, col - 1) && !isBlocked(row + deltaRow, col - 1)) {
          return new Node(row + deltaRow, col, node.getPrevious());
        }
      }
    }
    Node next = new Node(row + deltaRow, col + deltaCol, node.getPrevious());
    return jump(next, deltaRow, deltaCol);
  }

  protected Node[] identifySuccessors(Node node) {
    Node[] successors = new Node[8];
    int i = 0;
    for (Node neighbor : getNeighbors(node)) {

      int deltaRow = neighbor.getRow() - node.getRow();
      int deltaCol = neighbor.getCol() - node.getCol();
      Node jumpNode = jump(neighbor, deltaRow, deltaCol);
      if (jumpNode != null) {
        successors[i++] = jumpNode;
      }
    }
    return Arrays.copyOf(successors, i);
  }

  @Override
  protected Node[] getNeighbors(Node node) {
    return super.getNeighbors(node);
    /*
    Node[] neighbors = new Node[5];
    int row = node.getRow();
    int col = node.getCol();
    int deltaRow = (row - parent.getRow()) / Math.max(Math.abs(row - parent.getRow()), 1);
    int deltaCol = (col - parent.getCol()) / Math.max(Math.abs(col - parent.getCol()), 1);
    System.out.println(node + ", " + deltaRow + " " + deltaCol);
    // diagonals
    int i = 0;
    if (deltaRow != 0 && deltaCol != 0) {
      if (!isBlocked(row + deltaRow, col)) {
        neighbors[i++] = new Node(row + deltaRow, col, null);
      }
      if (!isBlocked(row, col + deltaCol)) {
        neighbors[i++] = new Node(row, col + deltaCol, null);
      }
      if (!isBlocked(row + deltaRow, col + deltaCol)
          && !isBlocked(row + deltaRow, col) && !isBlocked(row, col + deltaCol)) {
        neighbors[i++] = new Node(row + deltaRow, col + deltaCol, null);
      }
      // forced neighbors
      if (isBlocked(row - deltaRow, col) && !isBlocked(row - deltaRow, col + deltaCol)
              && !isBlocked(row, col + deltaCol)) {
        neighbors[i++] = new Node(row - deltaRow, col + deltaCol, null);
      }
      if (isBlocked(row, col - deltaCol) && !isBlocked(row + deltaRow, col - deltaCol)
              && !isBlocked(row + deltaRow, col)) {
        neighbors[i++] = new Node(row + deltaRow, col - deltaCol, null);
      }
    } else {
      // horizontal
      if (deltaRow == 0 && !isBlocked(row, col + deltaCol)) {

        neighbors[i++] = new Node(row, col + deltaCol, null);

        // forced neighbors
        if (isBlocked(row + 1, col) && !isBlocked(row + 1, col + deltaCol)) {
          neighbors[i++] = new Node(row + 1, col + deltaCol, null);
        }
        if (isBlocked(row - 1, col) && !isBlocked(row - 1, col + deltaCol)) {
          neighbors[i++] = new Node(row - 1, col + deltaCol, null);
        }
        // vertical
      } else if (!isBlocked(row + deltaRow, col)) {

        neighbors[i++] = new Node(row + deltaRow, col, null);

        // forced neighbors
        if (isBlocked(row, col + 1) && !isBlocked(row + deltaRow, col + 1)) {
          neighbors[i++] = new Node(row + deltaRow, col + 1, null);
        }
        if (isBlocked(row, col - 1) && !isBlocked(row + deltaRow, col - 1)) {
          neighbors[i++] = new Node(row + deltaRow, col - 1, null);
        }
      }
    }
    System.out.println(i);
    return Arrays.copyOf(neighbors, i);
  }
     */
  }
}
