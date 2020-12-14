package pfvisualizer.benchmark;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the result of a benchmark executed by ScenarioRunner.
 */
public class BenchmarkResults {
  List<double[][]> data = new ArrayList<>();
  List<Double> totals = new ArrayList<>();

  /**
   * Returns an array of value pairs for each path-finding algorithm.
   * The first array contains the value pairs for Dijkstra, the second array contains
   * the value pairs for A* and the third array contains the value pairs for JPS.
   * The first value of a value pair is the length of the optimal path and
   * the second value is the running time of the algorithm.
   *
   * @return an array of x and y coordinate pairs for each path-finding algorithm
   */
  public List<double[][]> getData() {
    return data;
  }


  /**
   * Returns a list of total median times for each path-finding algorithm.
   * The first value is the total median time for Dijkstra's algorithm,
   * the second value is the total median time for A* and the third value
   * is the total median time for JPS.
   *
   * @return a list of total median times
   */
  public List<Double> getTotals() {
    return totals;
  }
}
