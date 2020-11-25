package pfvisualizer.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * A class for parsing map files that are using the Moving AI Labs format:
 *     https://movingai.com/benchmarks/formats.html.
 */
public class MapFileParser {

  /**
   * Parses the map file given as the argument.
   *
   * @param file The map file
   * @return the map as a 2d int array where 0 indicates
   *     the square is passable and 1 means it is not
   */
  public static int[][] parse(File file) throws IOException { //TODO: handle exceptions better
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
        if (st.charAt(x) == '.') {
          map[y][x] = 0; // passable
        } else {
          map[y][x] = 1; // wall
        }
      }
    }
    return map;
  }
}
