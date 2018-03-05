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
        for(int i = 0; i < numV; i++) {
            adj[i] = new ArrayList<>();
        }

        //todo make random jelly fish graph
        Random random = new Random();

        //list vertex still have avail port
        while(true) {
            int[] vAvailPort = IntStream.range(0, nSwitch).filter(v -> degree(v) < r).toArray();
            if(vAvailPort.length <= 0) break;
            else if(vAvailPort.length == 1) {
                int v = vAvailPort[0];

                //if just only 1 switch with 1 port avail so it is ok. Break loop now
                if(degree(v) == 1) break;

                //random an edge randomV1, randomV2 and v1, v2 dont have connect to
                int[] listV = IntStream.range(0, nSwitch).filter(u -> !hasEdge(u, v)).toArray();
                boolean found = false;
                while(!found) {
                    int randomV1 = v;
                    while (randomV1 == v) {
                        randomV1 = listV[random.nextInt(listV.length)];
                    }

                    //for loop to look for right vertex in adj of v1 to
                    for (int i = 0; i < listV.length; i++) {
                        if (hasEdge(randomV1, listV[i])) {

                            removeEdge(randomV1, listV[i]);
                            addEdge(v, randomV1);
                            addEdge(v, listV[i]);
                            found = true;
                            break;
                        }
                    }
                }
            }
            else {
                while (true) {
                    System.out.println("Fuck la");
                    int v1 = vAvailPort[random.nextInt(vAvailPort.length)];

                    //list vertex that do not connect with v1 in vAvailport
                    int[] vNotConnect = Arrays.stream(vAvailPort).filter(v -> !hasEdge(v, v1) && v != v1).toArray();
                    if (vNotConnect.length == 0) {
                        continue;
                    } else {
                        int v2 = vNotConnect[random.nextInt(vNotConnect.length)];
                        addEdge(v1, v2);
                        break;
                    }
                }
            }
        }


    }



    @Override
    public List<Integer> hosts() {
        if(hosts != null) return hosts;
        this.hosts = new ArrayList<>();
        for(int i = nSwitch; i < numV; i++) {
            this.hosts.add(i);
        }

        return hosts;
    }

    @Override
    public List<Integer> switches() {
        if(switches != null) return switches;
        this.switches = new ArrayList<>();
        for(int i = 0; i < nSwitch; i++) {
            this.switches.add(i);
        }

        return switches;
    }

    @Override
    public boolean isHostVertex(int v) {
        if(this.hosts.contains(v) ) return true;
        return false;
    }

    @Override
    public boolean isSwitchVertex(int v) {
        if(this.switches.contains(v)) return true;
        return false;
    }
}
