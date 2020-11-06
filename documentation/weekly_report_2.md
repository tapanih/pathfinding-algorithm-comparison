## Week 2

Work time: 12 hours

This week I implemented Dijkstra's algorithm. Most of the time used went into figuring 
out how to handle neighbors on a grid. I opted to use a heuristic function of h(x) = 0 to 
make implementing A* algorithm straight-forward while avoiding copy-pasting code.

Implementing A* algorithm proved more difficult than expected due to some annoying 
bugs that surfaced while writing unit tests.

Code coverage and style checking were already done last week. However, I did
add style checking to GitHub Actions and changed Checkstyle warnings into build errors.

This week I learned that interfaces can contain variables that are implicitly 
public, static and final. That was handy for hard coding the diagonal distance of
sqrt(2) and making it available to all path-finding algorithms.

Next week I am going to look into implementing Jump Point Search and hopefully
visualizing the algorithms to see the difference in nodes visited.
Something I am unsure about the licensing on Moving AI Labs map files and especially
whether or not redistribution is permitted. Including some of the maps to the repository
would make automated testing easier.