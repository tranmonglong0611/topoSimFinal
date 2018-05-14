package topo.spaceShuffle;

import javafx.util.Pair;
import routing.RoutingAlgorithm;
import routing.RoutingPath;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceShuffleRouting extends RoutingAlgorithm {
    private SpaceShuffleTopology graph;
    private Map<Pair<Integer, Integer>, RoutingPath> precomputedPaths = new HashMap<>();
    private List<Integer> listErrorSwitch;

    public SpaceShuffleRouting(SpaceShuffleTopology graph) {
        this.graph = graph;
    }

    public SpaceShuffleRouting(SpaceShuffleTopology graph, List<Integer> listErrorSwitch) {
        this.graph = graph;
        this.listErrorSwitch = listErrorSwitch;
    }

    @Override
    public int next(int source, int current, int destination) {
        if (graph.isHostVertex(current)) {
            return graph.adj(current).get(0);
        } if (graph.adj(current).contains(destination)) {
            return destination;
        } else {
            // get next hop that minimize the distance to the destination
            double minDistance = Double.MAX_VALUE;
            int next = -1;
            for (int v : graph.adj(current)) {
                if (graph.distance(v, destination) < minDistance) {
                    minDistance = graph.distance(v, destination);
                    next = v;
                }
            }
            if(listErrorSwitch != null && listErrorSwitch.contains(next)) {
                return -1;
            }
            return next;
        }
    }

    @Override
    public RoutingPath path(int source, int destination) {
        if (precomputedPaths.containsKey(new Pair<>(source, destination))) {
            return precomputedPaths.get(new Pair<>(source, destination));
        } else {
            RoutingPath rp = new RoutingPath();
            int current = source;
            rp.path.add(source);
            while (current != destination) {
                if (current != source) {
                    rp.path.add(current);
                }
                current = next(source, current, destination);
            }
            rp.path.add(destination);
//            rp.path.add(destination);
            precomputedPaths.put(new Pair<>(source, destination), rp);
            return rp;
        }
    }
}

