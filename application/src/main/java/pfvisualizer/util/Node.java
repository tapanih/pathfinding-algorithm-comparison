package pfvisualizer.util;

import java.util.Objects;

public class Node {
  public final int row;
  public final int col;
  private final float distance;
  private final Node previous;

  /**
   * Constructs a node that is used in pathfinding.
   *
   * @param row the row number
   * @param col the column number
   * @param distance the distance to the start node along some path
   * @param previous the parent node
   */
  public Node(int row, int col, float distance, Node previous) {
    this.row = row;
    this.col = col;
    this.distance = distance;
    this.previous = previous;
  }

  public float getDistance() {
    return distance;
  }

  public Node getPrevious() {
    return previous;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Node node = (Node) o;
    return row == node.row && col == node.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }

  @Override
  public String toString() {
    return "Node{" + "row=" + row + ", col=" + col + ", distance=" + distance + '}';
  }
}
