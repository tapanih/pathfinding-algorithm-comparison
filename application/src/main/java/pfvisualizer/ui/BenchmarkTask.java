package pfvisualizer.ui;

import javafx.concurrent.Task;
import pfvisualizer.benchmark.ScenarioRunner;

public class BenchmarkTask extends Task<Void> {
  private static final int ITERATION_COUNT = 10;
  private final ScenarioRunner runner;

  public BenchmarkTask(ScenarioRunner runner) {
    this.runner = runner;
  }

  @Override
  protected Void call() {
    runner.run(ITERATION_COUNT);
    return null;
  }
}
