package topo.jellyFish;

import topo.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class JellyFishGraph extends Graph {

    public final int nPort;
    public final int r;                 // r port to connect to another switch

    public final int nHostPerSwitch;
    public final int nHost;
    public final int nSwitch;

    private List<Integer> hosts;
    private List<Integer> switches;


    public JellyFishGraph(int nSwitch, int nPort, int r) {
        this.nSwitch = nSwitch;
        this.nPort = nPort;
        this.r = r;
        this.nHostPerSwitch = nPort - r;
        this.nHost = nSwitch * nHostPerSwitch;
        this.numV = nSwitch + nHost;


        adj = (List<Integer>[]) new List[numV];
        for (int i = 0; i < numV; i++) {
            adj[i] = new ArrayList<>();
        }

        //todo make random jelly fish graph
        Random random = new Random();

        //list vertex still have avail port

        while (true) {
            int[] vertexAvail = IntStream.range(0, nSwitch).filter(v -> degree(v) < r).toArray();
            if (vertexAvail.length == 0) break;
            else if (vertexAvail.length == 1) {
                if (degree(vertexAvail[0]) == r - 1) {
                    break;
                }
                List<Integer> notNeighbors = notNeighborSwitches(vertexAvail[0]);
                //
                int randomIndex = random.nextInt(notNeighbors.size());

                boolean isReplaced = false;

                for (int i = randomIndex; i < randomIndex + notNeighbors.size(); i++) {
                    int index = i % notNeighbors.size();
                    for (int j = 0; j < notNeighbors.size(); j++) {
                        int v1 = notNeighbors.get(index);
                        int v2 = notNeighbors.get(j);
                        if (hasEdge(v1, v2)) {
                            isReplaced = true;
                            removeEdge(v1, v2);
                            System.out.println("11addedge" + vertexAvail[0] + "===" + v1 + "====" + v2);
                            addEdge(vertexAvail[0], v1);
                            addEdge(vertexAvail[0], v2);
                            break;
                        }
                    }
                    if(isReplaced) break;
                }
                if (!isReplaced) System.out.println("SOMETHING WRONG");
            } else if (vertexAvail.length == 2 && hasEdge(vertexAvail[0], vertexAvail[1])) {
                List<Integer> notNeighborsV1 = notNeighborSwitches(vertexAvail[0]);
                List<Integer> notNeighborsV2 = notNeighborSwitches(vertexAvail[1]);

                boolean isReplaced = false;
                for (int i = 0; i < notNeighborsV1.size(); i++) {
                    for (int j = 0; j < notNeighborsV2.size(); j++) {
                        int v1 = notNeighborsV1.get(i);
                        int v2 = notNeighborsV2.get(j);
                        if (hasEdge(v1, v2)) {
                            isReplaced = true;
                            removeEdge(v1, v2);
                            System.out.println("22addedge" + vertexAvail[0] + "===" + v1);
                            System.out.println("22addedge" + vertexAvail[1] + "===" + v2);

                            addEdge(v1, vertexAvail[0]);
                            addEdge(v2, vertexAvail[1]);
                        }
                    }
                    if(isReplaced) break;
                }
                if (!isReplaced) System.out.println("SOMETHING WRONG2");

            } else {
                int v1;
                int v2;
                while (true) {
                    int rv1 = vertexAvail[random.nextInt(vertexAvail.length)];
                    int[] vNotConnect = Arrays.stream(vertexAvail).filter(v -> !hasEdge(v, rv1) && v != rv1).toArray();
                    if (vNotConnect.length == 0) {
                        continue;
                    }else {
                        v1 = rv1;
                        v2 = vNotConnect[random.nextInt(vNotConnect.length)];
                        break;
                    }
                }
                System.out.println("33addedge" + v1 + "===" + v2);

                addEdge(v1, v2);
            }
        }
//        while(true) {
//            int[] vAvailPort = IntStream.range(0, nSwitch).filter(v -> degree(v) < r).toArray();
//            if(vAvailPort.length <= 0) break;
//            else if(vAvailPort.length == 1) {
//                int v = vAvailPort[0];
//
//                //if just only 1 switch with 1 port avail so it is ok. Break loop now
//                if(degree(v) == 1) break;
//
//                //random an edge randomV1, randomV2 and v1, v2 dont have connect to
//                int[] listV = IntStream.range(0, nSwitch).filter(u -> !hasEdge(u, v)).toArray();
//                boolean found = false;
//                while(!found) {
//                    int randomV1 = v;
//                    while (randomV1 == v) {
//                        randomV1 = listV[random.nextInt(listV.length)];
//                    }
//
//                    //for loop to look for right vertex in adj of v1 to
//                    for (int i = 0; i < listV.length; i++) {
//                        if (hasEdge(randomV1, listV[i])) {
//
//                            removeEdge(randomV1, listV[i]);
//                            addEdge(v, randomV1);
//                            addEdge(v, listV[i]);
//                            found = true;
//                            break;
//                        }
//                    }
//                }
//            }
//            else {
//                while (true) {
//                    System.out.println("Fuck la");
//                    int v1 = vAvailPort[random.nextInt(vAvailPort.length)];
//
//                    //list vertex that do not connect with v1 in vAvailport
//                    int[] vNotConnect = Arrays.stream(vAvailPort).filter(v -> !hasEdge(v, v1) && v != v1).toArray();
//                    if (vNotConnect.length == 0) {
//                        continue;
//                    } else {
//                        int v2 = vNotConnect[random.nextInt(vNotConnect.length)];
////                        addEdge(v1, v2);
//                        break;
//                    }
//                }
//            }
//        }

        //add host to switches
        for (int i = nSwitch; i < numV; i++) {
            addEdge(i, (i - nSwitch) / nHostPerSwitch);
        }

        this.hosts();
        this.switches();
    }


    private List<Integer> notNeighborSwitches(int v) {
        List<Integer> listNeighbor = adj[v];
        List<Integer> listNotNeighborSwitches = new ArrayList<>();

        for (int i = 0; i < nSwitch; i++) {
            if (!listNeighbor.contains(i)) listNotNeighborSwitches.add(i);
        }

        return listNotNeighborSwitches;
    }


    @Override
    public List<Integer> hosts() {
        if (hosts != null) return hosts;
        this.hosts = new ArrayList<>();
        for (int i = nSwitch; i < numV; i++) {
            this.hosts.add(i);
        }

        return hosts;
    }

    @Override
    public List<Integer> switches() {
        if (switches != null) return switches;
        this.switches = new ArrayList<>();
        for (int i = 0; i < nSwitch; i++) {
            this.switches.add(i);
        }

        return switches;
    }

    @Override
    public boolean isHostVertex(int v) {
        if (this.hosts.contains(v)) return true;
        return false;
    }

    @Override
    public boolean isSwitchVertex(int v) {
        if (this.switches.contains(v)) return true;
        return false;
    }
}
