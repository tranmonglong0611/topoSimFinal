package topo.fatTree;

import org.junit.jupiter.api.Test;
import topo.jellyFish.JellyFishGraph;

import static org.junit.jupiter.api.Assertions.*;

class FatTreeGraphTest {

    @Test
    void temp() {
        JellyFishGraph a = new JellyFishGraph(6, 4, 2);
        System.out.println(a.toString());
    }
    @Test
    void graphContruct() {
        FatTreeGraph fatGraph = new FatTreeGraph(4);
        assertTrue(fatGraph.hasEdge(0, 4));
        assertTrue(fatGraph.hasEdge(1, 4));
        assertTrue(fatGraph.hasEdge(8, 12));
        assertTrue(fatGraph.hasEdge(11, 13));
        assertTrue(fatGraph.hasEdge(5, 6));
        assertTrue(fatGraph.hasEdge(6, 33));
        assertTrue(fatGraph.hasEdge(30, 32));
        assertTrue(fatGraph.hasEdge(31, 35));
        assertTrue(fatGraph.hasEdge(28, 31));
    }
    @Test
    public void graphConstruction2() throws Exception {
        FatTreeGraph fatGraph = new FatTreeGraph(6);
        assertTrue(fatGraph.hasEdge(0, 9));
        assertTrue(fatGraph.hasEdge(1, 9));
        assertTrue(fatGraph.hasEdge(2, 9));
        assertTrue(fatGraph.hasEdge(24, 29));
        assertTrue(fatGraph.hasEdge(33, 40));
        assertTrue(fatGraph.hasEdge(40, 44));
        assertTrue(fatGraph.hasEdge(12, 90));
        assertTrue(fatGraph.hasEdge(28, 93));
        assertTrue(fatGraph.hasEdge(28, 95));


    }

    @Test
    void switchType() {
        FatTreeGraph graph = new FatTreeGraph(4);
        assertEquals(graph.switchType(4), FatTreeGraph.EDGE);
        assertEquals(graph.switchType(14), FatTreeGraph.AGG);
        assertEquals(graph.switchType(33), FatTreeGraph.CORE);
    }

    @Test
    void getK() {
    }

    @Test
    void getAddress() {
        FatTreeGraph fatGraph = new FatTreeGraph(4);
        assertEquals(fatGraph.getAddress(17), new Address(10, 2, 0, 3));
        assertEquals(fatGraph.getAddress(5),  new Address(10, 0, 1, 1));
        assertEquals(fatGraph.getAddress(32), new Address(10, 4, 1, 1));
        assertEquals(fatGraph.getAddress(35), new Address(10, 4, 2, 2));
    }

    @Test
    void hosts() {
    }

    @Test
    void switches() {
    }

    @Test
    void isHostVertex() {
    }

    @Test
    void isSwitchVertex1() {
        FatTreeGraph fatGraph = new FatTreeGraph(4);
        assertFalse(fatGraph.isSwitchVertex(2));
        assertFalse(fatGraph.isSwitchVertex(16));
        assertFalse(fatGraph.isSwitchVertex(19));
        assertTrue(fatGraph.isSwitchVertex(32));
    }
    @Test
    void isSwitchVertex2() {
        FatTreeGraph fatGraph = new FatTreeGraph(6);
        assertFalse(fatGraph.isSwitchVertex(37));
        assertFalse(fatGraph.isSwitchVertex(20));
        assertFalse(fatGraph.isSwitchVertex(4));
        assertTrue(fatGraph.isSwitchVertex(90));
        assertTrue(fatGraph.isSwitchVertex(1000));
        assertTrue(fatGraph.isSwitchVertex(44));
        assertTrue(fatGraph.isSwitchVertex(27));
    }

    @Test
    void switchType1() {
    }
}