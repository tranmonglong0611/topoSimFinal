package topo.spaceShuffle;

import topo.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class SpaceShuffleGraph extends Graph {

    public final int nDimension;         //dimension
    public final int nPort;         //number of port each switch
    public final int nSwitch;       //number of switch


    public final int nHost;
    public final int r;     //r port used to connect another switch. k -r used to connect host
    public final int nHostPerSwitch;

    private List<Integer> hosts;
    private List<Integer> switches;

    /**
     * standPoint[d][i] is the stand point value in [0, 1) of vertex i in dimension d
     */
    private double[][] standPoint;
    /**
     * verticesInOrder[d] is the array of vertices sorted in ascending order in dimension d
     */
    private int[][] verticesInOrder;


    public SpaceShuffleGraph(int nSwitch, int nPort, int nDimension) {

        /**
         * init info
         */
        this.nSwitch = nSwitch;
        this.nPort = nPort;
        this.nDimension = nDimension;

        this.r = 2 * nDimension;
        this.nHostPerSwitch = nPort - r;
        this.nHost = nSwitch * (this.nPort - this.r);
        this.numV = nSwitch + nHost;

        adj = (List<Integer>[]) new List[numV];
        for (int v = 0; v < numV; v++) {
            adj[v] = new ArrayList<Integer>();
        }

        this.standPoint = new double[nDimension][nSwitch];
        this.verticesInOrder = new int[nDimension][nSwitch];

        for (int i = 0; i < nDimension; i++) {
            for (int j = 0; j < nSwitch; j++) {
                verticesInOrder[i][j] = j;
            }
        }

        Random random = new Random();
        //generate random standpoint for each vertex in each dimension
        for (int i = 0; i < nDimension; i++) {
            for (int j = 0; j < nSwitch; j++) {
                double r = random.nextDouble();
                standPoint[i][j] = r;
            }
        }

        IntStream.range(0, nDimension).forEach(d -> {
                    verticesInOrder[d] = Arrays.stream(verticesInOrder[d]).boxed().sorted((v1, v2) -> {
                        if (standPoint[d][v1] < standPoint[d][v2]) return -1;
                        else return 1;
                    }).mapToInt(i -> i).toArray();
                }
        );

        // add neighbors to each vertex using its order on each dimension
        for (int d = 0; d < nDimension; d++) {
            for (int i = 0; i < nSwitch; i++) {
                int u = verticesInOrder[d][i];
                int[] ns = new int[]{verticesInOrder[d][(i + 1) % nSwitch], verticesInOrder[d][(i - 1 + nSwitch) % nSwitch]};

                for (int n : ns) {
                    if (!hasEdge(u, n)) {
                        addEdge(u, n);
                        System.out.println("add edge: " + u + "====" + n);
                    }
                }
            }
        }
        //add new to test

        for (int i = 0; i < nSwitch; i++) {
            int u = i;
            int[] vAvail = IntStream.range(0, nSwitch).
                    filter(v -> !hasEdge(u, v) && u != v && degree(v) < r && degree(u) < r).toArray();

            if (vAvail.length > 0) {
                int v = vAvail[random.nextInt(vAvail.length)];
                System.out.println("fuck edge: " + u + "====" + v);
                addEdge(u, v);
            }
        }


        System.out.println(this.toString());


        // Add edges between host and switch
        int hostId = nSwitch;
        for (int i = 0; i < nSwitch; i++) {
            for (int j = 0; j < nHostPerSwitch; j++) {
                addEdge(i, hostId);
                hostId++;
            }
        }

    }

    public int nDimension() {
        return nDimension;
    }


    public double standPoint(int dimension, int v) {
        return standPoint[dimension][v];
    }

    public double distance(int u, int v) {
        if (isHostVertex(u)) u = adj(u).get(0);
        if (isHostVertex(v)) v = adj(v).get(0);

        double res = Double.MAX_VALUE;
        for (int d = 0; d < nDimension; d++) {
            double absoluteDistance = Math.abs(standPoint[d][u] - standPoint[d][v]);
            double distanceOnDimension = Math.min(absoluteDistance, 1 - absoluteDistance);
            res = Math.min(distanceOnDimension, 1 - distanceOnDimension);
        }

        return res;
    }

    @Override
    public List<Integer> hosts() {
        if (hosts != null) return hosts;

        hosts = new ArrayList<>();
        for (int i = nSwitch; i < numV; i++) {
            hosts.add(i);
        }
        return hosts;


    }

    @Override
    public List<Integer> switches() {
        if (switches != null) return switches;

        switches = new ArrayList<>();
        for (int i = 0; i < nSwitch; i++) {
            switches.add(i);
        }

        return switches;

    }

    @Override
    public boolean isHostVertex(int v) {
        return hosts.contains(v);
    }

    @Override
    public boolean isSwitchVertex(int v) {
        return !isHostVertex(v);
    }


}
