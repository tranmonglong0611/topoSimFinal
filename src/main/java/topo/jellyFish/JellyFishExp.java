package topo.jellyFish;

import common.Format;
import event.Event;
import event.EventSim;
import network.Config;
import network.Network;
import network.Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import output.OutFile;
import routing.RoutingPath;
import topo.Experiment;
import topo.TheoryParam;
import topo.fatTree.FatTreeExp;
import topo.spaceShuffle.SpaceShuffleGraph;
import topo.spaceShuffle.SpaceShuffleRouting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JellyFishExp {

    public static void main(String[] args) {


        Logger logger = LogManager.getLogger(JellyFishGraph.class.getName());

        JellyFishGraph jlGraph = new JellyFishGraph(20, 6, 3);
        AllShortestPathRouting jlRouting = new AllShortestPathRouting(jlGraph);

        logger.info("Done making graph");
//        OutFile.getFile().append(jlGraph.toString());
        logger.info("Done write class to file");

        TheoryParam theoryParam = new TheoryParam(jlGraph, jlRouting);

        Network net = new Network(jlGraph, jlRouting);
        EventSim sim = new EventSim(9999999);


        Map<Integer, Integer> traffic = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            ArrayList<Integer> hosts = (ArrayList<Integer>) jlGraph.hosts();
            int temp1 = (int) (Math.random() * hosts.size());
            int temp2 = (int) (Math.random() * hosts.size());

            if (temp1 == temp2) continue;
            traffic.put(hosts.get(temp1), hosts.get(temp2));
        }


        for (Integer source : traffic.keySet()) {
            Integer destination = traffic.get(source);
            RoutingPath routingPath = jlRouting.getRandomPath(source, destination);
//            logger.info("Choosing: " + routingPath.toString());
//
//            for(RoutingPath path : jlRouting.routingTable.get(source).get(destination)) {
//                logger.info(path.toString());
//            }
//            System.out.println("=================");


            final Packet packet = new Packet(source, destination, sim.getTime(), routingPath);

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

        OutFile.getFile().close();

//        sim.out.append("Average Packet Travel: " + e.averagePacketTravel());

    }
}
