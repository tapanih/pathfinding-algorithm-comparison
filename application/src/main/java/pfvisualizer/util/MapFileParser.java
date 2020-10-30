package pfvisualizer.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MapFileParser {
  public static int[][] parse(String fileName) throws IOException { //TODO: handle exceptions better
    BufferedReader br = new BufferedReader(new FileReader(fileName));
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
          map[y][x] = 0;
        } else {
          map[y][x] = 1;
        }
      }
    }
    return map;
  }
}
