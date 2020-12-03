package pfvisualizer.ui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pfvisualizer.benchmark.Scenario;
import pfvisualizer.benchmark.ScenarioRunner;



public class BenchmarkWindow extends Stage {
  private final BorderPane root = new BorderPane();
  private final TextArea textArea = new TextArea();
  private final Console console = new Console(textArea);
  ExecutorService exService = Executors.newSingleThreadExecutor();

  /**
   * A window for showing benchmark results.
   * @param scenarios an array of scenarios for benchmarking
   */
  public BenchmarkWindow(Scenario[] scenarios) {
    Button button = new Button();
    button.setText("Benchmark!");
    button.setOnAction(event -> {
      exService.submit(new BenchmarkTask(new ScenarioRunner(scenarios, console)));
      root.setTop(null);
    });
    textArea.setEditable(false);
    root.setTop(button);
    root.setCenter(textArea);
    this.setTitle("Benchmark");
    this.setScene(new Scene(root, 400, 500));
    this.setOnHiding(event -> exService.shutdown());
  }
}
