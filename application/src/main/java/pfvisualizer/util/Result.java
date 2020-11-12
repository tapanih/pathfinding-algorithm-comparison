package pfvisualizer.util;

public class Result {
  private int[][] map;
  private float distance;

  /**
   * The result of a pathfinding algorithm.
   */
  public Result(int[][] map, float distance) {
    this.map = map;
    this.distance = distance;
  }

  public int[][] getMap() {
    return map;
  }

  public float getDistance() {
    return distance;
  }
}
