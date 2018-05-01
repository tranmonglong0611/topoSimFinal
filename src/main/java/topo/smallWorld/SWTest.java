package topo.smallWorld;

import routing.ShortestRoutingAlgorithm;

import java.util.List;

public class SWTest {

    public static void main(String args[]) {
        SmallWorldGraph a = new SmallWorldGraph(5, 5, "torus", new double[]{1.6, 1.6});
        System.out.println(a.toString());


        List<Integer> l  = a.shortestPath(27, 40);
        for(int i = 0; i < l.size(); i++) {
            System.out.println(l.get(i));
        }
    }
}
