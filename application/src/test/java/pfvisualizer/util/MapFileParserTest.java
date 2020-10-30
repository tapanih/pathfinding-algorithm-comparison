package pfvisualizer.util;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;
import org.junit.Test;

public class MapFileParserTest {

  @Test
  public void mapIsParsedCorrectly() throws IOException {
    File mapFile = new File("src/test/resources/maps/test.map");
    int[][] map = MapFileParser.parse(mapFile.getAbsolutePath());
    int[][] mapCorrect = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 1, 1, 1, 1, 0, 0, 1},
        {1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
        {1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
        {1, 0, 0, 1, 1, 0, 1, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    assertArrayEquals(map, mapCorrect);
  }
}
