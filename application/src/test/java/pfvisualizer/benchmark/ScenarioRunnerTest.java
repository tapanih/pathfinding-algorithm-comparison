package pfvisualizer.benchmark;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import pfvisualizer.io.Parser;

public class ScenarioRunnerTest {
  TestIO io;

  @Before
  public void initializeTest() {
    io = new TestIO();
  }

  @Test
  public void scenarioRunnerReturnsCorrectAmountOfInformation() throws IOException {
    File file = new File("src/test/resources/scenarios/test.map.scen");
    Scenario[] scenarios = Parser.parseScenario(file);
    ScenarioRunner runner = new ScenarioRunner(scenarios, io, 10);
    BenchmarkResults results = runner.run();
    assertEquals(3, results.getSeriesList().size());
    assertEquals(4, results.getSeriesList().get(0).getData().size());
    assertEquals(4, results.getSeriesList().get(1).getData().size());
    assertEquals(4, results.getSeriesList().get(2).getData().size());
  }
}
