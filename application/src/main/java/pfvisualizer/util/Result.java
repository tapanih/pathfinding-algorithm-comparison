package pfvisualizer.util;

public class Result {
  private int[][] map;
  private float distance;
  private boolean pathFound;

  /**
   * The result of a pathfinding algorithm.
   */
  public Result(int[][] map, float distance, boolean pathFound) {
    this.map = map;
    this.distance = distance;
    this.pathFound = pathFound;
  }

  public int[][] getMap() {
    return map;
  }

  public float getDistance() {
    return distance;
  }

  public boolean isPathFound() {
    return pathFound;
  }
}
