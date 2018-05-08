package topo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import routing.RoutingAlgorithm;

import java.util.List;

/*
    author tamolo
    date 3/21/18
    tinh toan cac tham so ly thuyet cua mot datacenter
*/
public class TheoryParam {
    Graph graph;
    RoutingAlgorithm routing;
    Logger logger = LogManager.getLogger(TheoryParam.class.getName());

    public TheoryParam(Graph graph, RoutingAlgorithm routing) {
        this.graph = graph;
        this.routing = routing;

        logger.info("Graph diameter: " + this.diameter());
        logger.info("Average length path: " + this.averageLengthPath());
    }


    //xét đường đi của tất cả các cặp nút (u, v), đường đi có độ dài lớn nhất chính là đường kính của topology đó
    public int diameter() {
        int max = -1;
        List<Integer> listHost = graph.hosts();
        for(int i = 0; i < listHost.size(); i++) {
            for(int j = i + 1; j < listHost.size(); j++) {
                //plus 1 cause path do not consist source and destination
                int diameter = routing.path(listHost.get(i), listHost.get(j)).size() + 1;
                if(max < diameter) max = diameter;
            }
        }
        return max;
    }

    public double averageLengthPath() {
        double sum = 0;
        int numOfPath = 0;
        List<Integer> listHost = graph.hosts();
        for(int i = 0; i < listHost.size(); i++) {
            for(int j = i + 1; j < listHost.size(); j++) {
                numOfPath++;
                sum += routing.path(listHost.get(i), listHost.get(j)).size() + 1;
            }
        }
        return sum / numOfPath;
    }
}
