package routing;

import org.junit.jupiter.api.Test;
import topo.fatTree.FatTreeGraph;
import topo.jellyFish.K_ShortestPathRouting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;


class K_ShortestPathRoutingTest {

    @Test
    void kspTest() {

        FatTreeGraph a = new FatTreeGraph(4);
        K_ShortestPathRouting al = new K_ShortestPathRouting(a, 4);

        List<RoutingPath> result = al.ksp(0, 8, 4);

        List<RoutingPath> realResult = new ArrayList<>();
        List<Integer> p1 = new ArrayList<Integer>() {{add(0);add(4);add(6);add(32);add(14);add(12);add(8);}};
        realResult.add(new RoutingPath(p1));
        List<Integer> p2 = new ArrayList<Integer>() {{ add(0);add(4);add(7);add(34);add(15);add(12);add(8);}};
        realResult.add(new RoutingPath(p2));

        List<Integer> p3 = new ArrayList<Integer>() {{ add(0);add(4);add(6);add(33);add(14);add(12);add(8);}};
        realResult.add(new RoutingPath(p3));
        List<Integer> p4 = new ArrayList<Integer>() {{ add(0);add(4);add(7);add(35);add(15);add(12);add(8);}};
        realResult.add(new RoutingPath(p4));

        for(int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
    }

    @Test
    void routingTableTest() {
        FatTreeGraph ftGraph = new FatTreeGraph(4);
        K_ShortestPathRouting routing = new K_ShortestPathRouting(ftGraph, 2);

        for(int host : ftGraph.hosts()) {
            Map<Integer, List<RoutingPath>> a = routing.routingTable.get(host);
            for(int key: a.keySet()) {
                System.out.println(a.get(key));
            }
        }
    }
}