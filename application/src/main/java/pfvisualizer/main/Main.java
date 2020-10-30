package pfvisualizer.main;

import org.apache.commons.cli.*;

public class Main {
  public static void main(String[] args) throws Exception {
    Options options = new Options();
    options.addOption(Option.builder("a")
        .desc("pathfinding algorithm to test")
        .longOpt("algorithm")
        .hasArg()
        .argName("name")
        .required()
        .build());
    options.addOption(Option.builder("m")
        .desc("map file path")
        .longOpt("map")
        .hasArg()
        .argName("file-name")
        .required()
        .build()
    );

    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();
    CommandLine cmd;

    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      System.out.println(e.getMessage());
      formatter.printHelp("pathfinder-visualizer", options);
      return;
    }

    String algorithm = cmd.getOptionValue("algorithm");
    String map = cmd.getOptionValue("map");

    System.out.println(algorithm);
    System.out.println(map);
  }
}
