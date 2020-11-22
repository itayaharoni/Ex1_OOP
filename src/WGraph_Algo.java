package ex1.src;

import java.io.*;
import java.util.*;
/**
 * This class represents an Undirected (positive) Weighted Graph Theory algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected();
 * 3. double shortestPathDist(int src, int dest);
 * 4. List<node_data> shortestPath(int src, int dest);
 * 5. Save(file);
 * 6. Load(file);
 *
 * @author Itay.Aharoni
 *
 */
public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph graphAlgo;
    /**
     * Constructor for the class, initializes the object of the class.
     */
    public WGraph_Algo(){
        graphAlgo=new WGraph_DS();
    }
    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
    graphAlgo=g;
    }
    /**
     * Return the underlying graph of which this class works.
     * @return graphAlgo - the graph object of the class
     */
    @Override
    public weighted_graph getGraph() {
        return graphAlgo;
    }
    /**
     * Compute a deep copy of this weighted graph.
     * This method created a new graph, iterates through this graph nodes
     * and adding new nodes with identical keys to this graph's nodes.
     * Then connecting the copy graph's nodes identically as this graph.
     * @return copyGraphAlgo - a deep copy of this graph
     */
    @Override
    public weighted_graph copy() {
        WGraph_DS copyGraphAlgo=new WGraph_DS();
        Iterator<node_info> itr=graphAlgo.getV().iterator();
        while(itr.hasNext()){
            node_info vertex=itr.next();
            copyGraphAlgo.addNode(vertex.getKey());
            copyGraphAlgo.getNode(vertex.getKey()).setInfo(vertex.getInfo());
            copyGraphAlgo.getNode(vertex.getKey()).setTag(vertex.getTag());
        }
        itr=copyGraphAlgo.getV().iterator();
        while(itr.hasNext()) {
            node_info copyVertex = itr.next();
            Iterator<node_info> itrNi = graphAlgo.getV(copyVertex.getKey()).iterator();
            while (itrNi.hasNext()) {
                node_info copyVertexNi = itrNi.next();
                copyGraphAlgo.connect(copyVertex.getKey(), copyVertexNi.getKey(), graphAlgo.getEdge(copyVertex.getKey(), copyVertexNi.getKey()));
            }
        }
        copyGraphAlgo.setMC(graphAlgo.getMC());
        return copyGraphAlgo;
    }
    /**
     * Returns true if and only if (iff) there is a valid path from EVREY node to each
     * other node.
     * This method initializes the tag object of every node in the graph to -1,
     * and then adding a random node to a list. Then iterating through it's neighbors
     * adding them to the list and changing their tag field to 0, and lastly removing the first node
     * ,and repeating the cycle to every node in the list until the list is empty.
     * @return true iff the graph is connected
     */
    @Override
    public boolean isConnected() {
        if(graphAlgo==null) return true;
        if(graphAlgo.nodeSize()==0||graphAlgo.nodeSize()==1) return true;
        int vertexCount=0;
        Iterator<node_info> graphVertex=graphAlgo.getV().iterator();
        node_info vertex=null;
        while (graphVertex.hasNext()){
            vertex=graphVertex.next();
            vertex.setTag(-1);
        }
        LinkedList<node_info> queue=new LinkedList<node_info>();
        queue.addFirst(vertex);
        vertex.setTag(0);
        while(!queue.isEmpty()){
            Iterator<node_info> vertexNi=graphAlgo.getV(queue.getFirst().getKey()).iterator();
            while (vertexNi.hasNext()){
                node_info vNi=vertexNi.next();
                if(vNi.getTag()==-1){
                    queue.addLast(vNi);
                    vNi.setTag(0);
                }
            }
            if(!queue.isEmpty()) {
                queue.removeFirst();
                vertexCount++;
            }
        }
        return vertexCount==graphAlgo.nodeSize();
    }
    /**
     * returns the length of the shortest path between src to dest
     * if no such path --> returns -1
     * This method initializes every nodes tag field in the graph to -1,
     * then changing the tag field of the src node to 0 adding it to a
     * priority queue (which is prioritized by the tag value of each node in an increasing order).
     * Then iterating through the first node in the priority queue neighbors, updating the
     * neighbors tag (update if needed). Lastly removing the first node and repeating this cycle.
     * When the priority queue is empty return the tag value of the dest node.
     * @param src - start node
     * @param dest - end (target) node
     * @return the shortest length between src to dest.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if(src==dest) return 0;
        if(graphAlgo.getNode(src)==null||graphAlgo.getNode(src)==null) return -1;
        Iterator<node_info> graphVertices=graphAlgo.getV().iterator();
        node_info graphVertex=null;
        while(graphVertices.hasNext()){
            graphVertex=graphVertices.next();
            graphVertex.setTag(-1);
        }
        PriorityQueue<node_info> pQueue=new PriorityQueue<node_info>(new nodeInfoComparator());
        graphVertex=graphAlgo.getNode(src);
        pQueue.add(graphVertex);
        graphVertex.setTag(0);
        while(!pQueue.isEmpty()){
            Iterator<node_info> vertexNi=graphAlgo.getV(pQueue.peek().getKey()).iterator();
            while(vertexNi.hasNext()){
                node_info nodeNi=vertexNi.next();
                if(nodeNi.getTag()==-1){
                    nodeNi.setTag(pQueue.peek().getTag()+graphAlgo.getEdge(nodeNi.getKey(),pQueue.peek().getKey()));
                    pQueue.add(nodeNi);
                }
                else if(nodeNi.getTag()>pQueue.peek().getTag()+graphAlgo.getEdge(nodeNi.getKey(),pQueue.peek().getKey()))
                    nodeNi.setTag(pQueue.peek().getTag()+graphAlgo.getEdge(nodeNi.getKey(),pQueue.peek().getKey()));
            }
            if(!pQueue.isEmpty())
                pQueue.remove();
        }
        return graphAlgo.getNode(dest).getTag();
    }
    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * if no such path --> returns null;
     * This method calls the method shortestPathDist, then starting from the dest node
     * adding it to a list, iterating through dest node neighbors and finding the node
     * that it's tag value plus the edge value between that node and dest node equals
     * dest node tag. After finding it adding it to the list (to the first position of the list),
     * and repeating this cycle until it reaches the src node.
     * Lastly returning the list.
     * @param src - start node
     * @param dest - end (target) node
     * @return ls - the list of nodes in the shortest path between src to dest.
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if(graphAlgo.getNode(src)==null||graphAlgo.getNode(dest)==null) return null;
        LinkedList<node_info> ls=new LinkedList<node_info>();
        ls.addFirst(graphAlgo.getNode(dest));
        if(src==dest) return ls;
        shortestPathDist(src, dest);
        if(graphAlgo.getNode(dest).getTag()==-1) return null;
        while(ls.getFirst().getTag()!=0){
            Iterator<node_info> vertexNi= graphAlgo.getV(ls.getFirst().getKey()).iterator();
            while(vertexNi.hasNext()){
                node_info pathNi=vertexNi.next();
                if(ls.getFirst().getTag()==pathNi.getTag()+graphAlgo.getEdge(ls.getFirst().getKey(), pathNi.getKey())){
                    ls.addFirst(pathNi);
                    break;
                }
            }
        }
        return ls;
    }
    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream path = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(path);
            out.writeObject(graphAlgo);
            out.close();
            path.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try{
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            graphAlgo=(WGraph_DS)in.readObject();
            in.close();
            fileIn.close();
        }catch (IOException e) {
            return false;
        }
        catch (ClassNotFoundException e){
            return false;
        }
        return true;
    }
    /**
     * This class compares between two nodes - node1,node2.
     * The class compares the two nodes according to the
     * value of their tag field.
     */
    private class nodeInfoComparator implements Comparator<node_info>{
        /**
         * This method compares between two nodes
         * according to their tag value.
         * return 1 if node1 tag value is bigger then node2, -1 if it's smaller
         * and 0 if it's equal.
         * @param o1 -node1
         * @param o2 -node2
         * @return 1 if node1 bigger, -1 if node1 smaller and 0 if equal.
         */
        @Override
        public int compare(node_info o1, node_info o2) {
            if(o1.getTag()>o2.getTag())
                return 1;
            else if(o1.getTag()<o2.getTag())
                return -1;
            return 0;
        }
    }
}
