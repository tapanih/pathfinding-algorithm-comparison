Week 3:

Work time: 10 hours

I thought about how to visualize the data. First I was thinking about making a JavaFX application but that seems like a lot of work for no gain as far as course grading goes. I’m thinking about outputting an image with different colors for walls, path, visited nodes and unvisited nodes. Using ImageIO should work nicely for that.

I made the program accept the algorithm name, map file, start coordinates and end coordinates can be given as arguments. Then the map and the distance of the path are printed to the console. This is obviously not a good interface because after a certain width, the map becomes unreadable. The map also looks skewed in the vertical direction because of the letter size. It is good enough for some basic testing, though.

I implemented a binary heap. First, I was thinking about making it generic for the fun of it but apparently Java doesn’t like arrays of generic elements. According to the internet, the solution is to typecast an Object array into a generic T array which outputs warnings but makes the code compile. Some guy on StackOverflow said that the collections library doesn’t compile without warnings, too? That’s when I thought Java is a stupid language and ditched using generics. So I made the heap for Node objects only.

I started working on Jump Point Search and got neighbor pruning to almost work. By that I mean the whole program explodes so I'll be continuing with that next week.