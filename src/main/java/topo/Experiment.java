package topo;

import event.Event;
import event.EventSim;
import network.Network;
import network.Packet;
import output.OutFile;
import routing.RoutingAlgorithm;

import java.util.Map;

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

        OutFile.getFile().append("Total Time Travel " + sim.totalTimePacketTravel);
        OutFile.getFile().append("Total Packet Sent: " + sim.numSent);

        long averageTime = sim.totalTimePacketTravel / sim.numSent;
        return averageTime;
    }
}
