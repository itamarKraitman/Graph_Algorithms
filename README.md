# **Exercise 2 - Graph Algorithms main.java.GUI**
>By Itamar Kraitman & Yuval Bubnovsky

Object-Oriented Programming course @ Ariel University, exercise 2

In this assignment, we were tasked with creating a main.java.GUI which will hold a directed weighted graph, run algorithms on said graph, present the results to the user and have save/load capabilities.
The interfaces were given to us and we were not allowed to alter them in any way.

Instructions regarding running the code follow this Readme.

## **Overview**
Our workflow on this exercise has been Test-Driven Development from day one, each class and function which had it's tests written before we implemented it - we believe it has benefited to our code reliability and development process.
<br> Work on this exercise began by examining the JSON files given to us by the course instructors and figuring out the data structures which we will be using.
<br> We represent our graphs using Hash Maps, where the vertices are mapped as key:Integer -> value:Node object and the edges are mapped into a nested Hash Map where the key is the edge's source vertex and the value is another Hash Map where the key is the edge's destination and it is mapped unto the edge's object as follows:<br>
```markdown
Graph Vertices: Integer(ID) -----> Node Object
Graph Edges: Integer(SRC) -----> (Integer(DEST) ----> Edge Object)
```
We chose this data structure because it allows us to have O(1) complexity in all operations on the data structure and ensures the ID's are a set (no repeating ID's)
# **Class Files**
This section will only discuss the 3 major class files in the code (Directed Weighted Graph, Directed Weighted Graph Algorithms, Ex2), it will also include an honorable mention to the Dijkstra class.
## Ex2
This is the "main" class of the code, it initialized the main.java.GUI and allows the construction of our graph objects
## Directed Weighted Graph
This class implements our data structure and represents a directed weighted graph, it includes iterators for the nodes & edges of the graph and also supports actions such as adding/removing nodes and connecting two vertices together with an edge.<br>
Each graph element is represented as an object.
## Directed Weighted Graph Algorithms
This graph uses an underlying graph object to create a new object which can support multiple algorithms and actions. The algorithms supported are:<br>
* Depth-First Search
* Dijkstra's Shortest Path
* Graph Connectivity
* Finding The Center Of The Graph
* Augmented Version Of TSP (Traveling Salesmen Problem)

## Algorithm Complexity Analysis

### Graph Connectivity O(|V|+|E|)
A given graph G=(V,E) is connected iff there exists a path from each vertex to every other vertex (in other words, it only has 1 connected component). <br>We check this by running DFS from a random vertex,making sure we visit every other vertex on the graph, then we transpose the graph's edges and run DFS from the same vertex again, if we visited all other nodes - graph is connected! further than that, since it is a directed graph, it is strongly connected.<br>
Since we go through every node and every edge twice, the complexity is O(|V| + |E|)

### Shortest Path + Shortest Distance using Dijkstra's O(|E|log|V|)
We use Dijkstra's shortest path algorithm for several purposes, first we use it to calculate the shortest distance between a vertex to all other vertices in the graph (we use this to find the center of a graph - explained later on), we also use it to determine the shortest path between two vertices and reconstruct that path - since this algorithm is so widely used in the exercise, it sits within it's own class and is an object which we create and use as needed.<br>The algorithm is implemented using a Priorty Queue and a comparator which trim the running time from the naive O(|E|+|V|) to a more comfortable O(|E|log|V|), we decided to use a Priorty Queue instead of a Fibonacci Heap because the implementation was more straight-forward.
<br> [Dijkstra's Algorithm - Wiki page][Dwiki]
### Graph Center O(|V|\*|E|log(|V|))
The center of a graph is the set of all vertices of minimum eccentricity, that is, the set of all vertices where the greatest distance to other vertices is minimal.<br>
To find such center of a graph, we use Dijkstra's algorithm on each vertex, take note of the maximum distance each vertex holds and then we return the vertex where the maximum distance is the smallest - it is the center of the graph.<br>
We run Dijkstra's algorithm |V| times, where |V| is the number of vertices in the graph, so our total running time is - O(|V|*|E|log(|V|)). If we hadn't used Priorty Queue while implementing Dijkstra's algorithm, this runtime would go up to O(|V|^3) at worst case.

### Augmented TSP O(N*|E|log(|V|))
Given a subset of vertices contained within the graph, we need to return an optimal (by weight) path containing all vertices in the subset.<br>
Unlike the original TSP, we are allowed to repeat vertices and are allowed to travel "out" of the subset to find the optimal solution.
<br>
Our approach to solving this problem is using Nearest Neighbour algorithm, which is a greedy algorithm that chooses the best closest neighbour, we modified it so if no neighbour is found - a Dijkstra iteration is performed to find a path through the entire graph to the next vertex in the subset, thus making sure as long as the subset is part of the same connected component - a solution can always be found.<br>
Where N is the number of cities, the worst case analysis has us performing N Dijkstra iterations, thus the total worst-case analysis is O(N*|E|log(|V|)).

## Algorithm Run Times

|**Vertices** |**Edges**|     **Graph Connectivity**     |     **Center**     |
|-------------|-------------|-------------------------|-------------------------- 
|   10          |    90         |          24ms               |       51ms              |                  
|    100         |    1000         |          50ms               |     186ms             |                    
|    1000         |     10000        |         111ms                |    3sec & 201ms            |                      
|    10000        |    100000       |        452ms                 |     5min       |
|    100000       |    1000000      |        7sec & 12ms            |     timeout              |

All tests for over 100K vertices have resulted in a timeout.

## Tests

All tests were written as part of the development and allowed us to make sure whenever we mark a component as finished - it passes all tests and is ready to go.
<br>This is an important lesson we take with us forward.
<br>All tests are available in the repository

## How To Download & Use
Make sure you download Ex2.jar from this repository, then navigate to the folder the JAR is in and use:
```commandline
java -jar Ex2.jar YOUR_GRAPH.json
```

where the JSON file should be formatted the same as the example files in the data folder - executing this command will boot up the main.java.GUI and load in the graph from the JSON file.
<br>Once the main.java.GUI is up & running you can use it to save/load another graph file and run all the aforementioned algorithms on said graph.

## **UML Diagram**
The UML Diagram, generated with Planet UML plugin for IntelliJ is available in the repository files.

[Dwiki]: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
