package pfvisualizer.util;

/**
 * A result of a search.
 */
public class Result {
  private final int[][] map;
  private final float distance;
  private final boolean pathFound;

  /**
   * The result of a pathfinding algorithm.
   *
   * @param map the map with the path and visited nodes marked
   * @param distance the length of the path found
   * @param pathFound a boolean representing whether a path was found of not
   */
  public Result(int[][] map, float distance, boolean pathFound) {
    this.map = map;
    this.distance = distance;
    this.pathFound = pathFound;
  }

  /**
   * Returns the map with the path and visited nodes marked.
   *
   * @return the map
   */
  public int[][] getMap() {
    return map;
  }

  /**
   * Returns the length of the path found.
   *
   * @return the length of the path
   */
  public float getDistance() {
    return distance;
  }

  /**
   * Check if a path was found of not.
   *
   * @return true if a path was found and false if path was not found
   */
  public boolean isPathFound() {
    return pathFound;
  }
}
