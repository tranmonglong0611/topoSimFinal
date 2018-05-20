import common.Format;
import javafx.util.Pair;
import simulation.Network;
import simulation.Packet;
import simulation.event.Event;
import simulation.event.EventSim;
import report.Report;
import topo.TheoryParam;
import topo.Topology;
import topo.fatTree.FatTreeRouting;
import topo.fatTree.FatTreeTopology;
import topo.routing.RoutingAlgorithm;

import java.util.ArrayList;
import java.util.List;

/*
    author tamolo
    date 5/19/18
*/
public class Experiment {

    public static void main(String[] args) {
        Experiment experiment = new Experiment();
        experiment.something();
    }
    public void something() {

        //tao graph moi
        //
        FatTreeTopology topo = new FatTreeTopology(4);
        FatTreeRouting routing = new FatTreeRouting(topo);
        Report.getTopoInfoFile().append(topo.toString());


        doExperiment(topo, routing, 10, true);
        TheoryParam theoryParam = new TheoryParam(topo, routing, true);
        Report.endSim();


    }
    public void doExperiment(Topology topo, RoutingAlgorithm routing, int numPacket, boolean isTracing) {
        EventSim sim = new EventSim(99999999999999L, isTracing);
        List<Pair<Integer, Integer>> traffic = makeRandomTraffic(topo, routing, numPacket);
        Network net = new Network(topo, routing);
        runSim(sim, net, traffic);
    }

    public static List<Pair<Integer, Integer>> makeRandomTraffic(Topology topo, RoutingAlgorithm routing, int numPacket) {
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

    public void runSim(EventSim sim, Network net, List<Pair<Integer, Integer>> traffic) {
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

    public List<Integer> randomErrorSwitch(Topology topo, int percent) {
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
