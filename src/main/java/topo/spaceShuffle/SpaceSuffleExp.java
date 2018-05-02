package topo.SpaceSuffle;


import common.Format;
import common.Knuth;

import event.Event;
import event.EventSim;
import network.Network;
import network.Packet;
import topo.Experiment;
import topo.spaceShuffle.SpaceShuffleGraph;
import topo.spaceShuffle.SpaceShuffleRouting;

import java.util.*;

public class SpaceSuffleExp {
    public static void main(String[] args) {
        SpaceShuffleGraph ftGraph = new SpaceShuffleGraph(9, 6 , 2);
        SpaceShuffleRouting ftRouting = new SpaceShuffleRouting(ftGraph);

        ArrayList<Integer> listHost = (ArrayList<Integer>) ftGraph.hosts();


        Network net = new Network(ftGraph, ftRouting);
        EventSim sim = new EventSim(1000);


        Map<Integer, Integer> traffic = new HashMap<>();
        for (int i = 0; i < 10; i++) {
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
                    return String.format(Format.LeftAlignFormat, packet.startNode, packet.endNode, "Delay At Node " + source, packet.startTime);

                }
            });

        }


        sim.process();
        Experiment e = new Experiment(sim);


        System.out.println(e.averagePacketTravel());

    }
}
