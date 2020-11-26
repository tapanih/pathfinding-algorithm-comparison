package pfvisualizer.ui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pfvisualizer.util.Scenario;

public class BenchmarkWindow extends Stage {
  private final BorderPane root = new BorderPane();
  private final Scenario[] scenarios;

  /**
   * A window for showing benchmark results.
   * @param scenarios an array of scenarios for benchmarking
   */
  public BenchmarkWindow(Scenario[] scenarios) {
    this.scenarios = scenarios;
    this.setTitle("Benchmark");
    this.setScene(new Scene(root, 400, 500));

    for (Scenario scenario : scenarios) {

    }
  }
}
