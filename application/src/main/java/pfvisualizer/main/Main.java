package pfvisualizer.main;

import java.io.IOException;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import pfvisualizer.algorithms.AStar;
import pfvisualizer.algorithms.Dijkstra;
import pfvisualizer.algorithms.Pathfinder;
import pfvisualizer.util.MapFileParser;
import pfvisualizer.util.Node;
import pfvisualizer.util.Result;


/**
 * The main program.
 */
public class Main {

  /**
   * The entry point into the application.
   *
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    Options options = new Options();
    options.addOption(Option.builder("a")
        .desc("pathfinding algorithm to test\n (a for A* and d for Dijkstra)")
        .longOpt("algorithm")
        .hasArg()
        .argName("name")
        .required()
        .build());
    options.addOption(Option.builder("m")
        .desc("map file path")
        .longOpt("map")
        .hasArg()
        .argName("file-name")
        .required()
        .build()
    );
    options.addOption(Option.builder("c")
        .desc("start and end coordinates")
        .longOpt("coords")
        .numberOfArgs(4)
        .valueSeparator(' ')
        .type(int[].class)
        .argName("x1 y1 x2 y2")
        .required()
        .build());

    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();
    CommandLine cmd;

    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      System.out.println(e.getMessage());
      formatter.printHelp("pathfinder-visualizer", options);
      return;
    }

    String algorithmName = cmd.getOptionValue("algorithm");
    Pathfinder algorithm;
    if (algorithmName.equals("d")) {
      algorithm = new Dijkstra();
    } else if (algorithmName.equals("a")) {
      algorithm = new AStar();
    } else {
      System.out.println("Invalid algorithm name. Valid options are: a d");
      formatter.printHelp("pathfinder-visualizer", options);
      return;
    }

    String mapFile = cmd.getOptionValue("map");
    int[][] map;
    try {
      map = MapFileParser.parse(mapFile);
    } catch (IOException e) {
      System.out.println(e.getMessage()); //TODO: show a better error message
      formatter.printHelp("pathfinder-visualizer", options);
      return;
    }

    String[] coords = cmd.getOptionValues("coords");

    int startCol;
    int startRow;
    int endCol;
    int endRow;
    try {
      startCol = Integer.parseInt(coords[0].trim());
      startRow = Integer.parseInt(coords[1].trim());
      endCol = Integer.parseInt(coords[2].trim());
      endRow = Integer.parseInt(coords[3].trim());
    } catch (NumberFormatException e) {
      System.out.println("Error: expected all coordinates to be integers");
      formatter.printHelp("pathfinder-visualizer", options);
      return;
    }

    Result result = algorithm.search(map, startCol, startRow, endCol, endRow);
    if (result == null) {
      System.out.println("Path was not found");
    } else {
      for (int row = 0; row < result.getMap().length; row++) {
        for (int col = 0; col < result.getMap()[0].length; col++) {
          System.out.print(result.getMap()[row][col]);
        }
        System.out.println();
      }
      System.out.println("\nlength: " + result.getDistance());
    }
  }
}
