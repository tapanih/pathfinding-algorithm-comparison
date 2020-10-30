package pfvisualizer.util;

import java.util.Objects;

public class Node {
  public final int row;
  public final int col;

  public Node(int row, int col) {
    this.row = row;
    this.col = col;
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
}
