package pfvisualizer.algorithms;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import pfvisualizer.util.Result;

public class JumpPointSearchTest {
  float delta = 0.001f;
  Pathfinder algorithm;

  @Before
  public void initializeAlgorithm() {
    algorithm = new JumpPointSearch();
  }

  @Test
  public void pathLengthIsZeroWhenStartAndEndNodesAreEqual() {
    int[][] map = {{0}};
    int[][] mapCorrect = {{4}};
    Result result = algorithm.search(map, 0, 0, 0, 0);
    assertEquals(0, result.getDistance(), delta);
    assertArrayEquals(mapCorrect, result.getMap());
  }

  @Test
  public void algorithmReturnsAnOptimalPath() {
    int[][] map = {
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 1},
        {0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
        {0, 1, 0, 1, 0, 0, 1, 1, 0, 1},
        {0, 1, 0, 1, 0, 0, 1, 0, 0, 1},
        {0, 1, 0, 1, 0, 1, 0, 0, 0, 1},
        {0, 1, 0, 0, 0, 0, 1, 0, 0, 1},
        {0, 1, 0, 0, 0, 0, 1, 1, 0, 1},
        {0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
        {0, 1, 0, 1, 0, 0, 0, 0, 0, 1}
    };
    Result result = algorithm.search(map, 0, 8, 6, 4);
    assertEquals(26.24264069f, result.getDistance(), delta);
  }


  @Test
  public void algorithmReturnsAnOptimalPath2() {
    int[][] map = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 1, 1, 1, 1, 0, 0, 1},
        {1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
        {1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
        {1, 0, 0, 1, 1, 0, 1, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    Result result = algorithm.search(map, 1, 1, 4, 3);
    assertEquals(11.8284f, result.getDistance(), delta);
  }
}
