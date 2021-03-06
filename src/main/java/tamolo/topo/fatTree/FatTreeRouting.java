package tamolo.topo.fatTree;

import javafx.util.Pair;
import tamolo.topo.routing.RoutingAlgorithm;
import tamolo.topo.routing.RoutingPath;
import tamolo.topo.Triplet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tranmonglong0611 on 29/11/2017.
 */

public class FatTreeRouting extends RoutingAlgorithm {
    private FatTreeTopology G;
    protected Map<Pair<Integer, Integer>, RoutingPath> precomputedPaths = new HashMap<>();
    protected Map<Integer, Map<Integer, Integer>> suffixTables = new HashMap<>();
    protected Map<Integer,
            Map<Triplet<Integer, Integer, Integer>, Integer>> prefixTables = new HashMap<>();
    protected Map<Integer,
            Map<Pair<Integer, Integer>, Integer>> corePrefixTables = new HashMap<>();

    public FatTreeRouting(FatTreeTopology G) {
        this.G = G;
        buildTables();

//        List<Integer> hosts = G.hosts();

//        for (int i = 0; i < hosts.size() - 1; i++) {
//            for (int j = i + 1; j < hosts.size(); j++) {
//                int source = hosts.get(i);
//                int destination = hosts.get(j);
//                path(source, destination);
//            }
//        }
//        for(Map.Entry<Pair<Integer, Integer>, RoutingPath> entry : precomputedPaths.entrySet()) {
//            Pair<Integer, Integer> key  = entry.getKey();
//            RoutingPath value = entry.getValue();
//        }
    }

    private void buildTablesWithTolerance(List<Integer> listErrorSwitch) {

    }

    private void buildTables() {
        int k = G.getK();
        int numEachPod = k * k / 4 + k;

        // edge switches
        for (int p = 0; p < k; p++) {
            int offset = numEachPod * p;
            for (int e = 0; e < k / 2; e++) {
                int edgeSwitch = offset + k * k / 4 + e;
                // create suffix table
                HashMap<Integer, Integer> suffixTable = new HashMap<>();
                for (int suffix = 2; suffix <= k / 2 + 1; suffix++) {
                    int agg = offset + k * k / 4 + (e + suffix - 2) % (k / 2) + (k / 2);
                    suffixTable.put(suffix, agg);
                }
                suffixTables.put(edgeSwitch, suffixTable);
            }
        }
        // agg switches
        for (int p = 0; p < k; p++) {
            int offset = numEachPod * p;
            for (int a = 0; a < k / 2; a++) {
                int aggSwitch = offset + k * k / 4 + k / 2 + a;

                // create suffix table
                Map<Integer, Integer> suffixTable = new HashMap<>();
                for (int suffix = 2; suffix <= k / 2 + 1; suffix++) {
                    int core = a * k / 2 + (suffix + a - 2) % (k / 2) + numEachPod * k;
                    suffixTable.put(suffix, core);
                }
                // inject to the behavior
                suffixTables.put(aggSwitch, suffixTable);

                // create prefix table
                Map<Triplet<Integer, Integer, Integer>, Integer> prefixTable
                        = new HashMap<>();
                for (int e = 0; e < k / 2; e++) {
                    int edgeSwitch = offset + k * k / 4 + e;
                    prefixTable.put(new Triplet<>(10, p, e), edgeSwitch);
                }
                prefixTables.put(aggSwitch, prefixTable);

            }
        }
        // core switches
        for (int c = 0; c < k * k / 4; c++) {
            int core = k * k * k / 4 + k * k + c;

            // build core prefix
            HashMap<Pair<Integer, Integer>, Integer> corePrefixTable =
                    new HashMap<>();
            for (int p = 0; p < k; p++) {
                int offset = numEachPod * p;
                int agg = (c / (k / 2)) + k / 2 + k * k / 4 + offset;
                corePrefixTable.put(new Pair<>(10, p), agg);
            }
            corePrefixTables.put(core, corePrefixTable);
        }
    }

    /**
     * Time complexity: O(1)
     */
    @Override
    public int next(int source, int current, int destination) {
        if (G.isHostVertex(current)) {
            return G.adj(current).get(0);
        } else if (G.adj(current).contains(destination)) {
            return destination;
        } else {
            int type = G.switchType(current);
            if (type == FatTreeTopology.CORE) {
                Address address = G.getAddress(destination);
                Pair<Integer, Integer> prefix
                        = new Pair<>(address._1, address._2);
                Map<Pair<Integer, Integer>, Integer> corePrefixTable =
                        corePrefixTables.get(current);

                return corePrefixTable.get(prefix);
            } else if (type == FatTreeTopology.AGG) {
                Address address = G.getAddress(destination);

                Triplet<Integer, Integer, Integer> prefix
                        = new Triplet<>(address._1, address._2, address._3);
                int suffix = address._4;

                Map<Triplet<Integer, Integer, Integer>, Integer> prefixTable =
                        prefixTables.get(current);
                Map<Integer, Integer> suffixTable = suffixTables.get(current);

                if (prefixTable.containsKey(prefix)) {
                    return prefixTable.get(prefix);
                } else {
                    return suffixTable.get(suffix);
                }
            } else { // Edge switch
                Address address = G.getAddress(destination);
                int suffix = address._4;
                Map<Integer, Integer> suffixTable = suffixTables.get(current);
                return suffixTable.get(suffix);
            }
        }
    }

    @Override
    public RoutingPath path(int source, int destination) {
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
