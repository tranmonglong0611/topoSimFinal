package routing;

import org.junit.jupiter.api.Test;
import topo.fatTree.FatTreeGraph;
import topo.jellyFish.K_SortestPathRouting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class K_SortestPathRoutingTest {

    @Test
    void kspTest() {

        FatTreeGraph a = new FatTreeGraph(4);
        K_SortestPathRouting al = new K_SortestPathRouting(a, 4);

        List<RoutingPath> result = al.ksp(0, 8, 4);

        List<List<Integer>> realResult = new ArrayList<>();
        List<Integer> p1 = new ArrayList<Integer>() {{add(0);add(4);add(6);add(32);add(14);add(12);add(8);}};
        realResult.add(p1);
        List<Integer> p2 = new ArrayList<Integer>() {{ add(0);add(4);add(7);add(34);add(15);add(12);add(8);}};
        realResult.add(p2);

        List<Integer> p3 = new ArrayList<Integer>() {{ add(0);add(4);add(6);add(33);add(14);add(12);add(8);}};
        realResult.add(p3);
        List<Integer> p4 = new ArrayList<Integer>() {{ add(0);add(4);add(7);add(35);add(15);add(12);add(8);}};
        realResult.add(p4);

        for(int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
    }

    @Test
    void routingTableTest() {
        FatTreeGraph ftGraph = new FatTreeGraph(4);
        K_SortestPathRouting routing = new K_SortestPathRouting(ftGraph, 2);

        for(int host : ftGraph.hosts()) {
            Map<Integer, List<RoutingPath>> a = routing.routingTable.get(host);
            for(int key: a.keySet()) {
                System.out.println(a.get(key));
            }
        }
    }
}