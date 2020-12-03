package pfvisualizer.benchmark;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.chart.XYChart;

public class BenchmarkResults {
  List<XYChart.Series<Number, Number>> seriesList = new ArrayList<>();
  List<Double> totals = new ArrayList<>();

  public List<XYChart.Series<Number, Number>> getSeriesList() {
    return seriesList;
  }

  public List<Double> getTotals() {
    return totals;
  }
}
