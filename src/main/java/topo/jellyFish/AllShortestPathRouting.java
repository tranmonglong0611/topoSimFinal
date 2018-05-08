package topo.jellyFish;

import org.apache.logging.log4j.LogManager;
import routing.RoutingAlgorithm;
import routing.RoutingPath;
import topo.Graph;

import java.util.*;

/*
    author tamolo
    date 5/4/18
*/
public class AllShortestPathRouting extends RoutingAlgorithm {

    Graph graph;
    public Map<Integer, Map<Integer, List<RoutingPath>>> routingTable;

    public AllShortestPathRouting(Graph graph) {
        this.graph = graph;
        routingTable = new HashMap<>();

        for(int source : graph.hosts()) {
            Map<Integer, List<RoutingPath>>  allPathPerVertex = new HashMap<>();
            for(int des : graph.hosts()) {
                if(source == des) continue;
                else {
                    List<List<Integer>> allPaths = graph.allShortestPath(source, des);
                    List<RoutingPath> routingPaths = new ArrayList<>();
                    for(List<Integer> path  : allPaths) {
                        routingPaths.add(new RoutingPath(path));
                    }
                    allPathPerVertex.put(des, routingPaths);
                }
                routingTable.put(source, allPathPerVertex);
            }
        }
    }

    public RoutingPath getRandomPath(int source, int des) {
        if(graph.isSwitchVertex(source) || graph.isSwitchVertex(des)) {
            LogManager.getLogger(K_ShortestPathRouting.class.getName()).error("Can not get path from switch vertex");
            return null;
        }
        List<RoutingPath> listPath = routingTable.get(source).get(des);

        Random random = new Random(80);
        int randomIndex = random.nextInt(listPath.size());
        return listPath.get(randomIndex);
    }

    @Override
    public int next(int source, int current, int destination) {
        return 0;
    }

    @Override
    public RoutingPath path(int source, int destination) {
        return routingTable.get(source).get(destination).get(0);
    }
}
