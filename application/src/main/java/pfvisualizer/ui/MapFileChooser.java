package pfvisualizer.ui;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MapFileChooser {
  private final ExtensionFilter mapFilter = new ExtensionFilter("Map files (*.map)", "*.map");
  private final FileChooser mapFileChooser;

  public MapFileChooser() {
    mapFileChooser = new FileChooser();
    mapFileChooser.getExtensionFilters().add(mapFilter);
  }

  public File showOpenMapDialog(Stage primaryStage) {
    return mapFileChooser.showOpenDialog(primaryStage);
  }
}
