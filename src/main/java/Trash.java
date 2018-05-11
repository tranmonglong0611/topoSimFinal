import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import routing.RoutingPath;
import topo.Graph;
import topo.fatTree.FatTreeGraph;
import topo.fatTree.FatTreeRoutingFaultTolerance;
import topo.smallWorld.SmallWorldGraph;

import javax.swing.text.StyleContext;
import java.util.*;

/*
    author tamolo
    date 5/1/18
*/
public class Trash {
    final static Logger logger = LogManager.getLogger(Trash.class);

    public static void main(String[] args) {
        FatTreeGraph fatTreeGraph = new FatTreeGraph(4);
        ArrayList<Integer> errorSwitch = new ArrayList<>();
//        errorSwitch.add(32);
        errorSwitch.add(33);
        errorSwitch.add(34);
        errorSwitch.add(7);


//        errorSwitch.add(35);

        FatTreeRoutingFaultTolerance ra = new FatTreeRoutingFaultTolerance(fatTreeGraph, errorSwitch);
        RoutingPath trace = ra.path(2, 17);
        System.out.println(trace);
//
//        Map<Integer, Map<Integer, Integer>> aggreDownRouting = ra.aggreDownRouting;
//        Map<Integer, Integer> haha = aggreDownRouting.get(22);
//        for(Map.Entry<Integer, Integer> entry: haha.entrySet()) {
//            System.out.println(entry.getKey() + "===" + entry.getValue());
//        }
//        for (Map.Entry<Integer, Map<Integer, Integer>> entry : aggreDownRouting.entrySet()) {
//            Map<Integer, Integer> hehe =entry.getValue();
//            System.out.println("k = " + entry.getKey());
//            System.out.println();
//            for(int i = 0; i < hehe.size(); i++) {
//                System.out.print(hehe.get(i) + "---");
//            }
//            System.out.println();
//        }

        //key la core vertex. Map co key la pod, value la agg vertex
//        Map<Integer, Map<Integer, Integer>> coreRouting = ra.coreRouting;
//        Map<Integer, Integer> haha =coreRouting.get(32);
//        for(Map.Entry<Integer, Integer> entry: haha.entrySet()) {
//            System.out.println(entry.getKey() + "===" + entry.getValue());
//        }


        //key = 2 => list cac core switch khong the connect toi pod = 2
//        Map<Integer, List<Integer>> coreAvailEachPod = ra.coreAvailEachPod;
//
//        for (Map.Entry<Integer, List<Integer>> entry : coreAvailEachPod.entrySet()) {
//            List<Integer> hehe =entry.getValue();
//            System.out.println("k = " + entry.getKey());
//            System.out.println();
//            for(int i = 0; i < hehe.size(); i++) {
//                System.out.print(hehe.get(i) + "---");
//            }
//            System.out.println();
//        }
    }

}
