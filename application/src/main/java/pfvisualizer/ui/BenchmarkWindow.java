package pfvisualizer.ui;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pfvisualizer.benchmark.BenchmarkResults;
import pfvisualizer.benchmark.Scenario;
import pfvisualizer.benchmark.ScenarioRunner;

public class BenchmarkWindow extends Stage {
  public static final int WINDOW_WIDTH = 800;
  public static final int WINDOW_HEIGHT = 600;

  private static final int ITERATIONS = 10;

  private final BorderPane root = new BorderPane();
  private final Text text = new Text();
  private final Console console = new Console(text);

  /**
   * A window for showing benchmark results.
   *
   * @param scenarios an array of scenarios for benchmarking
   */
  public BenchmarkWindow(Scenario[] scenarios) {
    Button button = new Button();
    button.setText("Benchmark!");
    button.setOnAction(event -> startBenchmark(scenarios));
    root.setCenter(button);
    this.setTitle("Benchmark");
    this.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
  }

  private void startBenchmark(Scenario[] scenarios) {
    root.setCenter(text); // hide the button
    BenchmarkTask task = new BenchmarkTask(new ScenarioRunner(scenarios, console, ITERATIONS));
    Thread thread = new Thread(task);
    thread.setDaemon(true); // close this thread when application closes
    thread.start();
    task.setOnSucceeded(event -> showResults(task.getValue()));
  }

  private void showResults(BenchmarkResults results) {
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("optimal path length");
    yAxis.setLabel("time (ms)");
    final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
    lineChart.getData().addAll(results.getSeriesList());
    lineChart.setTitle(
        String.format("Total median times: Dijkstra %.2f ms, A* %.2f ms, JPS %.2f ms",
        results.getTotals().get(0), results.getTotals().get(1), results.getTotals().get(2))
    );
    root.setCenter(lineChart);
  }
}
