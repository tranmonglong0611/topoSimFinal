package topo.spaceShuffle;


import common.Format;

import event.Event;
import event.EventSim;
import network.Config;
import network.Network;
import network.Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import output.OutFile;
import topo.Experiment;

import java.util.*;

public class SpaceShuffleExp {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(SpaceShuffleExp.class.getName());
        SpaceShuffleGraph ssGraph = new SpaceShuffleGraph(800, 6, 2);
        SpaceShuffleRouting ftRouting = new SpaceShuffleRouting(ssGraph);

        ArrayList<Integer> listHost = (ArrayList<Integer>) ssGraph.hosts();

        logger.info("Done making graph");
        OutFile.getFile().append(ssGraph.toString());
        logger.info("Done write class to file");

        Network net = new Network(ssGraph, ftRouting);
        EventSim sim = new EventSim(1000);


        Map<Integer, Integer> traffic = new HashMap<>();
        for (int i = 0; i < Config.NUM_PACKET_SEND; i++) {
            ArrayList<Integer> hosts = (ArrayList<Integer>) ssGraph.hosts();
            int temp1 = (int) (Math.random() * hosts.size());
            int temp2 = (int) (Math.random() * hosts.size());

            if (temp1 == temp2) continue;
            traffic.put(hosts.get(temp1), hosts.get(temp2));
        }


        for (Integer source : traffic.keySet()) {
            Integer destination = traffic.get(source);

            final Packet packet = new Packet(source, destination, sim.getTime());

            sim.addEvent(new Event(++sim.numEvent, sim.getTime()) {
                @Override
                public void execute() {
                    net.getHostById(source).process(packet, sim);
                }

                @Override
                public String info() {
                    return String.format(Format.LeftAlignFormat, packet.startNode, packet.endNode, "Delay At Node " + source, packet.startTime);

                }
            });

        }


        sim.process();
        Experiment e = new Experiment(sim);


        System.out.println(e.averagePacketTravel());

    }
}
