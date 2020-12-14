package pfvisualizer.benchmark;

import java.util.Arrays;
import javafx.scene.chart.XYChart;
import pfvisualizer.algorithms.AStar;
import pfvisualizer.algorithms.Dijkstra;
import pfvisualizer.algorithms.JumpPointSearch;
import pfvisualizer.algorithms.Pathfinder;
import pfvisualizer.io.IO;
import pfvisualizer.util.Result;

/**
 * A class for performing benchmarks using scenarios.
 */
public class ScenarioRunner {
  private final IO io;
  private final Pathfinder dijkstra = new Dijkstra();
  private final Pathfinder astar = new AStar();
  private final Pathfinder jps = new JumpPointSearch();
  private final Scenario[] scenarios;
  private final int iterations;

  /**
   * Performs benchmarking.
   *
   * @param scenarios scenarios used for benchmarking
   * @param io        io used for printing progress notifications
   * @param iterations iterations per scenario
   */
  public ScenarioRunner(Scenario[] scenarios, IO io, int iterations) {
    this.scenarios = scenarios;
    this.io = io;
    this.iterations = iterations;
  }

  /**
   * Runs benchmarks with all the pathfinding algorithms, prints progress notifications to IO
   * and returns the results.
   *
   * @return benchmark results
   */
  public BenchmarkResults run() {
    BenchmarkResults results = new BenchmarkResults();

    runScenariosWith(dijkstra, "Dijkstra", results);
    runScenariosWith(astar, "A*", results);
    runScenariosWith(jps, "JPS", results);

    return results;
  }


  /**
   * Runs a benchmark with the given algorithm.
   *
   * @param algorithm the path-finding algorithm used
   * @param algorithmName the name of the algorithm
   * @param results the results object to update
   */
  private void runScenariosWith(Pathfinder algorithm, String algorithmName,
                                BenchmarkResults results) {
    double[][] data = new double[2][scenarios.length];
    double total = 0;
    for (int i = 0; i < scenarios.length; i++) {
      double result = runScenarioWith(algorithm, scenarios[i]);
      total += result;
      data[0][i] = scenarios[i].getExpectedDistance();
      data[1][i] = result;
      io.print("Benchmarking " + algorithmName + "... [" + i + "/" + scenarios.length + "]");
    }
    results.getData().add(data);
    results.getTotals().add(total);
  }

  /**
   * Runs a scenario with the given algorithm.
   *
   * @param algorithm the path-finding algorithm used
   * @param scenario the scenario used
   * @return the median running time (ms) of n iterations of the algorithm on the given scenario
   */
  private double runScenarioWith(Pathfinder algorithm, Scenario scenario) {
    algorithm.search(scenario.getMap(), scenario.getStartCol(), scenario.getStartRow(),
        scenario.getEndCol(), scenario.getEndRow());
    long[] times = new long[iterations];
    long time;
    for (int i = 0; i < iterations; i++) {
      time = System.nanoTime();
      Result result = algorithm.search(scenario.getMap(), scenario.getStartCol(),
          scenario.getStartRow(), scenario.getEndCol(), scenario.getEndRow());
      time = System.nanoTime() - time;

      // throw an exception if path found differs from expected
      if (Math.abs(result.getDistance() - scenario.getExpectedDistance()) > 0.1) {
        throw new RuntimeException();
      }
      times[i] = time;
    }
    Arrays.sort(times);
    return times[iterations / 2] / 1000000.0;
  }
}
