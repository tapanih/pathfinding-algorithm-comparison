package pfvisualizer.algorithms;

import pfvisualizer.util.Node;

import java.util.ArrayList;

public interface Pathfinder {
  public ArrayList<Node> search(int[][] grid, int startCol, int startRow, int endCol, int endRow);
}
