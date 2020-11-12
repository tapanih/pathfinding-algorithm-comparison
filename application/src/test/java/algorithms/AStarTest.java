package algorithms;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.Test;
import pfvisualizer.algorithms.AStar;
import pfvisualizer.algorithms.Dijkstra;
import pfvisualizer.util.Node;
import pfvisualizer.util.Result;

public class AStarTest {
  float delta = 0.001f;

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
    AStar astar = new AStar();
    Result result = astar.search(grid, 1, 1, 6, 6);
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
    AStar astar = new AStar();
    Result result = astar.search(map, 0, 8, 6, 4);
    assertEquals(26.24264069f, result.getDistance(), delta);
  }
}
