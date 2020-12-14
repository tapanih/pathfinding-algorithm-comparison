## Implementation

The program is divided into 6 packages

* `pfvisualizer.algorithms` contains the pathfinding algorithms and the interface that they share
* `pfvisualizer.benchmark` contains classes that define scenarios, how to run scenarios  and what are included in the benchmark results
* `pfvisualizer.data` contains the data structures of which there are only the binary heap and its interface
* `pfvisualizer.io` contains the map and scenario parsers and an interface for printing results to the user
* `pfvisualizer.ui` contains the main class and UI components
* `pfvisualizer.util` contains utility classes

The program is a JavaFX application that visualizes path-finding algorithms and
can be used to run benchmarks on them. Inside the application, users can open
map and scenario files in Moving AI Labs file format. Opened maps are drawn on the screen and users can click twice to place start and end points. After setting the points, the program visualizes the selected path-finding algorithm by showing the path, visited and unvisited nodes.

The program supports three path-finding algorithms: Dijkstra, A* and Jump Point Search. The algorithms operates on 8-connected grid maps with a diagonal cost of sqrt(2). Also no corner cutting is allowed when moving diagonally. This makes the program compatible with Moving AI Labs benchmarks.

Javadocs are available [here](https://tapanih.github.io/pathfinding-visualizer/).

## Time and space complexities

Dijkstra's algorithm implemented with a binary heap runs in time O((|E|+|V|)log|V|). In this application, the number of edges has an upper bound of 8 so the time complexity can equivalently be expressed as O(|V|log|V|). The other algorithms are based on Dijkstra's so they have the same time complexity.

The algorithms store data that is bounded by the number of vertices so the space complexity is O(|V|).

## Performance analysis

Dijkstra is the slowest algorithm with the exception of maze maps where A* algorithm seems to perform worse. JPS is clearly the fastest on all tested maps. More about performance testing can be found on the [testing document](testing.md).

## Flaws and improvements

The biggest bottlenecks in algorithms have not been identified and different heuristic functions could be tested more.

## Sources

https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm