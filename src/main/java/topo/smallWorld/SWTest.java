package topo.smallWorld;

import routing.ShortestRoutingAlgorithm;

import java.util.List;

public class SWTest {

    public static void main(String args[]) {
        SWRingGraph a = new SWRingGraph(10, 2, 0.2);
        System.out.println(a.toString());


        List<Integer> l  = a.shortestPath(0, 6);
        for(int i = 0; i < l.size(); i++) {
            System.out.println(l.get(i));
        }
    }
}
