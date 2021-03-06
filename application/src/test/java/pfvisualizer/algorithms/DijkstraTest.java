package pfvisualizer.algorithms;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import pfvisualizer.util.Result;

public class DijkstraTest {
  float delta = 0.001f;
  Pathfinder algorithm;

  @Before
  public void initializeAlgorithm() {
    algorithm = new Dijkstra();
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
  public void algorithmWorksCorrectlyWhenPathCannotBeFound() {
    int[][] map = {
        {0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0},
        {0, 1, 0, 1, 0},
        {0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0}
    };
    Result result = algorithm.search(map, 0, 0, 2, 2);
    assertFalse(result.isPathFound());
  }

  @Test
  public void algorithmFavorsDiagonalMovement() {
    int[][] map = {
        {1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 1},
        {1, 0, 0, 0, 0, 0, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1}
    };
    Result result = algorithm.search(map, 1, 1, 6, 6);
    assertEquals(7.07106f, result.getDistance(), delta);
  }

  @Test
  public void dijkstraReturnsAnOptimalPath() {
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
