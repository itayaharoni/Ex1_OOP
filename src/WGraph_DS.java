package ex1.src;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
/**
 * This class represents an undirected weighted graph,
 * and a set of simple methods applicable on it.
 * The graph contains 5 objects;
 * 1) wGraph- HashMap that contains all of the graph vertices.
 * 2) Ni- HashMap that contains each vertex group of neighbors,
 *    can be accessed through the vertex key.
 * 3) edgeNi- HashMap that contains each vertex group of edges,
 *    can be accessed through the vertex key.
 * 4) edgeSize- the graph's number of edges.
 * 5) mcCounter- the number of changes been made in the graph.
 */
public class WGraph_DS implements weighted_graph, Serializable {
    private HashMap<Integer, node_info> wGraph;
    private HashMap<Integer, HashMap<Integer, node_info>> Ni;
    private HashMap<Integer, HashMap<Integer, Double>> edgeNi;
    private int edgeSize;
    private int mcCounter;
    /**
     * Constructor for the graph, initializes this graph object's.
     */
    public WGraph_DS() {
        wGraph = new HashMap<Integer, node_info>();
        Ni = new HashMap<Integer, HashMap<Integer, node_info>>();
        edgeNi = new HashMap<Integer, HashMap<Integer, Double>>();
        edgeSize = 0;
        mcCounter = 0;
    }
    /**
     * return the node_data by the node_id.
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        return wGraph.get(key);
    }
    /**
     * return true if and only if there is an edge between node1 and node2
     * @param node1
     * @param node2
     * @return true if and only if there is an edge between node1 and node2
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (node1 == node2) return false;
        if (wGraph.get(node1) == null || wGraph.get(node2) == null) return false;
        if (Ni.get(node1).get(node2) == null) return false;
        return true;
    }
    /**
     * return the weight if the edge (node1, node1) exists. In case
     * there is no such edge - should return -1.
     * @param node1
     * @param node2
     * @return the weight between node1 and node2.
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (node1 == node2) return -1;
        if (wGraph.get(node1) == null || wGraph.get(node2) == null) return -1;
        if (Ni.get(node1).get(node2) == null) return -1;
        return edgeNi.get(node1).get(node2);
    }
    /**
     * add a new node to the graph with the given key.
     * if there is already a node with such a key -> no action is performed.
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (wGraph.get(key) == null) {
            NodeInfo node = new NodeInfo(key);
            wGraph.put(key, node);
            HashMap<Integer, node_info> tempNi = new HashMap<Integer, node_info>();
            Ni.put(key, tempNi);
            HashMap<Integer, Double> tempEdgeNi = new HashMap<Integer, Double>();
            edgeNi.put(key, tempEdgeNi);
            mcCounter++;
        }
    }
    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     * @param node1 -key of node1
     * @param node2 -key of node2
     * @param w - the weight of the edge
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (w >= 0) {
            if (wGraph.get(node1) == null || wGraph.get(node2) == null || node1 == node2) return;
            if (!hasEdge(node1, node2)) {
                Ni.get(node1).put(node2, wGraph.get(node2));
                Ni.get(node2).put(node1, wGraph.get(node1));
                edgeSize++;
            }
            mcCounter++;
            edgeNi.get(node1).put(node2, w);
            edgeNi.get(node2).put(node1, w);
        }
    }
    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return wGraph.values();
    }
    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        return Ni.get(node_id).values();
    }
    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        if (wGraph.get(key) == null) return null;
        mcCounter++;
        if (Ni.get(key).size() == 0) return wGraph.remove(key);
        edgeSize -= edgeNi.get(key).size();
        node_info[] graphNodes = Ni.get(key).values().toArray(new node_info[0]);
        for (int i = 0; i < graphNodes.length; i++) {
            Ni.get(graphNodes[i].getKey()).remove(key);
            edgeNi.get(graphNodes[i].getKey()).remove(key);
        }
        return wGraph.remove(key);
    }
    /**
     * Delete the edge from the graph,
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (wGraph.get(node1) == null || wGraph.get(node2) == null) return;
        if (Ni.get(node1).get(node2) == null) return;
        Ni.get(node1).remove(node2);
        Ni.get(node2).remove(node1);
        edgeNi.get(node1).remove(node2);
        edgeNi.get(node2).remove(node1);
        mcCounter++;
        edgeSize--;
    }
    /** return the number of vertices (nodes) in the graph.
     * @return nodeSize- the numbers of vertices in the graph.
     */
    @Override
    public int nodeSize() {
        return wGraph.size();
    }
    /**
     * return the number of edges (undirectional graph).
     * @return edgeSize
     */
    @Override
    public int edgeSize() {
        return edgeSize;
    }
    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph causes an increment in the ModeCount
     * @return mcCounter
     */
    @Override
    public int getMC() {
        return mcCounter;
    }
    /**
     * Allows changing the mode count of the graph.
     * @param mc
     */
    public void setMC(int mc) {
        this.mcCounter = mc;
    }
    /**
     * A method that overrides java equals method.
     * This method iterates through this graph objects and
     * compare the values of each object to the other graph objects.
     * If all of the objects are equal the method return true, else false.
     * @param obj
     * @return true if and only if the this graph equals to the object graph.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof WGraph_DS))
            return false;
        WGraph_DS object = (WGraph_DS) obj;
        if (this.nodeSize() != object.nodeSize())
            return false;
        if (this.edgeSize != object.edgeSize)
            return false;
        if (this.getMC() != object.getMC())
            return false;
        Iterator<node_info> itr = this.getV().iterator();
        while (itr.hasNext()) {
            node_info temp = itr.next();
            if (object.getNode(temp.getKey()) == null)
                return false;
            Iterator<node_info> itr2 = this.getV(temp.getKey()).iterator();
            while (itr2.hasNext()) {
                node_info temp2 = itr2.next();
                if (!(object.hasEdge(temp.getKey(), temp2.getKey())))
                    return false;
                if (object.getEdge(temp.getKey(), temp2.getKey()) != this.getEdge(temp.getKey(), temp2.getKey()))
                    return false;
            }
        }
        return true;
    }
    /**
     * A method to print this graph.
     * @return s -a string represents the objects of the graph.
     */
    @Override
    public String toString() {
        String s = "";
        Iterator<node_info> itr = this.getV().iterator();
        while (itr.hasNext()) {
            node_info temp = itr.next();
            s += "Node " + temp.getKey() + " info: " + temp.getInfo() + " tag: " + temp.getTag() + "\n";
            s += "Node " + temp.getKey() + " neighbors: \n";
            Iterator<node_info> itr2 = this.getV(temp.getKey()).iterator();
            while (itr2.hasNext()) {
                node_info temp2 = itr2.next();
                s += "Neighbor key: " + temp2.getKey() + " edge: " + this.getEdge(temp.getKey(), temp2.getKey()) + "\n";
            }
            s += "================================================";
        }
        return s;
    }
    /**
     * This class represents a vertex in an undirected and weighted graph,
     * and the set of operations applicable on it.
     */
    private class NodeInfo implements node_info, Serializable {
        private int key;
        private double tag;
        private String info;
        /**
         * Constructor to the node, initializes the received key
         * as the vertex (node) key, and initializes the other objects.
         * @param key
         */
        public NodeInfo(int key) {
            this.key = key;
            info = "";
            tag = -1;
        }
        /**
         * Return the key (id) associated with this node.
         * Note: each node_data should have a unique key.
         * @return key
         */
        @Override
        public int getKey() {
            return key;
        }
        /**
         * return the remark (meta data) associated with this node.
         * @return info
         */
        @Override
        public String getInfo() {
            return info;
        }
        /**
         * Changes the remark (meta data) associated with this node.
         * @param s - the new meta data associated with this node.
         */
        @Override
        public void setInfo(String s) {
            info = s;
        }
        /**
         * Returns the temporal data.
         * @return tag
         */
        @Override
        public double getTag() {
            return tag;
        }
        /**
         * Allows setting the "tag" value for temporal marking an node - common
         * practice for marking by algorithms.
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            tag = t;
        }
        /**
         * A method that allows to print the node.
         * @return a string describing the node's content.
         */
        @Override
        public String toString() {
            return "NodeInfo{" +
                    "key=" + key +
                    ", tag=" + tag +
                    ", info='" + info + '\'' +
                    '}';
        }
    }
}
