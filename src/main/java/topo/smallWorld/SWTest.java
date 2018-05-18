package topo.smallWorld;

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
import routing.RoutingPath;
import routing.ShortestRoutingAlgorithm;
import topo.Experiment;
import topo.TheoryParam;

import javax.xml.bind.SchemaOutputResolver;
import java.util.*;

public class SWTest {

    public static void main(String args[]) {

        SmallWorldTopology swGraph = new SmallWorldTopology(40, 40, "torus", new double[]{1.6, 1.6});
        ShortestRoutingAlgorithm swRouting = new ShortestRoutingAlgorithm(swGraph);
        Logger logger = LogManager.getLogger(SWTest.class.getName());

        logger.info("Done making graph");
        logger.info("Done write class to file");

        ArrayList<Integer> listHost = (ArrayList<Integer>) swGraph.hosts();
        Network net = new Network(swGraph, swRouting);
        EventSim sim = new EventSim(9999999999999999L, false);


        List<Pair<Integer, Integer>> traffic = new ArrayList<>();
//        traffic.put(0, 3);
//        traffic.add(new Pair<>(1, 3));

        int numSent = 0;
        while(numSent < 12000) {
            ArrayList<Integer> hosts = (ArrayList<Integer>) swGraph.hosts();
            int temp1 = (int) (Math.random() * hosts.size() / 2);
            int temp2 = hosts.size() / 2  + (int) (Math.random() * hosts.size() / 2);

            if (temp1 == temp2) continue;
            traffic.add(new Pair(hosts.get(temp1), hosts.get(temp2)));
            numSent++;
        }

        for (Pair<Integer, Integer> pair : traffic) {
            int destination = pair.getValue();
            int source = pair.getKey();
//            logger.info("Choosing: " + routingPath.toString());
//
//            for(RoutingPath path : jlRouting.routingTable.get(source).get(destination)) {
//                logger.info(path.toString());
//            }
//            System.out.println("=================");


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

        System.out.println("Sending: " + sim.numSent);
        System.out.println("Received: " + sim.numReceived);
        System.out.println("Average Packet Travel: " + sim.averagePacketTravel());
        System.out.println("Bandwidth: " + sim.throughput());

        TheoryParam theoryParam = new TheoryParam(swGraph, swRouting);
        OutFile.getFile().close();

//        sim.out.append("Average Packet Travel: " + e.averagePacketTravel());

    }

}
