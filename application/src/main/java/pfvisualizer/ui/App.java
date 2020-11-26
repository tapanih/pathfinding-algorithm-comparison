package pfvisualizer.ui;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pfvisualizer.algorithms.AStar;
import pfvisualizer.algorithms.Dijkstra;
import pfvisualizer.algorithms.JumpPointSearch;
import pfvisualizer.algorithms.Pathfinder;
import pfvisualizer.util.MapFileParser;
import pfvisualizer.util.Result;

public class App extends Application {
  public static final int WINDOW_WIDTH = 800;
  public static final int WINDOW_HEIGHT = 600;

  private final MapFileChooser mapFileChooser = new MapFileChooser();
  private final Canvas canvas = new Canvas();
  private final BorderPane root = new BorderPane();
  private final BorderPane canvasHolder = new BorderPane();
  private final VBox menu = new VBox();
  private final Dijkstra dijkstra = new Dijkstra();
  private final AStar astar = new AStar();
  private final JumpPointSearch jps = new JumpPointSearch();
  private Pathfinder activePathfinder = dijkstra;
  private int[][] map;
  private boolean startIsPlaced = false;
  private boolean pathIsDrawn = false;
  private int startRow;
  private int startCol;
  private int endRow;
  private int endCol;

  @Override
  public void start(Stage primaryStage) throws Exception {

    root.setLeft(menu);
    root.setCenter(canvasHolder);

    canvas.getGraphicsContext2D().setImageSmoothing(false);
    canvasHolder.setCenter(canvas);

    canvasHolder.heightProperty().addListener(event -> setCanvasScale());
    canvasHolder.widthProperty().addListener(event -> setCanvasScale());
    canvas.heightProperty().addListener(event -> setCanvasScale());
    canvas.widthProperty().addListener(event -> setCanvasScale());

    Button openFileButton = new Button();
    openFileButton.setText("Open map file...");

    openFileButton.setOnAction(e -> {
      File file = mapFileChooser.showOpenMapDialog(primaryStage);
      // user closed the dialog without choosing a file
      if (file == null) {
        return;
      }
      try {
        this.map = MapFileParser.parse(file);
        startIsPlaced = false;
        drawMapOnCanvas(map);

      } catch (IOException ioException) {
        new Alert(Alert.AlertType.ERROR, "map file could not be opened").show();
      }
    });

    canvas.setOnMouseClicked(event -> {
      int col = (int) event.getX();
      int row = (int) event.getY();

      if (startIsPlaced) {
        if (startRow != row || startCol != col) {
          endRow = row;
          endCol = col;
          Result result = activePathfinder.search(map, startCol, startRow, endCol, endRow);
          if (result != null) {
            drawMapOnCanvas(result.getMap());
            pathIsDrawn = true;
          }
        }
        startIsPlaced = false;
      } else {
        startRow = row;
        startCol = col;
        startIsPlaced = true;
      }
    });

    final ToggleButton dijkstraButton = new RadioButton();
    dijkstraButton.setText("Dijkstra");
    dijkstraButton.setSelected(true);
    dijkstraButton.setOnAction(e -> setActivePathfinder(dijkstra));
    final ToggleButton astarButton = new RadioButton();
    astarButton.setText("A*");
    astarButton.setOnAction(e -> setActivePathfinder(astar));
    final ToggleButton jpsButton = new RadioButton();
    jpsButton.setText("JPS");
    jpsButton.setOnAction(e -> setActivePathfinder(jps));

    final ToggleGroup toggleGroup = new ToggleGroup();
    toggleGroup.getToggles().addAll(dijkstraButton, astarButton, jpsButton);

    menu.getChildren().addAll(openFileButton, dijkstraButton, astarButton, jpsButton);

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void setActivePathfinder(Pathfinder pathfinder) {
    this.activePathfinder = pathfinder;
    if (pathIsDrawn) {
      Result result = activePathfinder.search(map, startCol, startRow, endCol, endRow);
      drawMapOnCanvas(result.getMap());
    }
  }

  private void setCanvasScale() {
    double scaleFactor = Math.min(canvasHolder.getHeight() / canvas.getHeight(),
        canvasHolder.getWidth() / canvas.getWidth());
    canvas.setScaleY(scaleFactor);
    canvas.setScaleX(scaleFactor);
  }

  private void drawMapOnCanvas(int[][] map) {
    int height = map.length;
    int width = map[0].length;
    canvas.setHeight(height);
    canvas.setWidth(width);
    PixelWriter writer = canvas.getGraphicsContext2D().getPixelWriter();
    canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (map[row][col] == 0) {
          writer.setColor(col, row, Color.WHITE);
        } else if (map[row][col] == 1) {
          writer.setColor(col, row, Color.BLACK);
        } else if (map[row][col] == 2) {
          writer.setColor(col, row, Color.GREEN);
        } else {
          writer.setColor(col, row, Color.RED);
        }
      }
    }
  }
}
