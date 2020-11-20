## Week 4

Work time: 14 hours

Jump Point Search took a lot of effort to get right so I had little time for
anything else this week. I probably did too many things at once which made debugging harder than I needed to be. For example, neighbor pruning is totally independent from actually finding jump points so either of those could have been implemented first. I did a bit of both at the same time and got nowhere.

There is some refactoring that needs to be done but had no time to do. For example, I used some built-in functions that need to be replaced. I also think I can get rid of the search method in Jump Point Search and just use the inherited one. This requires that calculating distance to previous node needs to be extracted to a method. 

I learned more about Jump Point Search and how to implement it on the given rule set. For example, there are less forced neighbors when corner cutting is not allowed and jumping has to be done one square further than normal in some cases.

I created implementation and testing documents but had little to say at this point. I manually tested the algorithms on a 512x512 map to check for correctness. They all found the optimal path instantly.

Next week I am going to start working on the visualization and begin
performance testing with larger maps. Hopefully I also have some time to do optimization based on the results and also do some refactoring on the side.