# Ex1_OOP

This repository contains 3 interfaces representing an undirected and weighted graph, and 2 classes implementing those interfaces. 

## WGraph_DS Class:

This class implements a simple methods applicable on an undirected and weighted graph, which also contains a private class-  
NodeInfo class - a class that implements a vertex in the graph and contains 3 fields: String info, double tag and int key.  
**A brief explanation on the NodeInfo fields-**    
**info-** for meta data.  
**tag-** useful for the WGraph_Algo class implementing methods.  
**key-** the key helps identify the vertex in the graph, each vertex has a unique key.  

The WGraph_DS class is mainly for creating the graph and applying simple methods on this graph. Such as- adding a vertex (node), connecting between two 
vertices, removing a vertex, getting the collection of the vertices in the graph and more. Each method has a brief explanation of it's purpose and 
the variables it recieves and returns above the function. 

Here's an example of using some simple methods:
```bash
// Creating an object of WGraph_DS
WGraph_DS graph=new WGraph_DS();

// Adding vertices to the graph
// Adding a vertex with the key 1, the other fields of the vertex are initialized.
graph.addNode(1);
// Adding a vertex with the key 2
graph.addNode(2);
// Adding a vertex with the key 3
graph.addNode(3);

// Connecting the vertices with each other
// Connecting the vertices with the keys 1,3. The edge weight is 2.5. 
graph.connect(1,2,2.5);
// Connecting the vertices with the keys 1,2. The edge weight is 8.9. 
graph.connect(2,3,8.9);


```

And now the graph object looks like this:
![graph](https://user-images.githubusercontent.com/74153075/99913780-32c6b580-2d02-11eb-8e30-33c426ca8f0e.PNG)

You can similarly use the other functions: 
```bash
// getV returns a collection of all the vertices in the graph
Collection<node_info> c = graph.getV();
// checking if the collection contains the node with the key 1 from the object- graph.
System.out.println(c.contains(graph.getNode(1));

// getV(node_id) returns a collection of all the nodes neighboring to the node with the key node_id
Collection<node_info> Ni = graph.getV(1);
// checking if the collection contains the node with the key 2 from the object- graph.
System.out.println(Ni.contains(graph.getNode(2));
// checking if the object graph has an edge between 2 nodes with the keys 1 and 2
System.out.println(graph.hasEdge(1,2));
// checking if the list of the neighbors of the node with the key 1 in the graph contains a node with the key 3
System.out.println(Ni.contains(graph.getNode(3));
// checking if the object graph has an edge between 2 nodes with the keys 1 and 3
System.out.println(graph.hasEdge(1,3));
// getting the edge value between the nodes with the keys 1,2
System.out.println(graph.hasEdge(1,2));


output: true
        true
        true
        false
        false
        2.5
```
You can also remove a node from the graph, resulting in removing all the edges with this node. Example:
```bash
// printing the amount of vertices in the graph
System.out.println(graph.nodeSize());

// removing the node with the key 1 from the object graph
graph.removeNode(1);

// printing the amount of vertices in the graph
System.out.println(graph.nodeSize());


output: 3
        2
```

Now the object graph will look like this:
![‏‏graph2](https://user-images.githubusercontent.com/74153075/99914281-eaf55d80-2d04-11eb-8eaa-2a611d871c83.PNG)


## WGraph_Algo class:

This class contains methods a bit more complicated then the WGraph_DS. Some of the methods are easy to understand and self explanatory, such as:
init, getGraph, copy(**deep copy**- not a pointer),save and load. We will deepen in those methods.

Let's say we create an identical graph as before: 
![graph](https://user-images.githubusercontent.com/74153075/99913780-32c6b580-2d02-11eb-8e30-33c426ca8f0e.PNG)

If we use the method shortestPathDist, on the nodes with the keys 1 and 3, the function will return the double value of 11.4
since the shortest distance from node 1 to node 3 is traveling through the node 2 and onwards to node 3.
Using in a code:
```bash
// creating a new instance of WGraph_Algo
WGraph_Algo graph_algo=new WGraph_Algo();

// initializing the instace of WGraph_Algo using the object graph
graph_algo.init(graph);

// printing the shortest distance between the nodes with the key 1 and 3
System.out.println(graph_algo.shortestPathDist(1,3));

output: 11.4

```
The method shortestPath(int src,int dest) returns a list containing all the nodes that occur on the shortest path from the nodes src to dest:
Let's assume the shortest path from the node src and the node path contains 3 other nodes, those nodes will be marked as node1,node2,node3.
So the list of nodes that will be returned is: src-> node1-> node2-> node3-> dest (assuming that this is the nodes order of appearance from src to dest).
Code example:
```bash
// putting the list of the nodes appearing on the shortest 
// path from the node with the key 1 to the node with the key 3 in a list
List<node_info> ls=graph_algo.shortestPath(1,2);

// printing the list size
System.out.println(ls.size());

// printing if the list contains the node with the key value of 3 from the graph
System.out.println(ls.contains(graph.getNode(3)));

// printing if the list contains the node with the key value of 1 from the graph
System.out.println(ls.contains(graph.getNode(1)));

// printing if the list contains the node with the key value of 2 from the graph
System.out.println(ls.contains(graph.getNode(2)));

output: 2
        false
        true 
        true

```
Because in out example we called the function with the key values of 1,2. The list the functioned returned contains only the nodes 1,2.
That was a brief explanation of the repo, the classes and the more complicated methods, for more information or technical support you may 
contact us through the mail - itay.aharoni1@msmail.ariel.ac.il.
