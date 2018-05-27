package topo.fatTree;

import topo.Topology;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tranmonglong0611 on 20/11/2017.
 */
public class FatTreeTopology extends Topology {
    public static final int CORE = 1;
    public static final int AGG  = 2;
    public static final int EDGE = 3;


    private int k;
    private int numServer;
    private int numCoreSwitch;
    private int numEdgeSwitch;
    private int numAgreeSwitch;

    public int numSwitchEachPod;

    private List<Integer> switches;
    private List<Integer> hosts;
    private Address[] address;

    public FatTreeTopology(int k){
        this.type = "FatTree";
        this.k = k;
        numServer = k * k * k / 4;
        numCoreSwitch = k * k / 4;
        numEdgeSwitch = k * k / 2;
        numAgreeSwitch = k * k /2;


        this.numV = numServer + numCoreSwitch + numEdgeSwitch + numAgreeSwitch;
        this.numE = 0;

        adj = (List<Integer>[]) new List[numV];
        for (int v = 0; v < numV; v++) {
            adj[v] = new ArrayList<Integer>();
        }

        // each pod has k^2/4 servers and k switches
        int numEachPod = k * k / 4 + k;
        numSwitchEachPod = numEachPod;
        for (int p = 0; p < k; p++) {
            int offset = numEachPod * p;

            // between server and edge
            for (int e = 0; e < k / 2; e++) {
                int edgeSwitch = offset + k * k / 4 + e;
                for (int s = 0; s < k / 2; s++) {
                    int server = offset + e * k / 2 + s;
                    addEdge(edgeSwitch, server);
                }
            }

            // between agg and edge
            for (int e = 0; e < k / 2; e++) {
                int edgeSwitch = offset + k * k / 4 + e;
                for (int a = k / 2; a < k; a++) {
                    int aggSwitch = offset + k * k / 4 + a;
                    addEdge(edgeSwitch, aggSwitch);
                }
            }

            // between agg and core
            for (int a = 0; a < k / 2; a++) {
                int aggSwitch = offset + k * k / 4 + k / 2 + a;
                for (int c = 0; c < k / 2; c++) {
                    int coreSwitch = a * k / 2 + c + numAgreeSwitch + numEdgeSwitch + numServer;
                    addEdge(aggSwitch, coreSwitch);
                }
            }

        }

        this.buildAddress();
        this.hosts();
        this.switches();
    }

    private void buildAddress() {
        this.address = new Address[numV];

        int numEachPod = k * k / 4 + k;

        // address for pod's switches
        for (int p = 0; p < k; p++) {
            int offset = numEachPod * p;
            for (int s = 0; s < k; s++) {
                int switchId = offset + k * k / 4 + s;
                address[switchId] = new Address(10, p, s, 1);
            }
        }

        // address for core switches
        for (int j = 1; j <= k / 2; j++) {
            for (int i = 1; i <= k / 2; i++) {
                int offset = numAgreeSwitch + numEdgeSwitch + numServer;
                int switchId = offset + (j - 1) * k / 2 + i - 1;
                address[switchId] = new Address(10, k, j, i);
            }
        }

        // address for servers
        for (int p = 0; p < k; p++) {
            int offset = numEachPod * p;
            for (int e = 0; e < k / 2; e++) {
                for (int h = 2; h <= k / 2 + 1; h++) {
                    int serverId = offset + e * k / 2 + h - 2;
                    address[serverId] = new Address(10, p, e, h);
                }
            }
        }
    }

    public int getK() {
        return k;
    }

    public Address getAddress(int u) {
        return address[u];
    }

    @Override
    public List<Integer>hosts() {
        if (hosts != null) return hosts;
        hosts = new ArrayList<>();

        int numEachPod = k * k / 4 + k;
        for (int p = 0; p < k; p++) {
            int offset = numEachPod * p;
            for (int e = 0; e < k / 2; e++) {
                for (int h = 0; h < k / 2; h++) {
                    int serverId = offset + e * k / 2 + h;
                    hosts.add(serverId);
                }
            }
        }

        return hosts;
    }

    @Override
    public List<Integer> switches() {
        if(switches != null) return switches;
        switches = new ArrayList<>();

        for(int i = 0; i < numV; i++) {
            if(hosts.contains(i)) continue;
            switches.add(i);
        }

        return switches;
    }

    @Override
    public String generalInfo() {
        return this.type + "(" + this.k + ")";
    }

    public boolean isHostVertex(int u) {
        return hosts.contains(u);
    }

    public boolean isSwitchVertex(int u) {
        return !isHostVertex(u);
    }

    public int switchType(int u) {
        int numEachPod = k * k / 4 + k;
        if (u >= k * numEachPod) return CORE;
        else {
            int os = u % numEachPod;
            if (os >= k * k / 4 + k / 2) return AGG;
            else return EDGE;
        }
    }

    //ust for all vertex type except core switch. Core switch does not belong to pod
    public int podBelongTo(int vertex) {
        return vertex / numSwitchEachPod;
    }
}
