package pfvisualizer.ui;

import javafx.concurrent.Task;
import pfvisualizer.benchmark.BenchmarkResults;
import pfvisualizer.benchmark.ScenarioRunner;

public class BenchmarkTask extends Task<BenchmarkResults> {
  private final ScenarioRunner runner;

  public BenchmarkTask(ScenarioRunner runner) {
    this.runner = runner;
  }

  @Override
  protected BenchmarkResults call() {
    return runner.run();
  }
}
