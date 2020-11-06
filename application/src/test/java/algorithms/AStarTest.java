package algorithms;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import org.junit.Test;
import pfvisualizer.algorithms.AStar;
import pfvisualizer.util.Node;

public class AStarTest {
  float delta = 0.001f;

  @Test
  public void aStarReturnsAnOptimalPath() {
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
    AStar aStar = new AStar();
    ArrayList<Node> path = aStar.search(map, 0, 8, 6, 4);
    assertEquals(26, path.size());
    assertEquals(26.24264069f, path.get(0).getHeuristic(), delta);
  }
}
