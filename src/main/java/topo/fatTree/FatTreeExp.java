package topo.fatTree;

import common.Format;
import event.Event;
import event.EventSim;
import javafx.util.Pair;
import network.Config;
import network.Network;
import network.Packet;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import output.OutFile;
import topo.Experiment;
import topo.TheoryParam;
import topo.spaceShuffle.SpaceShuffleGraph;
import topo.spaceShuffle.SpaceShuffleRouting;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FatTreeExp {

    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(FatTreeExp.class.getName());
//
//        SpaceShuffleGraph ftGraph = new SpaceShuffleGraph(9, 6, 2);
//        SpaceShuffleRouting ftRouting = new SpaceShuffleRouting(ftGraph);
        FatTreeGraph ftGraph = new FatTreeGraph(4);
        ArrayList<Integer> errorSwitch = new ArrayList<>();
        errorSwitch.add(4);
        errorSwitch.add(5);
        FatTreeRoutingFaultTolerance ftRouting = new FatTreeRoutingFaultTolerance(ftGraph, errorSwitch);
//        TheoryParam theoryParam = new TheoryParam(ftGraph, ftRouting);

        ArrayList<Integer> listHost = (ArrayList<Integer>) ftGraph.hosts();

        Network net = new Network(ftGraph, ftRouting);
        EventSim sim = new EventSim(99999999);

        List<Pair<Integer, Integer>> traffic = new ArrayList<>();
//        traffic.put(0, 3);
//        traffic.put(1, 3);
        int numSent = 0;
        while(numSent < 100) {
            ArrayList<Integer> hosts = (ArrayList<Integer>) ftGraph.hosts();
            int temp1 = (int) (Math.random() * hosts.size());
            int temp2 = (int) (Math.random() * hosts.size());

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
        Experiment e = new Experiment(sim);

        OutFile.getFile().append("\nTotal packet sent: " + sim.numSent);
        OutFile.getFile().append("\nTotal packet received: " + sim.numReceived);


        OutFile.getFile().close();
//        sim.out.append("Average Packet Travel: " + e.averagePacketTravel());

    }
}
