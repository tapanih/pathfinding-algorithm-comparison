## Week 2

Work time: 12 hours

This week I implemented Dijkstra's algorithm and also wrote unit tests for it.
After fixing some annoying bugs surfaced by the unit tests, the algorithm seems to work in small maps. Most of the time used went into figuring out how to handle neighbors on a grid. Especially if some kind of pre-processing would make the algorithm faster. I ended up doing no pre-processing. When looking into implementing A* algorithm, I opted to use a heuristic function of h(x) = 0 for Dijkstra. This decision made implementing A* algorithm require less copy-pasting. Copy-paste code is something I want to avoid this early in development. I had to make some changes to my initial Dijkstra implementation to make A* algorithm work by simply overriding the heuristic function. This change created some extra bugs to fix.

Code coverage report and style checking were already done last week. However, I did add style checking to GitHub Actions and changed Checkstyle warnings into build errors.

This week I learned that interfaces can contain variables that are implicitly 
public, static and final. That was handy for hard coding the diagonal distance of sqrt(2) and making it available to all path-finding algorithms.

Next week I am going to look into implementing Jump Point Search and hopefully
visualizing the algorithms to see the difference in nodes visited.
Something I am unsure about the licensing on Moving AI Labs map files and especially whether or not redistribution is permitted. Including some of the maps to the repository would make automated testing easier.