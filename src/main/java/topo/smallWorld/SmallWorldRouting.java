package topo.smallWorld;

import javafx.util.Pair;
import topo.routing.RoutingAlgorithm;
import topo.routing.RoutingPath;
import topo.routing.RoutingTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    author tamolo
    date 3/22/18
*/

/**
 * greedy routing
 */
public class SmallWorldRouting extends RoutingAlgorithm {
    private SmallWorldTopology G;
    List<Integer> listErrorSwitch;
    protected Map<Pair<Integer, Integer>, RoutingPath> precomputedPaths = new HashMap<>();

    public Map<Integer, RoutingTable> tables;

     public SmallWorldRouting(SmallWorldTopology G) {
        this.G = G;

        buildTables();
    }

    public SmallWorldRouting(SmallWorldTopology G, List<Integer> listErrorSwitch) {
         this(G);
         this.listErrorSwitch = listErrorSwitch;
    }

    private void buildTables() {
        tables = new HashMap<>();
        // Build routing table for switches
        for (int sid : G.switches()) {
            RoutingTable table = new RoutingTable();

            List<List<Integer>> neighbors = G.kHopNeighbor(sid, 3);

            for (int j : G.switches()) {
                if (sid != j) {
                    int min = G.switches().size() * 2;
                    int id = -1;
                    for (List<Integer> neighbor : neighbors) {
                        int distance = G.distance(neighbor.get(0), j) + neighbor.get(1);
                        if (distance < min) {
                            min = distance;
                            id = neighbor.get(2);
                        }
                    }
                    table.addRoute(j, id);
                } else {
                    table.addRoute(j, -1);
                }
            }
            for (int hid : G.getHostsOfSwitch(sid)) {
                table.addRoute(hid, hid);
            }
            tables.put(sid, table);
        }
    }

    @Override
    public int next(int source, int current, int destination) {

         int next;
        if (G.isHostVertex(current)) {
            next = G.adj(current).get(0);
        }
        else if (G.adj(current).contains(destination)) {
            next =  destination;
        } else {
            //get the destination switch
            int desSwitch = G.isHostVertex(destination) ? G.adj(destination).get(0) : destination;
            next = tables.get(current).getNextNode(desSwitch);
        }
        if(listErrorSwitch.contains(next)) {
            return -1;
        }else return next;
    }

    @Override
    public RoutingPath path(int source, int destination) {
         //same as fatTree
        if (precomputedPaths.containsKey(new Pair<>(source, destination))) {
            return precomputedPaths.get(new Pair<>(source, destination));
        } else {
            RoutingPath rp = new RoutingPath();
            int current = source;
            rp.path.add(current);
            while (current != destination) {
                if (current != source) {
                    rp.path.add(current);
                }
                current = next(source, current, destination);
            }

            rp.path.add(destination);
            precomputedPaths.put(new Pair<>(source, destination), rp);
            return rp;
        }
    }
}


