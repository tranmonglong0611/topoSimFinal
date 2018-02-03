package topo.SpaceShuffle;

import topo.RoutingAlgorithm;
import topo.RoutingPath;

public class SpaceShuffleRouting extends RoutingAlgorithm {
    private SpaceShuffleGraph G;

    public SpaceShuffleRouting(SpaceShuffleGraph G) {
        this.G = G;
    }

    @Override
    public int next(int source, int current, int destination) {
        if (G.isHostVertex(current)) {
            return G.adj(current).get(0);
        } if (G.adj(current).contains(destination)) {
            return destination;
        } else {
            // get next hop that minimize the distance to the destination
            double minDistance = Double.MAX_VALUE;
            int next = -1;
            for (int v : G.adj(current)) {
                if (G.distance(v, destination) < minDistance) {
                    minDistance = G.distance(v, destination);
                    next = v;
                }
            }
            return next;
        }
    }

    @Override
    public RoutingPath path(int source, int destination) {
        return null;
    }
}

