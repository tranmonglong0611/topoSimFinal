package routing;

import topo.Graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortestRoutingAlgorithm extends RoutingAlgorithm {

    private Graph graph;

    //example <1, <5, 3>> de di tu 1 ->5 thi tiep theo phai di den 3
    private Map<Integer, Map<Integer, Integer>> paths;

    public ShortestRoutingAlgorithm(Graph graph) {
        paths = new HashMap<>();
        for (int sw : graph.switches()) {
            paths.put(sw, new HashMap<>());
        }
    }


    @Override
    public int next(int source, int current, int destination) {

        if (paths.get(current).containsKey(destination)) {
            return paths.get(current).get(destination);
        }

        List<Integer> shortestPath = graph.shortestPath(current, destination);

        //precompute some paths
        for (int i = 0; i < shortestPath.size(); i++) {
            paths.get(shortestPath.get(i)).put(destination, shortestPath.get(i + 1));
        }

        return shortestPath.get(1);
    }

    @Override
    public RoutingPath path(int source, int destination) {

        RoutingPath rp = new RoutingPath();

        int current = source;
        while (current != destination) {
            rp.path.add(current);
            current = next(source, current, destination);
            if (current == -1) return null;
        }

        rp.path.add(destination);

        return rp;
    }
}
