package topo.spaceShuffle;

import javafx.util.Pair;
import routing.RoutingAlgorithm;
import routing.RoutingPath;

import java.util.HashMap;
import java.util.Map;

public class SpaceShuffleRouting extends RoutingAlgorithm {
    private SpaceShuffleGraph graph;
    private Map<Pair<Integer, Integer>, RoutingPath> precomputedPaths = new HashMap<>();

    public SpaceShuffleRouting(SpaceShuffleGraph graph) {
        this.graph = graph;
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
            while (current != destination) {
                if (current != source) {
                    rp.path.add(current);
                }
                current = next(source, current, destination);
            }
//            rp.path.add(destination);
            precomputedPaths.put(new Pair<>(source, destination), rp);
            return rp;
        }
    }
}

