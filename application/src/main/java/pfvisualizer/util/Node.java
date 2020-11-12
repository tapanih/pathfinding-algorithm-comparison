package pfvisualizer.util;

public class Node {
  private final int row;
  private final int col;
  private final Node previous;

  // this heuristic value is used by path-finding algorithms
  // at the end node the heuristic value will be the actual distance of the path
  private float heuristic = 0;

  /**
   * Constructs a node that is used in pathfinding.
   *
   * @param row the row number
   * @param col the column number
   * @param previous the parent node
   */
  public Node(int row, int col, Node previous) {
    this.row = row;
    this.col = col;
    this.previous = previous;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public Node getPrevious() {
    return previous;
  }

  public float getHeuristic() {
    return heuristic;
  }

  public void setHeuristic(float heuristic) {
    this.heuristic = heuristic;
  }

  @Override
  public String toString() {
    return "Node{" + "row=" + getRow() + ", col=" + getCol() + ", heuristic=" + heuristic + '}';
  }
}
