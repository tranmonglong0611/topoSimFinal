package topo.spaceShuffle;


import common.Format;

import event.Event;
import event.EventSim;
import javafx.util.Pair;
import network.Config;
import network.Network;
import network.Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import output.OutFile;
import topo.Experiment;
import topo.TheoryParam;

import java.util.*;

public class SpaceShuffleExp {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(SpaceShuffleExp.class.getName());
        SpaceShuffleGraph ssGraph = new SpaceShuffleGraph(10, 6, 2);
        ArrayList<Integer> listErrorSwitch = new ArrayList<>();
        listErrorSwitch.add(5);
        listErrorSwitch.add(7);

        SpaceShuffleRouting ssRouting = new SpaceShuffleRouting(ssGraph, listErrorSwitch);
        ArrayList<Integer> listHost = (ArrayList<Integer>) ssGraph.hosts();

        logger.info("Done making graph");
        OutFile.getFile().append(ssGraph.toString());
        logger.info("Done write class to file");
//        TheoryParam theoryParam = new TheoryParam(ssGraph, ssRouting);
//

        Network net = new Network(ssGraph, ssRouting);
        EventSim sim = new EventSim(99999999);

        List<Pair<Integer, Integer>> traffic = new ArrayList<>();
//        traffic.put(0, 3);
//        traffic.put(1, 3);
        int numSent = 0;
        while(numSent < 100) {
            ArrayList<Integer> hosts = (ArrayList<Integer>) ssGraph.hosts();
            int temp1 = (int) (Math.random() * hosts.size());
            int temp2 = (int) (Math.random() * hosts.size());

            if (temp1 == temp2) continue;
            traffic.add(new Pair(hosts.get(temp1), hosts.get(temp2)));
            numSent++;
        }

        for (Pair<Integer, Integer> pair : traffic) {
            int destination = pair.getValue();
            int source = pair.getKey();
            final Packet packet = new Packet(source, destination, sim.getTime());

            sim.addEvent(new Event(++sim.numEvent, sim.getTime()) {
                @Override
                public void execute() {
                    net.getHostById(source).process(packet, sim);
                }

                @Override
                public String info() {
                    return String.format(Format.LeftAlignFormat, packet.startNode, packet.endNode, "Delay At Node " + source, this.timeStart);

                }
            });

        }

        sim.process();
        Experiment e = new Experiment(sim);

        OutFile.getFile().append("\nTotal packet sent: " + sim.numSent);
        OutFile.getFile().append("\nTotal packet received: " + sim.numReceived);

        System.out.println(e.averagePacketTravel());
        OutFile.getFile().close();

    }
}
