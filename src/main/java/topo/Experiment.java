package topo;

import event.Event;
import event.EventSim;
import network.Network;
import network.Packet;
import output.OutFile;

import java.util.Map;

public class Experiment {

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
