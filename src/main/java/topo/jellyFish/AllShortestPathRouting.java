package topo.jellyFish;

import org.apache.logging.log4j.LogManager;
import topo.routing.RoutingAlgorithm;
import topo.routing.RoutingPath;
import topo.Topology;

import java.util.*;

/*
    author tamolo
    date 5/4/18
*/
public class AllShortestPathRouting extends RoutingAlgorithm {

    Topology graph;
    List<Integer> listErrorSwitch;
    public Map<Integer, Map<Integer, List<RoutingPath>>> routingTable;

    public AllShortestPathRouting(Topology graph) {
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

    public AllShortestPathRouting(Topology graph, List<Integer> listErrorSwitch) {
        this.graph = graph;
        routingTable = new HashMap<>();
        this.listErrorSwitch = listErrorSwitch;

        for(int source : graph.hosts()) {
            Map<Integer, List<RoutingPath>>  allPathPerVertex = new HashMap<>();
            for(int des : graph.hosts()) {
                if(source == des) continue;
                else {
                    List<List<Integer>> allPaths = graph.allShortestPath(source, des);

                    //all invalid node will be set to -1
                    for(List<Integer> path : allPaths) {
                        for(int node : path) {
                            if (listErrorSwitch.contains(node)) {
                                int index = path.indexOf(node);
                                path.set(index, -1);
                            }
                        }
                    }
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
