## Week 5

Work time: 18 hours

This week I created a graphical user interface using JavaFX. I had set up JavaFX for our team's software engineering course mini project so configuration was quite painless. I learned that creating 25000 buttons is not a good idea so I used JavaFX Canvas to draw the map. JavafX Canvas was already familiar to me from my Software Development Methods project. I spent a bit too much time on improving the user interface but I am quite happy with the result.

Visualizing Dijkstra and A* was simple because all the visited nodes are added to the heap. This is not the case with JPS so I had to approach the visualization differently. I ended up using a different color for nodes that are visited by the jump function but are not added to the heap.Those nodes should be processed much quicker because there are no need for heap operations that have O(log n) time complexity. Also,the parent nodes are not always adjacent to their children in Jump Point Search. That means a line must be drawn between the parent node and the child node to properly show the optimal path. That was quite straight-forward to implement using Bresenham's line algorithm.

I found and fixed a dumb bug where JPS doesn't draw the correct path if start and end nodes are equal.

I also wrote a parser for scenario files. I also developed a benchmarking utility inside the main program that does benchmarks using the scenario files.
The benchmarks seem to run slowly even though they are running in a thread separate from the application thread. The first results are also slightly disappointing because Jump Point Search is not running that much faster than the other algorithms. I have read reports about 10x performance gain over A* but currently it is about two times faster.

