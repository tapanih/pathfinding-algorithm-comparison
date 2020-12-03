package pfvisualizer.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import pfvisualizer.algorithms.Pathfinder;
import pfvisualizer.benchmark.Scenario;

/**
 * A class for parsing map and scenario files that are using the Moving AI Labs format:
 *     https://movingai.com/benchmarks/formats.html.
 */
public class Parser {

  /**
   * Parses the map file given as the argument.
   *
   * @param file The map file
   * @return the map as a 2d int array where 0 indicates
   *     the square is passable and 1 means it is not
   */
  public static int[][] parseMap(File file) throws IOException { //TODO: handle exceptions better
    BufferedReader br = new BufferedReader(new FileReader(file));
    br.readLine(); // type octile
    String[] parts = br.readLine().split(" "); // height y
    int height = Integer.parseInt(parts[1]);
    parts = br.readLine().split(" "); // width x
    int width = Integer.parseInt(parts[1]);
    br.readLine(); // map
    int[][] map = new int[height][width];
    for (int y = 0; y < height; y++) {
      String st = br.readLine();
      for (int x = 0; x < width; x++) {
        char c = st.charAt(x);
        if (c == '.' || c == 'G') {
          map[y][x] = Pathfinder.UNVISITED;
        } else if (c == 'S' || c == 'W') {
          throw new UnsupportedOperationException("Swamp and water tiles "
              + "are not currently supported");
        } else {
          map[y][x] = Pathfinder.WALL;
        }
      }
    }
    return map;
  }

  /**
   * Parses the scenario file given as the argument.
   */
  public static Scenario[] parseScenario(File file) throws IOException {
    HashMap<String, int[][]> fileNameToMap = new HashMap<>();
    List<String> lines = Files.readAllLines(file.toPath());
    lines.remove(0); // skip first line
    Scenario[] scenarios = new Scenario[lines.size()];
    int i = 0;
    for (String line : lines) {
      String[] parts = line.split("\\s+");
      String mapFileName = parts[1];

      int[][] map;
      if (fileNameToMap.containsKey(mapFileName)) {
        map = fileNameToMap.get(mapFileName);
      } else {
        // look for the map file in the same directory
        File mapFile = new File(file.toPath().getParent().toString()
            + File.separator + mapFileName);
        map = parseMap(mapFile);
        fileNameToMap.put(mapFileName, map);
      }

      int width = Integer.parseInt(parts[2]);
      int height = Integer.parseInt(parts[3]);
      if (width != map[0].length || height != map.length) {
        throw new UnsupportedOperationException("Scaling map size is not yet supported.");
      }

      int startCol = Integer.parseInt(parts[4]);
      int startRow = Integer.parseInt(parts[5]);
      int endCol = Integer.parseInt(parts[6]);
      int endRow = Integer.parseInt(parts[7]);
      float expectedDistance = Float.parseFloat(parts[8].trim());
      scenarios[i++] = new Scenario(map, startCol, startRow, endCol, endRow, expectedDistance);
    }
    return scenarios;
  }
}
