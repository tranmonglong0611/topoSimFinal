package tamolo.topo.fatTree;

import tamolo.common.Format;
import tamolo.simulation.event.Event;
import tamolo.simulation.event.EventSim;
import javafx.util.Pair;
import tamolo.simulation.Network;
import tamolo.simulation.Packet;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import tamolo.report.Report;

import java.util.ArrayList;
import java.util.List;

public class FatTreeExp {

    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(FatTreeExp.class.getName());
//
//        SpaceShuffleGraph ftGraph = new SpaceShuffleGraph(9, 6, 2);
//        SpaceShuffleRouting ftRouting = new SpaceShuffleRouting(ftGraph);
        FatTreeTopology ftGraph = new FatTreeTopology(4);

        ArrayList<Integer> errorSwitch = new ArrayList<>();
//        FatTreeRouting ftRouting = new FatTreeRouting(ftGraph);
        int numSwitch = ftGraph.switches().size();

        for(int i = 0; i < 20 * numSwitch / 100; i++) {
            int random = (int)(Math.random() * numSwitch);
            int eSwitch = ftGraph.switches().get(random);

            if(errorSwitch.contains(eSwitch)) {
                i--;
            }else {
                errorSwitch.add(eSwitch);
            }
        }

        FatTreeRoutingFaultTolerance ftRouting = new FatTreeRoutingFaultTolerance(ftGraph, errorSwitch);
//        TheoryParam theoryParam = new TheoryParam(ftGraph, ftRouting);

//        FatTreeRoutingFaultTolerance ftRouting = new FatTreeRoutingFaultTolerance(ftGraph, errorSwitch);
        ArrayList<Integer> listHost = (ArrayList<Integer>) ftGraph.hosts();

        Network net = new Network(ftGraph, ftRouting);
        EventSim sim = new EventSim(9999999999999999L, true);

        List<Pair<Integer, Integer>> traffic = new ArrayList<>();
//        traffic.put(0, 3);
//        traffic.add(new Pair<>(1, 3));
        int numSent = 0;
        while(numSent < 100) {
            ArrayList<Integer> hosts = (ArrayList<Integer>) ftGraph.hosts();
            int temp1 = (int) (Math.random() * hosts.size() / 2);
            int temp2 = hosts.size() / 2 + (int) (Math.random() * hosts.size() / 2);

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
//        sim.averagePacketTravel();

        System.out.println("numsent: " + sim.numSent);
        System.out.println("Num received: " + sim.numReceived);
        System.out.println("Average Packet Travel: " + sim.averagePacketTravel());
        System.out.println("Bandwidth: " + sim.throughput());



        System.out.println("get result at: " + Report.folderName);
//        sim.out.append("Average Packet Travel: " + e.averagePacketTravel());

    }
}
