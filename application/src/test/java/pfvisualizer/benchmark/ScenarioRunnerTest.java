package pfvisualizer.benchmark;

import static org.junit.Assert.assertTrue;

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
  public void scenarioRunnerFinishesSuccessfully() throws IOException {
    File file = new File("src/test/resources/scenarios/test.map.scen");
    Scenario[] scenarios = Parser.parseScenario(file);
    ScenarioRunner runner = new ScenarioRunner(scenarios, io);
    runner.run(10);
    assertTrue(io.contains("Total median times:"));
  }
}
