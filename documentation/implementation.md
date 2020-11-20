## Implementation

Project contains three path-finding algorithms: Dijkstra, A* and Jump Point Search.

## Time and space complexities

Dijkstra's algorithm implemented with a binary heap runs in time O((|E|+|V|)log|V|). In this application, the number of edges has an upper bound of 8 so the time complexity can equivalently be expressed as O(|V|log|V|). The other algorithms are based on Dijkstra's so they have the same time complexity.

The algorithms store data that is bounded by the number of vertices so the space complexity is O(|V|).

## Performance analysis

In progress.

## Sources

https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm