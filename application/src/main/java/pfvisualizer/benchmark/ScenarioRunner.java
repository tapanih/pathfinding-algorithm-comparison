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

    double dijkstraTotal = runScenariosWith(dijkstra, "Dijkstra", iterations);
    double astarTotal = runScenariosWith(astar, "A*", iterations);
    double jpsTotal = runScenariosWith(jps, "JPS", iterations);

    io.print(String.format("Total median times: \n"
        + "Dijkstra: %.2f ns\n"
        + "A*: %.2f ns\n"
        + "JPS: %.2f ns", dijkstraTotal, astarTotal, jpsTotal));
  }

  private double runScenariosWith(Pathfinder algorithm, String algorithmName, int iterations) {
    double total = 0;
    for (int i = 0; i < scenarios.length; i++) {
      double result = runScenarioWith(algorithm, scenarios[i], iterations);
      total += result;
      io.print("Benchmarking " + algorithmName + "... [" + i + "/" + scenarios.length + "]");
    }
    return total;
  }

  private double runScenarioWith(Pathfinder algorithm, Scenario scenario, int iterations) {
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
