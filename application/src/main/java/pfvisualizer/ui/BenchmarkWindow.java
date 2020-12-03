package pfvisualizer.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pfvisualizer.benchmark.Scenario;
import pfvisualizer.benchmark.ScenarioRunner;

public class BenchmarkWindow extends Stage {
  private final BorderPane root = new BorderPane();
  private final Text text = new Text();
  private final Console console = new Console(text);

  /**
   * A window for showing benchmark results.
   * @param scenarios an array of scenarios for benchmarking
   */
  public BenchmarkWindow(Scenario[] scenarios) {
    Button button = new Button();
    button.setText("Benchmark!");
    button.setOnAction(event -> {
      Thread thread = new Thread(new BenchmarkTask(new ScenarioRunner(scenarios, console)));
      thread.setDaemon(true); // close this thread when application closes
      thread.start();
      root.setTop(null); // hide the button
    });
    root.setTop(button);
    root.setCenter(text);
    this.setTitle("Benchmark");
    this.setScene(new Scene(root, 400, 500));
  }
}
