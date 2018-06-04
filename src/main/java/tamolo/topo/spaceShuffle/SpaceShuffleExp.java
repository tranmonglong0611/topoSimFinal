package tamolo.topo.spaceShuffle;


import tamolo.common.Format;

import tamolo.simulation.event.Event;
import tamolo.simulation.event.EventSim;
import javafx.util.Pair;
import tamolo.simulation.Network;
import tamolo.simulation.Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tamolo.report.Report;

import java.util.*;

public class SpaceShuffleExp {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(SpaceShuffleExp.class.getName());
        SpaceShuffleTopology ssGraph = new SpaceShuffleTopology(256, 8, 2);
//        ArrayList<Integer> listErrorSwitch = new ArrayList<>();
//        listErrorSwitch.add(5);
//        listErrorSwitch.add(7);
        ArrayList<Integer> errorSwitch = new ArrayList<>();
        int numSwitch = ssGraph.switches().size();

        for(int i = 0; i < 55 * numSwitch / 100; i++) {
            int random = (int)(Math.random() * numSwitch);
            int eSwitch = ssGraph.switches().get(random);

            if(errorSwitch.contains(eSwitch)) {
                i--;
            }else {
                errorSwitch.add(eSwitch);
            }
        }
        SpaceShuffleRouting ssRouting = new SpaceShuffleRouting(ssGraph, errorSwitch);

        System.out.println("Done make graph and routing");
//        TheoryParam theoryParam = new TheoryParam(ssGraph, ssRouting);
//

        Network net = new Network(ssGraph, ssRouting);
        EventSim sim = new EventSim(99999999999999999L, false);

        List<Pair<Integer, Integer>> traffic = new ArrayList<>();
//        traffic.put(0, 3);
//        traffic.put(1, 3);
        int numSent = 0;
        while(numSent < 10000) {
            ArrayList<Integer> hosts = (ArrayList<Integer>) ssGraph.hosts();
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

//        TheoryParam theoryParam = new TheoryParam(ssGraph, ssRouting);
        Report.getTraceFile().close();


    }
}
