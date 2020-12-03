package pfvisualizer.ui;

import static thorwin.math.Math.polyfit;
import static thorwin.math.Math.polynomial;

import com.sun.javafx.charts.Legend;
import java.util.Arrays;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
  private LineChart<Number, Number> lineChart;

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
    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    scene.getStylesheets().add("/css/chart.css");
    this.setScene(scene);
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

    lineChart = new LineChart<>(xAxis, yAxis);

    double[][] dijkstraData = results.getData().get(0);
    double[][] astarData = results.getData().get(1);
    double[][] jpsData = results.getData().get(2);

    addDataPoints(dijkstraData, "Dijkstra");
    addDataPoints(astarData, "A*");
    addDataPoints(jpsData, "JPS");

    addTrendline(dijkstraData[0], dijkstraData[1]);
    addTrendline(astarData[0], astarData[1]);
    addTrendline(jpsData[0], jpsData[1]);

    Legend legend = (Legend) lineChart.lookup(".chart-legend");
    legend.getItems().removeIf(item -> item.getText().equals("remove from legend"));

    lineChart.setTitle(
        String.format("Total median times: Dijkstra %.2f ms, A* %.2f ms, JPS %.2f ms",
        results.getTotals().get(0), results.getTotals().get(1), results.getTotals().get(2))
    );
    root.setCenter(lineChart);
  }

  private void addDataPoints(double[][] data, String name) {
    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    for (int i = 0; i < data[0].length; i++) {
      series.getData().add(new XYChart.Data<>(data[0][i], data[1][i]));
    }
    series.setName(name);
    lineChart.getData().add(series);
  }

  private void addTrendline(double[] xs, double[] ys) {
    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    double[] coefficients = polyfit(xs, ys, 2);
    for (double x = 0; x <= Arrays.stream(xs).max().orElseGet(() -> 0.0); x++) {
      double y = polynomial(x, coefficients);
      series.getData().add(new XYChart.Data<>(x, y));
    }
    series.setName("remove from legend");
    lineChart.getData().add(series);
  }
}
