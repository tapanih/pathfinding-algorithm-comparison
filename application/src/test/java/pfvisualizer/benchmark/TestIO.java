package pfvisualizer.benchmark;

import java.util.ArrayList;
import java.util.List;
import pfvisualizer.io.IO;

public class TestIO implements IO {
  List<String> output = new ArrayList<>();

  @Override
  public void print(String text) {
    output.add(text);
  }

  public boolean contains(String text) {
    return output.stream().anyMatch(line -> line.contains(text));
  }
}
