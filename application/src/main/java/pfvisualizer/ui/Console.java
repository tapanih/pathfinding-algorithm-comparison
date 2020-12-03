package pfvisualizer.ui;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import pfvisualizer.benchmark.IO;

public class Console implements IO {
  private final TextArea console;

  public Console(TextArea console) {
    this.console = console;
  }

  @Override
  public void print(String text) {
    Platform.runLater(() -> console.appendText(text));
  }
}
