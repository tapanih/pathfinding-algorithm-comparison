package pfvisualizer.ui;

import javafx.application.Platform;
import javafx.scene.text.Text;
import pfvisualizer.io.IO;

public class Console implements IO {
  private final Text console;

  public Console(Text console) {
    this.console = console;
  }

  @Override
  public void print(String text) {
    Platform.runLater(() -> console.setText(text));
  }
}
