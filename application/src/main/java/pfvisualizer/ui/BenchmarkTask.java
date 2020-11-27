package pfvisualizer.ui;

import javafx.concurrent.Task;
import pfvisualizer.util.ScenarioRunner;

public class BenchmarkTask extends Task<Void> {
  ScenarioRunner runner;

  public BenchmarkTask(ScenarioRunner runner) {
    this.runner = runner;
  }

  @Override
  protected Void call() {
    runner.run(10);
    return null;
  }
}
