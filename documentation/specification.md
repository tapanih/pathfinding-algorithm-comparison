# Project specification

Alkuhuomautus: Olen tietojenkäsittelytieteen kandiohjelmassa (TKT).

## Overview of the program

The program compares and visualizes path-finding algorithms on 2D grid maps from [Moving AI Labs](https://movingai.com/benchmarks/grids.html). Additionally, the scenario files will be used for testing. The scenarios assume that the maps are 8-connected with diagonal movement cost of sqrt(2). In the simplest version of the program, the name of the algorithm and the file name of a map are provided as command line arguments. Based on the arguments given, the program shows the runtime of the selected algorithm along with an image of the map where the optimal path and visited nodes are colorized. Graphical UI, side-by-side comparisons and animations may be added if time permits. Code, comments and documentation will be written in English.

## Algorithms

### Dijkstra's algorithm

Since we are operating on an 8-connected grid maps, Dijkstra's algorithm is the natural choice for handling the different cost of diagonal movement. This algorithm also sets a baseline that can be compared against more sophisticated path-finding algorithms. The time complexity of the algorithm is O(n log n) where n is the number of squares. The amount of edges is approximately 8n which simplifies the complexity from the usual O(n + m log n).

The algorithm requires a priority queue which can be implemented in various ways but binary heap and fibonacci heap are the most promising options. Both may be implemented if time permits but first version will use a binary heap as it is simpler to code. The priority queue needs to implement insert, extract-min and decrease-key operations. The time complexities of these operations are shown in the table below:

| Operation      | insert   | extract-min | decrease-key |
|----------------|----------|-------------|--------------|
| Binary heap    | O(log n) | O(log n)    | O(log n)     |
| Fibonacci heap | O(1)     | O(1)*       | O(log n)*    |

\* = amortized time complexity

The algorithm needs a hash map with insert and find-key operations to store visited squares. The amortized time complexity of these operations should be O(1). Collisions will probably be handled by making every bucket a linked list.

### A* Algorithm

A* algorithm is an optimization of Dijkstra's. The time and space complexity should similarly be O(n log n) but with larger constants due to the added heuristic function. The heuristic function should take into account that only horizontal, vertical and diagonal movement is permitted.

Like Dijkstra's algorithm, A*  also requires a priority queue and a hash map.

### Jump point search (JPS)

Jump point search is an optimization of A* which avoids processing unnecessary cases by making long jumps along vertical or horizontal lines.

### References

https://movingai.com/benchmarks/grids.html

https://movingai.com/benchmarks/formats.html

https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm

https://www.cs.au.dk/~gerth/advising/thesis/anders-strand-holm-vinther_magnus-strand-holm-vinther.pdf

https://en.wikipedia.org/wiki/A*_search_algorithm

https://en.wikipedia.org/wiki/Jump_point_search

http://users.cecs.anu.edu.au/~dharabor/data/papers/harabor-grastien-icaps14.pdf