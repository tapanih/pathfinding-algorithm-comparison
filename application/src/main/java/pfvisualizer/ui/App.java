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
import pfvisualizer.util.MapFileParser;
import pfvisualizer.util.Result;

public class App extends Application {
  public static final int WINDOW_WIDTH = 800;
  public static final int WINDOW_HEIGHT = 600;
  public static final int LEGEND_SIZE = 16;

  private final HashMap<Integer, Color> intToColor = new HashMap<>();
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
        endRow = row;
        endCol = col;
        Result result = activePathfinder.search(map, startCol, startRow, endCol, endRow);
        if (result != null) {
          drawMapOnCanvas(result.getMap());
          pathIsDrawn = true;
        }
        startIsPlaced = false;
      } else {
        startRow = row;
        startCol = col;
        startIsPlaced = true;
      }
    });

    final Separator separator = new Separator();
    final Separator separator2 = new Separator();

    final Label legendLabel = new Label("Legend:");
    final GridPane legendPane = createLegend();

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

    menu.getChildren().addAll(openFileButton, separator, dijkstraButton, astarButton, jpsButton,
        separator2, legendLabel, legendPane);

    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    primaryStage.setScene(scene);
    primaryStage.show();
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
