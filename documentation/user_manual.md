## User Manual

## Running the program

Download ``pathfinding-visualizer.jar`` from [here](https://github.com/tapanih/pathfinding-visualizer/releases/download/1.0/pathfinding-visualizer.jar). The program can be run with command

```
java -jar pathfinding-visualizer.jar
```
from the folder the program was downloaded to.

### Visualizing algorithms

Open a map file with ``Open map...``

Click twice on the map to set start and end points. Make sure the start point is not inside a wall. Different algorithms can be selected from the side bar.

The colors used in the visualization are explained in the side bar. The nodes
that are visited but not added to the heap are unique to Jump Point Search.
Those nodes should be processed quicker than normal.

### Benchmarking using scenario files

Open a scenario file with ``Open scenario..`` 

The benchmarking tool expects a map file to exist in the same folder as the scenario file and the file name should be the same minus the suffix ``.scen``. For example, when scenario file is ``example.map.scen`` then the map file should be ``example.map``.

Click on ``Benchmark`` to begin benchmarking. This might take a while if the scenario and/or the map are large.

The sum of median times of 10 iterations of each line of the scenario is shown in the title of the graph. The graph itself shows how the length of the path effects the running time of the algorithm on the given map.