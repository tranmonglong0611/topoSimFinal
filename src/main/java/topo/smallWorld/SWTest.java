package topo.smallWorld;

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

public class SWTest {

    public static void main(String args[]) {

////        for(Map.Entry<Integer, RoutingTable> entry : routing.tables.entrySet()) {
////            System.out.println(entry.getKey());
////            System.out.println(entry.getValue());
////            System.out.println("=======");
////        }
//
//        System.out.println(a.toString());
//        List<List<Integer>> neighbors = a.kHopNeighbor(0,4);
//
//        for(int i = 0; i < neighbors.size(); i++) {
//            for(int j = 0; j < neighbors.get(i).size(); j++) {
//                System.out.print(neighbors.get(i).get(j) + "--");
//            }
//            System.out.println();
//        }
////        List<Integer> l  = a.shortestPath(27, 40);
////        for(int i = 0; i < l.size(); i++) {
////            System.out.println(l.get(i));
////        }
        SmallWorldTopology swGraph = new SmallWorldTopology(5, 5, "torus", new double[]{1.6, 1.6});
        SmallWorldRoutingAlgorithm swRouting = new SmallWorldRoutingAlgorithm(swGraph);
        Logger logger = LogManager.getLogger(SWTest.class.getName());

        logger.info("Done making graph");
        OutFile.getFile().append(swGraph.toString());
        logger.info("Done write class to file");

        ArrayList<Integer> listHost = (ArrayList<Integer>) swGraph.hosts();
        Network net = new Network(swGraph, swRouting);
        EventSim sim = new EventSim(1000);


        Map<Integer, Integer> traffic = new HashMap<>();
        for (int i = 0; i < Config.NUM_PACKET_SEND; i++) {
            ArrayList<Integer> hosts = (ArrayList<Integer>) swGraph.hosts();
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

//                    return packet.toString() + " CurrentNode: " + source;
                }
            });

        }

        logger.info("Start doing simulation");
        sim.process();
        Experiment e = new Experiment(sim);


    }
}
