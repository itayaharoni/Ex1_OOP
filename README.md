# Ex1_OOP

This repository contains 3 interfaces representing an undirected and weighted graph, and 2 classes implementing those interfaces. 

## WGraph_DS Class:

This class implements a simple methods applicable on an undirected and weighted graph, which also contains a private class-
NodeInfo class - a class that implements a vertex in the graph and contains 3 fields: String info, double tag and int key.
<ins>**A brief explanation on the NodeInfo fields-**</ins>
<ins>info-</ins> for meta data.
<ins>tag-</ins> useful for the WGraph_Algo class implementing methods.
<ins>key-</ins> the key helps identify the vertex in the graph, each vertex has a unique key.

The WGraph_DS class is mainly for creating the graph and applying simple methods on this graph. Such as- adding a vertex (node), connecting between two 
vertices, removing a vertex, getting the collection of the vertices in the graph and more. Each method has a brief explanation of it's purpose and 
the variables it recieves and returns. 

Here's an example of using those said methods:
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
graph.connect(1,3,2.5);
// Connecting the vertices with the keys 1,2. The edge weight is 8.9. 
graph.connect(1,2,8.9);
```
And now the graph object looks like this:

