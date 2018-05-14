package topo.jellyFish;

import javafx.util.Pair;
import routing.RoutingAlgorithm;
import topo.Topology;
import routing.RoutingPath;
import org.apache.logging.log4j.LogManager;

import java.util.*;

public class K_ShortestPathRouting extends RoutingAlgorithm {

    Topology graph;
    public Map<Integer, Map<Integer, List<RoutingPath>>> routingTable;


    public K_ShortestPathRouting(final Topology graph, final int K) {
        this.graph = graph;

        routingTable = new HashMap<>();

        for(int source : graph.hosts()) {
            Map<Integer, List<RoutingPath>> tablePath = new HashMap<>();
            for(int des : graph.hosts()) {
                if(source == des) continue;
                else {
                    List<RoutingPath> listPath = ksp(source, des, K);
                    tablePath.put(des, listPath);
                }
            }
            routingTable.put(source, tablePath);
        }
    }

    public List<RoutingPath> ksp(int source, int target, int K) {
        ArrayList<RoutingPath> result = new ArrayList<>();
        PriorityQueue<RoutingPath> candidates = new PriorityQueue<>();
        try {
            Topology cloneGraph = (Topology) graph.clone();
            RoutingPath kthPath = new RoutingPath(cloneGraph.shortestPath(source, target));
            result.add(kthPath);
            /* Iteratively compute each of the k shortest paths */
            for (int k = 1; k < K; k++) {
                // Get the (k-1)st shortest path
                RoutingPath previousPath = result.get(k - 1);
                /* Iterate over all of the nodes in the (k-1)st shortest path except for the target node; for each node,
                   (up to) one new candidate path is generated by temporarily modifying the graph and then running
                   Dijkstra's algorithm to find the shortest path between the node and the target in the modified
                   graph */
                for (int i = 0; i < previousPath.size(); i++) {
                    // Initialize a container to store the modified (removed) edges for this node/iteration
                    List<Pair<Integer, Integer>> removedEdges = new ArrayList<>();
                    // Spur node = currently visited node in the (k-1)st shortest path
                    int spurNode = previousPath.get(i);
                    // Root path = prefix portion of the (k-1)st path up to the spur node
                    RoutingPath rootPath = previousPath.cloneTo(i);
                    /* Iterate over all of the (k-1) shortest paths */
                    for (RoutingPath p : result) {
                        RoutingPath stub = p.cloneTo(i);
                        // Check to see if this path has the same prefix/root as the (k-1)st shortest path
                        if (rootPath.isSame(stub)) {
                            /* If so, eliminate the next edge in the path from the graph (later on, this forces the spur
                               node to connect the root path with an un-found suffix path) */
                            if(i < p.size() - 1) {
                                Pair<Integer, Integer> re = p.getEdge(i);
                                cloneGraph.removeEdge(re.getKey(), re.getValue());
                                removedEdges.add(re);
                            }
                        }
                    }
                    /* Temporarily remove all of the nodes in the root path, other than the spur node, from the graph */
                    for (Pair<Integer, Integer> rootPathEdge : rootPath.getEdges()) {
                        int rn = rootPathEdge.getKey();
                        if (rn != spurNode) {

                            List<Pair<Integer, Integer>> temp = cloneGraph.removeNode(rn);
//                            List<Pair<Integer, Integer>> temp2 = new ArrayList<>();
//                            for(int ii = 0; ii < temp.size() - 1; ii++) {
//                                temp2.add(new Pair(temp.get(ii), temp.get(ii+1)));
//                            }
                            removedEdges.addAll(temp);
                        }
                    }
                    // Spur path = shortest path from spur node to target node in the reduced graph
                    RoutingPath spurPath = new RoutingPath(cloneGraph.shortestPath(spurNode, target));

                    // If a new spur path was identified...
                    if (spurPath.size() != 0) {
                        // Concatenate the root and spur paths to form the new candidate path
                        RoutingPath totalPath = (RoutingPath)rootPath.clone();
                        totalPath.addPath(spurPath);
                        // If candidate path has not been generated previously, add it
                        if (!candidates.contains(totalPath))
                            candidates.add(totalPath);
                    }
                    // Restore all of the edges that were removed during this iteration
                    for(int ii = 0; ii < removedEdges.size(); ii++) {
                        cloneGraph.addEdge(removedEdges.get(ii).getKey(), removedEdges.get(ii).getValue());
                    }
                }

                /* Identify the candidate path with the shortest cost */
                boolean isNewPath;
                do {
                    kthPath = candidates.poll();
                    isNewPath = true;
                    if (kthPath != null) {
                        for (RoutingPath p : result) {
                            // Check to see if this candidate path duplicates a previously found path
                            if (p.isSame(kthPath)) {
                                isNewPath = false;
                                break;
                            }
                        }
                    }
                } while (!isNewPath);

                // If there were not any more candidates, stop
                if (kthPath == null)
                    break;
                // Add the best, non-duplicate candidate identified as the k shortest path
                result.add(kthPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    @Override
    public int next(int source, int current, int destination) {
        //dont care the source
        //todo MPTCP phan bo dong du lieu cho deu len cac node :( so fucking hard. I used random now

        if(graph.isHostVertex(current)) {
            return graph.adj(current).get(0);
        }

        List<RoutingPath> listPath = routingTable.get(source).get(destination);

        Random random = new Random(89);
        int randomInt = random.nextInt(listPath.size());

        int nextHop = listPath.get(randomInt).path.indexOf(current) + 1;
        return nextHop;
    }

    public RoutingPath getRandomPath(int source, int des) {
        if(graph.isSwitchVertex(source) || graph.isSwitchVertex(des)) {
            LogManager.getLogger(K_ShortestPathRouting.class.getName()).error("Can not get path from switch vertex");
            return null;
        }
        List<RoutingPath> listPath = routingTable.get(source).get(des);

        Random random = new Random(89);
        int randomIndex = random.nextInt(listPath.size());
        return listPath.get(randomIndex);
    }




    @Override
    public RoutingPath path(int source, int destination) {
        //return the shortest path
        return routingTable.get(source).get(destination).get(0);
    }

    public static void main(String[] args) {

    }
}
