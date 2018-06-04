package tamolo.topo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tamolo.report.Report;
import tamolo.topo.routing.RoutingAlgorithm;

import java.util.List;

/*
    author tamolo
    date 3/21/18
    tinh toan cac tham so ly thuyet cua mot datacenter
*/
public class TheoryParam {
    Topology graph;
    RoutingAlgorithm routing;
    Logger logger = LogManager.getLogger(TheoryParam.class.getName());

//    public TheoryParam(Topology graph, RoutingAlgorithm routing) {
//        this.graph = graph;
//        this.routing = routing;
//
//        if (graph.type == "SmallWorld") {
//            System.out.println("hehe");
//            logger.info("Graph diameter: " + (this.diameter() - 2));
//            logger.info("Average length path: " + (this.averageLengthPath() - 2));
//        } else {
//            logger.info("Graph diameter: " + this.diameter());
//            logger.info("Average length path: " + this.averageLengthPath());
//        }
//    }

    public TheoryParam(Topology graph, RoutingAlgorithm routing) {

        this.graph = graph;
        this.routing = routing;
        if (graph.type == "SmallWorld") {
            Report.getTopoTheoryParam().append("\nGraph diameter: " + (this.diameter() - 2));
            Report.getTopoTheoryParam().append("\nAverage length path: " + (this.averageLengthPath() - 2));
        } else {

            Report.getTopoTheoryParam().append("\nGraph diameter: " + this.diameter());
            Report.getTopoTheoryParam().append("\nAverage length path: " + this.averageLengthPath());
        }
    }


    //xét đường đi của tất cả các cặp nút (u, v), đường đi có độ dài lớn nhất chính là đường kính của topology đó
    public int diameter() {
        int max = -1;
        List<Integer> listHost = graph.hosts();
        for (int i = 0; i < listHost.size(); i++) {
            for (int j = i + 1; j < listHost.size(); j++) {
                //plus 1 cause path do not consist source and destination
                int diameter = routing.path(listHost.get(i), listHost.get(j)).size() - 1;
                if (max < diameter) max = diameter;
            }
        }
        return max;
    }

    public double averageLengthPath() {
        double sum = 0;
        int numOfPath = 0;
        List<Integer> listHost = graph.hosts();
        for (int i = 0; i < listHost.size(); i++) {
            for (int j = i + 1; j < listHost.size(); j++) {
                numOfPath++;
                sum += routing.path(listHost.get(i), listHost.get(j)).size() - 1;
            }
        }
        return sum / numOfPath;
    }
}
