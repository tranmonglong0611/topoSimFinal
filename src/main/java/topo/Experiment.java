package topo;

import simulation.event.EventSim;
import report.Report;

public class Experiment {

//    public final Graph graph;
//    public final RoutingAlgorithm routing;
//
//    public Experiment(Graph graph, RoutingAlgorithm routing) {
//        this.graph = graph;
//        this.routing = routing;
//
//
//    }
//
//    public int diameter() {
//
//    }
    EventSim sim;

    public Experiment(EventSim sim) {
        this.sim = sim;
    }

    public long averagePacketTravel() {
        Report.getTraceFile().append("Total Time Travel " + sim.totalTimePacketTravel);
        Report.getTraceFile().append("Total Packet Sent: " + sim.numSent);

        long averageTime = sim.totalTimePacketTravel / sim.numSent;
        return averageTime;
    }



}
