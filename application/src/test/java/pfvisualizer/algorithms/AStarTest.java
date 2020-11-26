package pfvisualizer.algorithms;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import pfvisualizer.util.Result;

public class AStarTest {
  float delta = 0.001f;
  Pathfinder algorithm;

  @Before
  public void initializeAlgorithm() {
    algorithm = new AStar();
  }

  @Test
  public void algorithmLooksForAPathInTheRightDirection() {
    int[][] grid = {
        {1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1}
    };
    int[][] mapCorrect = {
        {1, 1, 1, 1, 1, 1, 1, 1},
        {1, 4, 0, 0, 0, 0, 0, 1},
        {1, 0, 4, 0, 0, 0, 0, 1},
        {1, 0, 0, 4, 0, 0, 0, 1},
        {1, 0, 0, 0, 4, 0, 0, 1},
        {1, 0, 0, 0, 0, 4, 0, 1},
        {1, 0, 0, 0, 0, 0, 4, 1},
        {1, 1, 1, 1, 1, 1, 1, 1}
    };
    Result result = algorithm.search(grid, 1, 1, 6, 6);
    assertArrayEquals(result.getMap(), mapCorrect);
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
}
