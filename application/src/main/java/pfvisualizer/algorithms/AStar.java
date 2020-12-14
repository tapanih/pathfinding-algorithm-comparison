package pfvisualizer.algorithms;

import pfvisualizer.util.Node;

/**
 * A* algorithm.
 */
public class AStar extends Dijkstra {

  @Override
  protected float heuristic(Node node, Node end) {
    // difference in vertical direction
    int deltaCol = node.getCol() - end.getCol();
    deltaCol = (deltaCol > 0) ? deltaCol : -deltaCol;

    // difference in horizontal direction
    int deltaRow = node.getRow() - end.getRow();
    deltaRow = (deltaRow > 0) ? deltaRow : -deltaRow;

    // move diagonally as much as possible before moving straight
    int diagonalMoves = (deltaCol < deltaRow) ? deltaCol : deltaRow;
    int straightMoves = (deltaCol > deltaRow) ? (deltaCol - deltaRow) : (deltaRow - deltaCol);
    return STRAIGHT_DISTANCE * straightMoves + DIAGONAL_DISTANCE * diagonalMoves;
  }
}
