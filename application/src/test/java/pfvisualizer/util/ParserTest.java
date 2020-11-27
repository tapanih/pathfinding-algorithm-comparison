package pfvisualizer.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import org.junit.Test;

public class ParserTest {

  @Test
  public void mapIsParsedCorrectly() throws IOException {
    File file = new File("src/test/resources/maps/test.map");
    int[][] map = Parser.parseMap(file);
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

  @Test
  public void scenariosAreParsedCorrectly() throws IOException {
    int[][] mapCorrect = Parser.parseMap(new File("src/test/resources/scenarios/test.map"));
    Scenario[] scenariosCorrect = {
        new Scenario(mapCorrect, 2, 1, 2, 1, 0.0f),
        new Scenario(mapCorrect, 1, 1, 8, 1, 7.0f),
        new Scenario(mapCorrect, 1, 1, 1, 6, 5.0f),
        new Scenario(mapCorrect, 1, 1, 4, 3, 11.8284f)
    };

    File file = new File("src/test/resources/scenarios/test.map.scen");
    Scenario[] scenarios = Parser.parseScenario(file);
    assertArrayEquals(scenarios, scenariosCorrect);
  }
}
