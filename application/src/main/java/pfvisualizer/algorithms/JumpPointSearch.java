package pfvisualizer.algorithms;

import java.util.Arrays;
import pfvisualizer.data.BinaryHeap;
import pfvisualizer.data.Heap;
import pfvisualizer.util.Node;
import pfvisualizer.util.Result;

public class JumpPointSearch extends AStar {
  private Node end;
  private int[][] map;

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
        return new Result(map, node.getHeuristic());
      }

      // loop through all the generated jump points
      for (Node neighbor : identifySuccessors(node)) {

        int newRow = neighbor.getRow();
        int newCol = neighbor.getCol();

        int rowDist = Math.abs(neighbor.getRow() - node.getRow());
        int colDist = Math.abs(neighbor.getCol() - node.getCol());

        float edgeLength = STRAIGHT_DISTANCE * Math.abs(rowDist - colDist)
                           + DIAGONAL_DISTANCE * Math.min(rowDist, colDist);

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
      // scan vertically and horizontally for jump points
      if (jump(new Node(row + deltaRow, col, null), deltaRow, 0) != null
              || jump(new Node(row, col + deltaCol, null), 0, deltaCol) != null) {
        return node;
      }
    } else {
      // horizontal jumps
      if (deltaRow == 0) {
        if (!isBlocked(row + 1, col) && isBlocked(row + 1, col - deltaCol)) {
          return node;
        }
        if (!isBlocked(row - 1, col) && isBlocked(row - 1, col - deltaCol)) {
          return node;
        }
        // vertical jumps
      } else {
        if (!isBlocked(row, col + 1) && isBlocked(row - deltaRow, col + 1)) {
          return node;
        }
        if (!isBlocked(row, col - 1) && isBlocked(row - deltaRow, col - 1)) {
          return node;
        }
      }
    }

    if (isBlocked(row + deltaRow, col) || isBlocked(row, col + deltaCol)) {
      return node;
    }

    Node next = new Node(row + deltaRow, col + deltaCol, node.getPrevious());
    return jump(next, deltaRow, deltaCol);
  }

  /*
   * Finds jump points.
   */
  private Node[] identifySuccessors(Node node) {
    Node[] successors = new Node[8];
    int i = 0;
    for (Node neighbor : getNeighbors(node)) {

      int deltaRow = neighbor.getRow() - node.getRow();
      int deltaCol = neighbor.getCol() - node.getCol();
      Node jumpNode = jump(neighbor, deltaRow, deltaCol);
      if (jumpNode != null && map[jumpNode.getRow()][jumpNode.getCol()] == UNVISITED) {
        successors[i++] = jumpNode;
      }
    }
    return Arrays.copyOf(successors, i);
  }

  @Override
  protected Node[] getNeighbors(Node node) {
    Node parent = node.getPrevious();

    // return all neighbors if there is no parent
    if (parent == null) {
      return super.getNeighbors(node);
    }

    Node[] neighbors = new Node[5];
    int row = node.getRow();
    int col = node.getCol();

    // the parent might not be adjacent so normalize to -1, 1 or 0
    int deltaRow = (row - parent.getRow()) / Math.max(Math.abs(row - parent.getRow()), 1);
    int deltaCol = (col - parent.getCol()) / Math.max(Math.abs(col - parent.getCol()), 1);

    int i = 0;

    /*
     If the parent (p) is a diagonal neighbor, we can discard neighbors of the current square (c)
     marked with # because those can be reached quicker from the parent. This means, we only need
     to process neighbours marked with x.

          #xx
          #cx
          p##
    */
    if (deltaRow != 0 && deltaCol != 0) {
      if (!isBlocked(row + deltaRow, col)) {
        neighbors[i++] = new Node(row + deltaRow, col, node);
      }
      if (!isBlocked(row, col + deltaCol)) {
        neighbors[i++] = new Node(row, col + deltaCol, node);
      }

      // add the diagonal opposite of parent if it is walkable and we are not cutting corners
      if (!isBlocked(row + deltaRow, col + deltaCol)
          && !isBlocked(row + deltaRow, col) && !isBlocked(row, col + deltaCol)) {
        neighbors[i++] = new Node(row + deltaRow, col + deltaCol, node);
      }
    } else {
      /*
       if the parent is vertical neighbor, we can discard neighbors of the current square (c)
       marked with # because those can be reached quicker from the parent. This means, we only need
       to process neighbours marked with x.

           #xx
           pcx
           #xx
      */
      if (deltaRow == 0) {
        boolean nextBlocked = isBlocked(row, col + deltaCol);

        // add the neighbor opposite of parent if it is walkable
        if (!nextBlocked) {
          neighbors[i++] = new Node(row, col + deltaCol, node);
        }

        // add the bottom neighbor if it is walkable
        if (!isBlocked(row + 1, col)) {
          neighbors[i++] = new Node(row + 1, col, node);
          // add the diagonal if the bottom neighbor and the left/right neighbor are walkable
          if (!nextBlocked) {
            neighbors[i++] = new Node(row + 1, col + deltaCol, node);
          }
        }

        // add the top neighbor if it is walkable
        if (!isBlocked(row - 1, col)) {
          neighbors[i++] = new Node(row - 1, col, node);
          // add the diagonal if the bottom neighbor and the left/right neighbor are walkable
          if (!nextBlocked) {
            neighbors[i++] = new Node(row - 1, col + deltaCol, node);
          }
        }

        /*
         if the parent is horizontal neighbor, we can discard neighbors of the current square (c)
         marked with # because those can be reached quicker from the parent. This means, we only
         need to process neighbours marked with x.

             #p#
             xcx
             xxx
        */
      } else {
        boolean nextBlocked = isBlocked(row + deltaRow, col);

        // add the neighbor opposite of parent if it is walkable
        if (!nextBlocked) {
          neighbors[i++] = new Node(row + deltaRow, col, node);
        }

        // add the right neighbor if it is walkable
        if (!isBlocked(row, col + 1)) {
          neighbors[i++] = new Node(row, col + 1, node);
          // add the diagonal if the right neighbor and the top/bottom neighbor are walkable
          if (!nextBlocked) {
            neighbors[i++] = new Node(row + deltaRow, col + 1, node);
          }
        }

        // add the top neighbor if it is walkable
        if (!isBlocked(row, col - 1)) {
          neighbors[i++] = new Node(row, col - 1, node);
          // add the diagonal if the left neighbor and the top/bottom neighbor are walkable
          if (!nextBlocked) {
            neighbors[i++] = new Node(row + deltaRow, col - 1, node);
          }
        }
      }
    }

    return Arrays.copyOf(neighbors, i);
  }
}
