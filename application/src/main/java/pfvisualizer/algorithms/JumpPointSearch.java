package pfvisualizer.algorithms;

import java.util.Arrays;
import pfvisualizer.util.Node;

public class JumpPointSearch extends AStar {

  @Override
  public void buildPath(Node node, int[][] map) {
    while (node.getPrevious() != null) {
      Node prev = node.getPrevious();
      drawLine(node.getRow(), node.getCol(), prev.getRow(), prev.getCol());
      node = prev;
    }
    // this makes sure the "path" is correctly drawn when start and end nodes are equal
    map[node.getRow()][node.getCol()] = PATH;
  }

  /**
   * Draws a line using Bresenham's line algorithm.
   * https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
   */
  private void drawLine(int x0, int y0, int x1, int y1) {
    int dx = Math.abs(x1 - x0);
    int sx = x0 < x1 ? 1 : -1;
    int dy = -Math.abs(y1 - y0);
    int sy = y0 < y1 ? 1 : -1;
    int err = dx + dy;

    while (true) {
      map[x0][y0] = PATH;
      if (x0 == x1 && y0 == y1) {
        break;
      }
      int e2 = 2 * err;
      if (e2 >= dy) {
        err += dy;
        x0 += sx;
      }
      if (e2 <= dx) {
        err += dx;
        y0 += sy;
      }
    }
  }

  @Override
  protected float getDistanceBetween(Node first, Node second) {
    int rowDist = Math.abs(first.getRow() - second.getRow());
    int colDist = Math.abs(first.getCol() - second.getCol());

    return STRAIGHT_DISTANCE * Math.abs(rowDist - colDist)
        + DIAGONAL_DISTANCE * Math.min(rowDist, colDist);
  }

  /**
   * Finds jump points.
   */
  @Override
  protected Node[] getSuccessors(Node node) {
    Node[] successors = new Node[8];
    int i = 0;
    for (Node neighbor : getPrunedNeighbors(node)) {

      int deltaRow = neighbor.getRow() - node.getRow();
      int deltaCol = neighbor.getCol() - node.getCol();
      Node jumpNode = jump(neighbor, deltaRow, deltaCol);
      if (jumpNode != null && map[jumpNode.getRow()][jumpNode.getCol()] != VISITED) {
        successors[i++] = jumpNode;
      }
    }
    return Arrays.copyOf(successors, i);
  }

  /**
   * Recursively finds a jump point given a parent node and a direction.
   *
   * @return The jump point
   */
  private Node jump(Node node, int deltaRow, int deltaCol) {
    int row = node.getRow();
    int col = node.getCol();

    if (isBlocked(row, col)) {
      return null;
    }

    if (map[row][col] != VISITED) {
      map[row][col] = CHECKED;
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
      return null;
    }

    Node next = new Node(row + deltaRow, col + deltaCol, node.getPrevious());
    return jump(next, deltaRow, deltaCol);
  }


  /**
   * Returns a list of neighbouring nodes after pruning.
   */
  private Node[] getPrunedNeighbors(Node node) {
    Node parent = node.getPrevious();

    // return all neighbors if there is no parent
    if (parent == null) {
      return super.getSuccessors(node);
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
