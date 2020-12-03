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

  public int[][] getMap() {
    return map;
  }

  public int getStartRow() {
    return startRow;
  }

  public int getStartCol() {
    return startCol;
  }

  public int getEndRow() {
    return endRow;
  }

  public int getEndCol() {
    return endCol;
  }

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
