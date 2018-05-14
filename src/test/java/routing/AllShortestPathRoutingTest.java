package routing;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import topo.RandomGraph;

import java.util.ArrayList;
import java.util.List;

/*
    author tamolo
    date 5/4/18
*/
public class AllShortestPathRoutingTest {

    private RandomGraph construct() {
        RandomGraph graph = new RandomGraph(9);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 6);
        graph.addEdge(6, 7);
        graph.addEdge(7, 0);
        graph.addEdge(2, 8);
        graph.addEdge(8, 6);
        graph.addEdge(8, 7);
        graph.addEdge(1, 7);
        graph.addEdge(2, 5);
        graph.addEdge(3, 5);
        return graph;
    }

    @Test
    void routingTest1() {
        RandomGraph graph = construct();
        List<List<Integer>> temp = graph.allShortestPath(0, 4);

        List<Integer> p1 = new ArrayList<Integer>() {{add(0);add(1);add(2);add(3);add(4);}};
        List<Integer> p2 = new ArrayList<Integer>() {{add(0);add(1);add(2);add(5);add(4);}};
        List<Integer> p3 = new ArrayList<Integer>() {{add(0);add(7);add(6);add(5);add(4);}};

        assertTrue(temp.contains(p1));
        assertTrue(temp.contains(p2));
        assertTrue(temp.contains(p3));
        assertTrue(temp.size() == 3);
    }
    @Test
    void routingTest2() {
        RandomGraph graph = construct();
        List<List<Integer>> temp = graph.allShortestPath(7, 3);

        List<Integer> p1 = new ArrayList<Integer>() {{add(7);add(1);add(2);add(3);}};
        List<Integer> p2 = new ArrayList<Integer>() {{add(7);add(6);add(5);add(3);}};
        List<Integer> p3 = new ArrayList<Integer>() {{add(7);add(8);add(2);add(3);}};

        assertTrue(temp.contains(p1));
        assertTrue(temp.contains(p2));
        assertTrue(temp.contains(p3));
        assertTrue(temp.size() == 3);
    }


}
