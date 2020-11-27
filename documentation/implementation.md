## Implementation

The program is a JavaFX application that visualizes pathfinding algorithms and
can be used to run benchmarks on them. Inside the application, users can open
map and scenario files in Moving AI Labs file format. Opened maps are drawn on the screen and users can click twice to place start and end points. After setting the points, the program visualizes the selected path-finding algorithm by showing the path, visited and unvisited nodes.

The program supports three path-finding algorithms: Dijkstra, A* and Jump Point Search. The algorithms operates on 8-connected grid maps with a diagonal cost of sqrt(2). Also no corner cutting is allowed when moving diagonally. This makes the program compatible with Moving AI Labs benchmarks. 

## Time and space complexities

Dijkstra's algorithm implemented with a binary heap runs in time O((|E|+|V|)log|V|). In this application, the number of edges has an upper bound of 8 so the time complexity can equivalently be expressed as O(|V|log|V|). The other algorithms are based on Dijkstra's so they have the same time complexity.

The algorithms store data that is bounded by the number of vertices so the space complexity is O(|V|).

## Performance analysis

In initial testing A* algorithm runs 1.5 times faster than Dijkstra. Jump Point Search seems to run 2 times faster than A* algorithm.

## Flaws and improvements

The benchmarking utility is still in progress.

## Sources

https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm