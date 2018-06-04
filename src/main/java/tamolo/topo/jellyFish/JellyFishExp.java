package tamolo.topo.jellyFish;

import tamolo.common.Format;
import tamolo.simulation.event.Event;
import tamolo.simulation.event.EventSim;
import javafx.util.Pair;
import tamolo.simulation.Network;
import tamolo.simulation.Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tamolo.report.Report;
import tamolo.topo.routing.RoutingPath;

import java.util.ArrayList;
import java.util.List;

public class JellyFishExp {

    public static void main(String[] args) {


        Logger logger = LogManager.getLogger(JellyFishTopology.class.getName());

        JellyFishTopology jlGraph = new JellyFishTopology(320, 8, 4);

        ArrayList<Integer> errorSwitch = new ArrayList<>();
////        FatTreeRouting ftRouting = new FatTreeRouting(ftGraph);
        int numSwitch = jlGraph.switches().size();
//
        for(int i = 0; i < 5 * numSwitch / 100; i++) {
            int random = (int)(Math.random() * numSwitch);
            int eSwitch = jlGraph.switches().get(random);

            if(errorSwitch.contains(eSwitch)) {
                i--;
            }else {
                errorSwitch.add(eSwitch);

            }
        }
        AllShortestPathRouting jlRouting = new AllShortestPathRouting(jlGraph, errorSwitch);

        logger.info("Done making graph");
        logger.info("Done write class to file");


        Network net = new Network(jlGraph, jlRouting);
        EventSim sim = new EventSim(999999999999999L, false);


        List<Pair<Integer, Integer>> traffic = new ArrayList<>();
//        traffic.put(0, 3);
//        traffic.add(new Pair<>(1, 3));

        int numSent = 0;
        while(numSent < 10000) {
            ArrayList<Integer> hosts = (ArrayList<Integer>) jlGraph.hosts();
            int temp1 = (int) (Math.random() * hosts.size() / 2);
            int temp2 = hosts.size() / 2  + (int) (Math.random() * hosts.size() / 2);

            if (temp1 == temp2) continue;
            traffic.add(new Pair(hosts.get(temp1), hosts.get(temp2)));
            numSent++;
        }

        for (Pair<Integer, Integer> pair : traffic) {
            int destination = pair.getValue();
            int source = pair.getKey();
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

        Report.getTraceFile().append("\nTotal packet sent: " + sim.numSent);
        Report.getTraceFile().append("\nTotal packet received: " + sim.numReceived);
        System.out.println("numsent: " + sim.numSent);
        System.out.println("Num received: " + sim.numReceived);
        System.out.println("Average Packet Travel: " + sim.averagePacketTravel());
        System.out.println("Bandwidth: " + sim.throughput());

//        TheoryParam theoryParam = new TheoryParam(jlGraph, jlRouting);
        Report.getTraceFile().close();

//        sim.out.append("Average Packet Travel: " + e.averagePacketTravel());

    }
}
