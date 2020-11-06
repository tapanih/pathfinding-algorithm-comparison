package pfvisualizer.algorithms;

import pfvisualizer.util.Node;

public class AStar extends Dijkstra {
  @Override
  protected float heuristic(Node node, Node end) {
    int deltaCol = node.getCol() - end.getCol();
    int deltaRow = node.getRow() - end.getRow();
    deltaCol = (deltaCol > 0) ? deltaCol : -deltaCol;
    deltaRow = (deltaRow > 0) ? deltaRow : -deltaRow;
    int diagonalMoves = (deltaCol < deltaRow) ? deltaCol : deltaRow;
    int straightMoves = (deltaCol > deltaRow) ? (deltaCol - deltaRow) : (deltaRow - deltaCol);
    return STRAIGHT_DISTANCE * straightMoves + DIAGONAL_DISTANCE * diagonalMoves;
  }
}
