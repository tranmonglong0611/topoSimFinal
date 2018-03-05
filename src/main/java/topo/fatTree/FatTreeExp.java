package topo.fatTree;

import event.Event;
import event.EventSim;
import network.Config;
import network.Network;
import network.Packet;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import topo.Experiment;
import topo.spaceShuffle.SpaceShuffleGraph;
import topo.spaceShuffle.SpaceShuffleRouting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FatTreeExp {

    public static void main(String[] args) {


        Logger logger = LogManager.getLogger(FatTreeExp.class.getName());

        SpaceShuffleGraph ftGraph = new SpaceShuffleGraph(9, 6, 2);
        SpaceShuffleRouting ftRouting = new SpaceShuffleRouting(ftGraph);
        logger.info(ftGraph.toString());


        ArrayList<Integer> listHost = (ArrayList<Integer>) ftGraph.hosts();


        Network net = new Network(ftGraph, ftRouting);
        EventSim sim = new EventSim(1000);


        Map<Integer, Integer> traffic = new HashMap<>();
        for (int i = 0; i < Config.NUM_PACKET_SEND; i++) {
            ArrayList<Integer> hosts = (ArrayList<Integer>) ftGraph.hosts();
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
                    return packet.toString() + " CurrentNode: " + source;
                }
            });

        }


        sim.process();
        Experiment e = new Experiment(sim);


        sim.out.append("Average Packet Travel: " + e.averagePacketTravel());

    }
}
