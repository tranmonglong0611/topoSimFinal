package topo.FatTree;

import event.Event;
import event.EventSim;
import network.Config;
import network.Network;
import network.Packet;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import topo.Experiment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FatTreeExp {

    public static void main(String[] args) {


        Logger logger = LogManager.getLogger(FatTreeExp.class.getName());
//        String parameter = "tamolo";
//        if(logger.isDebugEnabled()){
//            logger.debug("This is debug : " + parameter);
//        }
//
//        if(logger.isInfoEnabled()){
//            logger.info("This is info : " + parameter);
//        }
//
//        logger.warn("This is warn : " + parameter);
//        logger.error("This is error : " + parameter);
//        logger.fatal("This is fatal : " + parameter);
        FatTreeGraph ftGraph = new FatTreeGraph(16);
        FatTreeRouting ftRouting = new FatTreeRouting(ftGraph, true);
        logger.info(ftGraph.toString());

//        SpaceShuffleGraph ftGraph = new SpaceShuffleGraph(30, 12 , 2);
//        SpaceShuffleRouting ftRouting = new SpaceShuffleRouting(ftGraph);



        ArrayList<Integer> listHost = (ArrayList<Integer>) ftGraph.hosts();


        Network net = new Network(ftGraph, ftRouting);
        EventSim sim = new EventSim(1000);


        Map<Integer, Integer> traffic = new HashMap<>();
        for (int i = 0; i < Config.NUM_PACKET_SEND; i++) {
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
                    return packet.toString() + " CurrentNode: " + source;
                }
            });

        }


        sim.process();
        Experiment e = new Experiment(sim);


        sim.out.append("Average Packet Travel: " + e.averagePacketTravel());

    }
}
