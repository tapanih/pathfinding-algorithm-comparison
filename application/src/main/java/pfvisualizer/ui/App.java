package pfvisualizer.ui;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import pfvisualizer.algorithms.AStar;
import pfvisualizer.algorithms.Dijkstra;
import pfvisualizer.algorithms.JumpPointSearch;
import pfvisualizer.algorithms.Pathfinder;
import pfvisualizer.io.Parser;
import pfvisualizer.util.Result;

public class App extends Application {
  public static final int WINDOW_WIDTH = 800;
  public static final int WINDOW_HEIGHT = 600;
  public static final int LEGEND_SIZE = 16;
  public static final int BUTTON_MIN_WIDTH = 124;

  private final HashMap<Integer, Color> intToColor = new HashMap<>();
  private final FileChoosers fileChoosers = new FileChoosers();
  private final Canvas canvas = new Canvas();
  private final BorderPane root = new BorderPane();
  private final BorderPane canvasHolder = new BorderPane();
  private final VBox menu = new VBox();
  private final Label helperLabel = new Label(" Open a map\n to get started.");
  private final Label distanceLabel = new Label("");
  private final Label timeLabel = new Label("");
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

  private void initializeNodeColors() {
    intToColor.put(Pathfinder.WALL, Color.BLACK);
    intToColor.put(Pathfinder.UNVISITED, Color.WHITE);
    intToColor.put(Pathfinder.VISITED, Color.GREEN);
    intToColor.put(Pathfinder.CHECKED, Color.LIGHTGREEN);
    intToColor.put(Pathfinder.PATH, Color.RED);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    initializeNodeColors();
    root.setLeft(menu);
    root.setCenter(canvasHolder);

    canvas.getGraphicsContext2D().setImageSmoothing(false);
    canvasHolder.setCenter(canvas);

    canvasHolder.heightProperty().addListener(event -> setCanvasScale());
    canvasHolder.widthProperty().addListener(event -> setCanvasScale());
    canvas.heightProperty().addListener(event -> setCanvasScale());
    canvas.widthProperty().addListener(event -> setCanvasScale());

    Button openFileButton = new Button();
    openFileButton.setText("Open map...");
    openFileButton.setMinWidth(BUTTON_MIN_WIDTH);
    Button openScenarioButton = new Button();
    openScenarioButton.setText("Open scenario...");
    openScenarioButton.setMinWidth(BUTTON_MIN_WIDTH);

    openFileButton.setOnAction(event -> {
      File file = fileChoosers.showOpenMapDialog(primaryStage);
      // user closed the dialog without choosing a file
      if (file == null) {
        return;
      }
      try {
        this.map = Parser.parseMap(file);
        startIsPlaced = false;
        drawMapOnCanvas(map);
        distanceLabel.setText("");
        helperLabel.setText(" Click twice\n on the map to set\n start and end\n points.");
      } catch (UnsupportedOperationException e) {
        new Alert(Alert.AlertType.ERROR, "The map contains unsupported features.").show();
      } catch (IOException e) {
        new Alert(Alert.AlertType.ERROR, "Map file could not be opened").show();
      }
    });

    openScenarioButton.setOnAction(event -> {
      File file = fileChoosers.showOpenScenarioDialog(primaryStage);
      // user closed the dialog without choosing a file
      if (file == null) {
        return;
      }
      try {
        new BenchmarkWindow(Parser.parseScenario(file)).show();
      } catch (IOException e) {
        new Alert(Alert.AlertType.ERROR, "scenario file could not be opened").show();
      }
    });

    canvas.setOnMouseClicked(event -> {
      int col = (int) event.getX();
      int row = (int) event.getY();
      placeStartOrEndNode(col, row);
    });

    final Separator separator = new Separator();
    separator.setStyle("-fx-padding: 0 0 5 0;");
    final Separator separator2 = new Separator();
    separator2.setStyle("-fx-padding: 0 0 5 0;");
    final Separator separator3 = new Separator();
    separator3.setStyle("-fx-padding: 0 0 5 0;");
    final Separator separator4 = new Separator();
    separator3.setStyle("-fx-padding: 0 0 5 0;");

    final Label legendLabel = new Label("Legend:");
    legendLabel.setStyle("-fx-padding: 5 0 2 5;");
    final GridPane legendPane = createLegend();

    final Label algorithmLabel = new Label("Algorithm:");
    algorithmLabel.setStyle("-fx-padding: 5 0 2 5;");
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

    final Label infoLabel = new Label("Info:");
    infoLabel.setStyle("-fx-padding: 5 0 2 5;");
    final Label instructionsLabel = new Label("Instructions:");
    instructionsLabel.setStyle("-fx-padding: 5 0 2 5;");

    menu.getChildren().addAll(openFileButton, openScenarioButton, algorithmLabel, separator,
        dijkstraButton, astarButton, jpsButton, legendLabel, separator2, legendPane, infoLabel,
        separator3, distanceLabel, timeLabel, instructionsLabel, separator4, helperLabel);

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    primaryStage.setTitle("Pathfinding visualizer");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void placeStartOrEndNode(int col, int row) {
    if (startIsPlaced) {
      endRow = row;
      endCol = col;
      Result result = activePathfinder.search(map, startCol, startRow, endCol, endRow);
      if (result.isPathFound()) {
        updateMapAndInfoBar(result);
        pathIsDrawn = true;
        helperLabel.setText(" Good job! You can\n set new start\n and end points \n"
            + " by clicking\n twice again.");
      } else {
        updateMapAndInfoBar(result);
        helperLabel.setText(" No path\n was found.");
      }
      startIsPlaced = false;
    } else if (map[row][col] != Pathfinder.WALL) {
      helperLabel.setText(" Click again\n to set an end\n point.");
      startRow = row;
      startCol = col;
      startIsPlaced = true;
    } else {
      helperLabel.setText(" Invalid\n start point.");
    }
  }

  private void updateMapAndInfoBar(Result result) {
    drawMapOnCanvas(result.getMap());
    if (result.isPathFound()) {
      distanceLabel.setText(String.format(" Length: %.2f", result.getDistance()));
    } else {
      distanceLabel.setText("");
    }
  }

  private GridPane createLegend() {
    final GridPane legendPane = new GridPane();

    final Rectangle wallRect = createLegendRectangle(Pathfinder.WALL);
    final Label wallLabel = new Label(" impassable");

    final Rectangle unvisitedRect = createLegendRectangle(Pathfinder.UNVISITED);
    final Label unvisitedLabel = new Label(" unvisited");

    final Rectangle visitedRect = createLegendRectangle(Pathfinder.VISITED);
    final Label visitedLabel = new Label(" visited, heap");

    final Rectangle checkedRect = createLegendRectangle(Pathfinder.CHECKED);
    final Label checkedLabel = new Label(" visited, no heap");

    final Rectangle pathRect = createLegendRectangle(Pathfinder.PATH);
    final Label pathLabel = new Label(" path");

    legendPane.add(wallRect, 0, 0);
    legendPane.add(wallLabel, 1, 0);
    legendPane.add(unvisitedRect, 0, 1);
    legendPane.add(unvisitedLabel, 1, 1);
    legendPane.add(visitedRect, 0, 2);
    legendPane.add(visitedLabel, 1, 2);
    legendPane.add(checkedRect, 0, 3);
    legendPane.add(checkedLabel, 1, 3);
    legendPane.add(pathRect, 0, 4);
    legendPane.add(pathLabel, 1, 4);
    return legendPane;
  }

  private Rectangle createLegendRectangle(int nodeType) {
    Rectangle rectangle = new Rectangle();
    rectangle.setWidth(LEGEND_SIZE);
    rectangle.setHeight(LEGEND_SIZE);
    rectangle.setFill(intToColor.get(nodeType));
    return rectangle;
  }

  private void setActivePathfinder(Pathfinder pathfinder) {
    this.activePathfinder = pathfinder;
    if (pathIsDrawn) {
      Result result = activePathfinder.search(map, startCol, startRow, endCol, endRow);
      updateMapAndInfoBar(result);
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
        if (map[row][col] == Pathfinder.UNVISITED) {
          writer.setColor(col, row, intToColor.get(Pathfinder.UNVISITED));
        } else if (map[row][col] == Pathfinder.VISITED) {
          writer.setColor(col, row, intToColor.get(Pathfinder.VISITED));
        } else if (map[row][col] == Pathfinder.WALL) {
          writer.setColor(col, row, intToColor.get(Pathfinder.WALL));
        } else if (map[row][col] == Pathfinder.CHECKED) {
          writer.setColor(col, row, intToColor.get(Pathfinder.CHECKED));
        } else if (map[row][col] == Pathfinder.PATH) {
          writer.setColor(col, row, intToColor.get(Pathfinder.PATH));
        }
      }
    }
  }
}
