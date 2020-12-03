package pfvisualizer.benchmark;

import java.util.Arrays;
import pfvisualizer.algorithms.AStar;
import pfvisualizer.algorithms.Dijkstra;
import pfvisualizer.algorithms.JumpPointSearch;
import pfvisualizer.algorithms.Pathfinder;

public class ScenarioRunner {
  private final IO io;
  private final Pathfinder dijkstra = new Dijkstra();
  private final Pathfinder astar = new AStar();
  private final Pathfinder jps = new JumpPointSearch();
  private final Scenario[] scenarios;

  public ScenarioRunner(Scenario[] scenarios, IO io) {
    this.scenarios = scenarios;
    this.io = io;
  }

  /**
   * Runs benchmarks with all the pathfinding algorithms and prints results to IO.
   * @param iterations number of iterations per scenario
   */
  public void run(int iterations) {

    io.print("Benchmarking Dijkstra...");
    double total = 0;
    for (Scenario scenario : scenarios) {
      double result = runWith(dijkstra, scenario, iterations);
      total += result;
    }
    io.print("\nTotal median time: " + total + " ns");

    io.print("\n\nBenchmarking A*...");
    total = 0;
    for (Scenario scenario : scenarios) {
      double result = runWith(astar, scenario, iterations);
      total += result;
    }
    io.print("\nTotal median time: " + total + " ns");

    io.print("\n\nBenchmarking JPS...");
    total = 0;
    for (Scenario scenario : scenarios) {
      double result = runWith(jps, scenario, iterations);
      total += result;
    }
    io.print("\nTotal median time: " + total + " ns\n");

    io.print("End of benchmark.");
  }

  private double runWith(Pathfinder algorithm, Scenario scenario, int iterations) {
    algorithm.search(scenario.getMap(), scenario.getStartCol(), scenario.getStartRow(),
        scenario.getEndCol(), scenario.getEndRow());
    long[] times = new long[iterations];
    long time;
    for (int i = 0; i < iterations; i++) {
      time = System.nanoTime();
      algorithm.search(scenario.getMap(), scenario.getStartCol(),
          scenario.getStartRow(), scenario.getEndCol(), scenario.getEndRow());
      time = System.nanoTime() - time;
      times[i] = time;
    }
    Arrays.sort(times);
    return times[iterations / 2] / 1000000.0;
  }
}
