package pfvisualizer.algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import pfvisualizer.algorithms.Dijkstra;
import pfvisualizer.util.Result;

public class DijkstraTest {
  float delta = 0.001f;

  @Test
  public void dijkstraReturnsNullIfPathCannotBeFound() {
    int[][] map = {
        {0, 0, 0, 0, 0},
        {0, 0, 1, 0, 0},
        {0, 1, 0, 1, 0},
        {0, 0, 1, 0, 0},
        {0, 0, 0, 0, 0}
    };
    Dijkstra dijkstra = new Dijkstra();
    Result result = dijkstra.search(map, 0, 0, 2, 2);
    assertNull(result);
  }

  @Test
  public void dijkstraFavorsDiagonalMovement() {
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
    Dijkstra dijkstra = new Dijkstra();
    Result result = dijkstra.search(map, 1, 1, 6, 6);
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
    Dijkstra dijkstra = new Dijkstra();
    Result result = dijkstra.search(map, 1, 1, 4, 3);
    assertEquals(11.8284f, result.getDistance(), delta);
  }
}
