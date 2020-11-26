package pfvisualizer.ui;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FileChoosers {
  private final ExtensionFilter mapFilter = new ExtensionFilter("Map files (*.map)", "*.map");
  private final ExtensionFilter scenarioFilter = new ExtensionFilter(
      "Scenario files (*.scen)", "*.scen"
  );
  private final FileChooser mapFileChooser;
  private final FileChooser scenarioFileChooser;

  /**
   * Wrapper class for file choosers that are responsible for opening map and scenario files.
   */
  public FileChoosers() {
    mapFileChooser = new FileChooser();
    scenarioFileChooser = new FileChooser();
    mapFileChooser.getExtensionFilters().add(mapFilter);
    scenarioFileChooser.getExtensionFilters().add(scenarioFilter);
  }

  public File showOpenMapDialog(Stage primaryStage) {
    return mapFileChooser.showOpenDialog(primaryStage);
  }

  public File showOpenScenarioDialog(Stage primaryStage) {
    return scenarioFileChooser.showOpenDialog(primaryStage);
  }
}
