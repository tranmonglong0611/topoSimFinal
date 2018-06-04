import tamolo.common.Format;
import javafx.util.Pair;
import tamolo.simulation.Network;
import tamolo.simulation.Packet;
import tamolo.simulation.event.Event;
import tamolo.simulation.event.EventSim;
import tamolo.report.Report;
import tamolo.topo.TheoryParam;
import tamolo.topo.Topology;
import tamolo.topo.fatTree.FatTreeRouting;
import tamolo.topo.fatTree.FatTreeTopology;
import tamolo.topo.routing.RoutingAlgorithm;

import java.util.ArrayList;
import java.util.List;

/*
    author tamolo
    date 5/19/18
*/
public class Experiment {

    public static void main(String[] args) {
        FatTreeTopology topo = new FatTreeTopology(4);
        FatTreeRouting routing = new FatTreeRouting(topo);
        Experiment.doSim(topo, routing, 100, true, true, true);
    }
    public static void topoStatisTic(Topology topo, RoutingAlgorithm routing) {
        Report.getTopoInfoFile().append(topo.toString());
        TheoryParam theoryParam = new TheoryParam(topo, routing);
        Report.endSim();
    }

    public static void doSim(Topology topo, RoutingAlgorithm routing, int numPacket, boolean topoInfoExport, boolean isTracing, boolean theoryParamCal) {

        if(topoInfoExport) {
            Report.getTopoInfoFile().append(topo.toString());
        }
        doExperiment(topo, routing, numPacket, isTracing);
        if(theoryParamCal) {
            TheoryParam theoryParam = new TheoryParam(topo, routing);
        }

        Report.endSim();


    }
    private static void doExperiment(Topology topo, RoutingAlgorithm routing, int numPacket, boolean isTracing) {
        EventSim sim = new EventSim(99999999999999L, isTracing);
        List<Pair<Integer, Integer>> traffic = makeRandomTraffic(topo, routing, numPacket);
        Network net = new Network(topo, routing);
        runSim(sim, net, traffic);
    }

    private static List<Pair<Integer, Integer>> makeRandomTraffic(Topology topo, RoutingAlgorithm routing, int numPacket) {
        List<Pair<Integer, Integer>> traffic = new ArrayList<>();
        int numSent = 0;
        while(numSent < numPacket) {
            ArrayList<Integer> hosts = (ArrayList<Integer>) topo.hosts();
            int temp1 = (int) (Math.random() * hosts.size() / 2);
            int temp2 = hosts.size() / 2 + (int) (Math.random() * hosts.size() / 2);

            if (temp1 == temp2) continue;
            traffic.add(new Pair(hosts.get(temp1), hosts.get(temp2)));
            numSent++;
        }
        return traffic;
    }

    private static void runSim(EventSim sim, Network net, List<Pair<Integer, Integer>> traffic) {
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
    }

    public static List<Integer> randomErrorSwitch(Topology topo, int percent) {
        ArrayList<Integer> errorSwitch = new ArrayList<>();
//        FatTreeRouting ftRouting = new FatTreeRouting(ftGraph);
        int numSwitch = topo.switches().size();
        for(int i = 0; i < percent * numSwitch / 100; i++) {
            int random = (int)(Math.random() * numSwitch);
            int eSwitch = topo.switches().get(random);

            if(errorSwitch.contains(eSwitch)) {
                i--;
            }else {
                errorSwitch.add(eSwitch);
            }
        }

        return errorSwitch;
    }
}
