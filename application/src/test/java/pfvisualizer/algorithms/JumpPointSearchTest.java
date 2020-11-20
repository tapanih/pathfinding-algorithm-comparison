package pfvisualizer.algorithms;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import pfvisualizer.util.Result;

public class JumpPointSearchTest {
  float delta = 0.001f;

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
    JumpPointSearch jps = new JumpPointSearch();
    Result result = jps.search(map, 0, 8, 6, 4);
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
   JumpPointSearch jps = new JumpPointSearch();
    Result result = jps.search(map, 1, 1, 4, 3);
    assertEquals(11.8284f, result.getDistance(), delta);
  }
}
