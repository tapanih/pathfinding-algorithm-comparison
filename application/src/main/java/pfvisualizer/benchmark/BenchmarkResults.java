package pfvisualizer.benchmark;

import java.util.ArrayList;
import java.util.List;

public class BenchmarkResults {
  List<double[][]> data = new ArrayList<>();
  List<Double> totals = new ArrayList<>();

  public List<double[][]> getData() {
    return data;
  }

  public List<Double> getTotals() {
    return totals;
  }
}
