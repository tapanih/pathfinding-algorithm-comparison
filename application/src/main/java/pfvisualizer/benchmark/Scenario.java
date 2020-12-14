package pfvisualizer.benchmark;

import java.util.Arrays;

public class Scenario {
  private final int[][] map;
  private final int startRow;
  private final int startCol;
  private final int endRow;
  private final int endCol;
  private final float expectedDistance;

  /**
   * Represents a scenario for performance testing.
   *
   * @param map the map for the scenario
   * @param startCol the column coordinate of the start node
   * @param startRow the row coordinate of the start node
   * @param endCol the column coordinate of the end node
   * @param endRow the row coordinate of the end node
   * @param expectedDistance the expected distance of the path between start and end nodes
   */
  public Scenario(int[][] map, int startCol, int startRow, int endCol,
                  int endRow, float expectedDistance) {
    this.map = map;
    this.startRow = startRow;
    this.startCol = startCol;
    this.endRow = endRow;
    this.endCol = endCol;
    this.expectedDistance = expectedDistance;
  }

  /**
   * Returns the map of this scenario.
   *
   * @return the map of this scenario
   */
  public int[][] getMap() {
    return map;
  }

  /**
   * Returns the row coordinate of the start node of this scenario.
   *
   * @return the row coordinate of the start node
   */
  public int getStartRow() {
    return startRow;
  }

  /**
   * Returns the column coordinate of the start node of this scenario.
   *
   * @return the column coordinate of the start node
   */
  public int getStartCol() {
    return startCol;
  }

  /**
   * Returns the row coordinate of the end node of this scenario.
   *
   * @return the row coordinate of the end node
   */
  public int getEndRow() {
    return endRow;
  }

  /**
   * Returns the column coordinate of the end node of this scenario.
   *
   * @return the column coordinate of the end node
   */
  public int getEndCol() {
    return endCol;
  }

  /**
   * Returns the expected distance between the start and end nodes of this scenario.
   *
   * @return the expected distance between the start and end nodes of this scenario
   */
  public float getExpectedDistance() {
    return expectedDistance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Scenario scenario = (Scenario) o;
    return startRow == scenario.getStartRow() && startCol == scenario.getStartCol()
        && endRow == scenario.getEndRow() && endCol == scenario.getEndCol()
        && Float.compare(scenario.getExpectedDistance(), expectedDistance) == 0
        && Arrays.deepEquals(map, scenario.getMap());
  }
}
