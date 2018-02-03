package topo;

import event.Event;
import event.EventSim;
import network.Network;
import network.Packet;

import java.util.Map;

public class Experiment {

    EventSim sim;

    public Experiment(EventSim sim) {
        this.sim = sim;
    }

    public long averagePacketTravel() {

        sim.out.append("Total Time Travel " + sim.totalTimePacketTravel);
        sim.out.append("Total Packet Sent: " + sim.numSent);

        long averageTime = sim.totalTimePacketTravel / sim.numSent;
        return averageTime;
    }

}
