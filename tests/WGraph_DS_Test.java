package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.*;

public class WGraph_DS_Test {
    private WGraph_DS graph;

    @BeforeEach
    void graph_creator() {
        graph = new WGraph_DS();
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);
        graph.addNode(4);
        graph.addNode(5);
        graph.addNode(6);
    }

    @Test
    @DisplayName("Simple test for connecting vertices in a weighted graph.")
    void test1() {
        graph.connect(1, 2, 110);
        graph.connect(1, 3, 4);
        graph.connect(2, 4, 2.3);
        graph.connect(4, 6, 2);
        assertTrue(graph.hasEdge(1, 2));
        assertEquals(110, graph.getEdge(1, 2));
        assertTrue(graph.hasEdge(1, 3));
        assertEquals(4, graph.getEdge(1, 3));
        assertTrue(graph.hasEdge(4, 2));
        assertEquals(2, 3, graph.getEdge(4, 2));
        assertTrue(graph.hasEdge(4, 6));
        assertEquals(2, graph.getEdge(4, 6));
        assertFalse(graph.hasEdge(3, 4));
    }

    @Test
    @DisplayName("Simple test for getting a vertex from a weighted graph.")
    void test2() {
        assertNotNull(graph.getNode(1));
        assertNotNull(graph.getNode(2));
        assertNotNull(graph.getNode(3));
        assertNull(graph.getNode(100));
        assertNull(graph.getNode(-1));
        assertNull(graph.getNode(34));

    }

    @Test
    @DisplayName("Simple test for removing and adding edges from a weighted graph.")
    void test3() {
        test1();
        assertEquals(4, graph.edgeSize());
        assertTrue(graph.hasEdge(1, 2));
        assertTrue(graph.hasEdge(2, 4));
        graph.removeEdge(1, 2);
        graph.removeEdge(4, 2);
        assertFalse(graph.hasEdge(1, 2));
        assertFalse(graph.hasEdge(2, 4));
        assertEquals(2, graph.edgeSize());
        graph.connect(4,2,-1);
        assertFalse(graph.hasEdge(2, 4));
        assertEquals(2, graph.edgeSize());
    }

    @Test
    @DisplayName("Simple test for removing node from a weighted graph.")
    void test4() {
        test1();
        assertEquals(6, graph.nodeSize());
        assertEquals(4, graph.edgeSize());
        graph.removeNode(1);
        assertEquals(5, graph.nodeSize());
        assertEquals(2, graph.edgeSize());
    }

    @Test
    @DisplayName("Test for simple get methods of the graph.")
    void test5() {
        test1();
        assertEquals(10,graph.getMC());
        assertEquals(4, graph.edgeSize());
        assertEquals(6,graph.nodeSize());
        graph.removeEdge(1,2);
        assertEquals(11,graph.getMC());
        assertEquals(3, graph.edgeSize());
        assertEquals(6,graph.nodeSize());
    }
    @Test
    @DisplayName("Test for getV methods.")
    void test6() {
        test1();
        Collection<node_info> c=graph.getV();
        assertTrue(c.contains(graph.getNode(1)));
        assertTrue(c.contains(graph.getNode(2)));
        assertTrue(c.contains(graph.getNode(3)));
        assertFalse(c.contains(graph.getNode(10)));
        assertFalse(c.contains(graph.getNode(-1)));
        c=graph.getV(1);
        assertFalse(c.contains(graph.getNode(1)));
        assertTrue(c.contains(graph.getNode(2)));
        assertTrue(c.contains(graph.getNode(3)));
        assertFalse(c.contains(graph.getNode(10)));
        assertFalse(c.contains(graph.getNode(-1)));
    }
}
