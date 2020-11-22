package ex1.tests;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WGraph_Algo_Test {
    private WGraph_DS graph;
    private WGraph_Algo graph_algo;

    @BeforeEach
    void graph_algo_creator(){
        graph=new WGraph_DS();
        graph_algo=new WGraph_Algo();
        graph_algo.init(graph);
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.connect(1,2,3);
        graph.connect(1,3,1);
        graph.connect(1,4,10);
        graph.connect(1,5,8);
        graph.connect(5,2,18);
    }
    @Test
    @DisplayName("init and getGraph test")
    void test1(){
        assertEquals(graph,graph_algo.getGraph());
    }
    @Test
    @DisplayName("copy test")
    void test2(){
        assertEquals(graph,graph_algo.copy());
    }
    @Test
    @DisplayName("Test for the method isConnected")
    void test3(){
    assertTrue(graph_algo.isConnected());
    graph.removeEdge(1,5);
    assertTrue(graph_algo.isConnected());
    graph.removeEdge(2,5);
    assertFalse(graph_algo.isConnected());
    graph.connect(5,3,1);
    assertTrue(graph_algo.isConnected());
    }
    @Test
    @DisplayName("ShortestPathDist test")
    void test4(){
        assertEquals(3,graph_algo.shortestPathDist(1,2));
        assertEquals(1,graph_algo.shortestPathDist(1,3));
        assertEquals(8,graph_algo.shortestPathDist(1,5));
        graph.removeEdge(1,5);
        assertEquals(21,graph_algo.shortestPathDist(1,5));
        graph.removeEdge(2,5);
        assertEquals(-1,graph_algo.shortestPathDist(1,5));
    }
    @Test
    @DisplayName("ShortestPath test")
    void test5(){
        List<node_info> ls=graph_algo.shortestPath(1,5);
        assertEquals(2,ls.size());
        assertTrue(ls.contains(graph.getNode(1)));
        assertTrue(ls.contains(graph.getNode(5)));
        assertFalse(ls.contains(graph.getNode(2)));
        graph.removeEdge(1,5);
        ls=graph_algo.shortestPath(1,5);
        assertEquals(3,ls.size());
        assertTrue(ls.contains(graph.getNode(1)));
        assertTrue(ls.contains(graph.getNode(5)));
        assertTrue(ls.contains(graph.getNode(2)));
    }
    @Test
    @DisplayName("Test for methods save and load.")
    void test6(){
        graph_algo.save("graph.txt");
        WGraph_Algo graph_algo_load=new WGraph_Algo();
        graph_algo_load.load("graph.txt");
        assertEquals(graph_algo_load.getGraph(),graph_algo.getGraph());
        assertFalse(graph_algo_load.load("no_EXIST.txt"));
    }
}
