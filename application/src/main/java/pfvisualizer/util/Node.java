package pfvisualizer.util;

/**
 * Represents a node on the map.
 */
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
   * @param row the row coordinate of the node
   * @param col the column coordinate of the node
   * @param previous the parent node
   */
  public Node(int row, int col, Node previous) {
    this.row = row;
    this.col = col;
    this.previous = previous;
  }

  /**
   * Returns the row coordinate of this node.
   *
   * @return the row coordinate
   */
  public int getRow() {
    return row;
  }

  /**
   * Returns the column coordinate of this node.
   *
   * @return the column coordinate
   */
  public int getCol() {
    return col;
  }

  /**
   * Returns the parent of this node.
   *
   * @return the parent node or null if node has no parent
   */
  public Node getPrevious() {
    return previous;
  }

  /**
   * Returns the heuristic value of this node.
   *
   * @return the heuristic value
   */
  public float getHeuristic() {
    return heuristic;
  }

  /**
   * Sets the heuristic value for this node.
   *
   * @param heuristic the new heuristic value
   */
  public void setHeuristic(float heuristic) {
    this.heuristic = heuristic;
  }
}
