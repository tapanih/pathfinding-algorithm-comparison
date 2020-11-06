package algorithms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import org.junit.Test;
import pfvisualizer.algorithms.Dijkstra;
import pfvisualizer.util.Node;

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
    ArrayList<Node> path = dijkstra.search(map, 0, 0, 2, 2);
    assertNull(path);
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
    ArrayList<Node> path = dijkstra.search(map, 1, 1, 6, 6);
    assertEquals(6, path.size());
    assertEquals(7.07106f, path.get(0).getHeuristic(), delta);
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
    ArrayList<Node> path = dijkstra.search(map, 1, 1, 4, 3);
    assertEquals(12, path.size());
    assertEquals(11.8284f, path.get(0).getHeuristic(), delta);
  }
}
