package topo.jellyFish;

import common.Knuth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    Logger logger = LogManager.getLogger(JellyFishGraph.class.getName());

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

        // make graph connected
        int[] intArray = IntStream.rangeClosed(0, nSwitch - 1).toArray();
        Integer[] listSwitchSuffle = Arrays.stream(intArray).boxed().toArray(Integer[]::new);
        Knuth.shuffle(listSwitchSuffle);
        addEdge(listSwitchSuffle[0], listSwitchSuffle[1]);
        for (int i = 2; i < listSwitchSuffle.length; i++) {
            int[] switchAvail = IntStream.range(0, i - 1).filter(v -> degree(v) < r).toArray();
            if (switchAvail.length < 1) {

                logger.error("No switch avail to connect");
                break;
            } else {
                int randomSwitch = switchAvail[random.nextInt(switchAvail.length)];
                addEdge(i, randomSwitch);
            }
        }


        //list vertex still have avail port
        while (true) {
//            System.out.println("hehe");
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
                            addEdge(vertexAvail[0], v1);
                            addEdge(vertexAvail[0], v2);
                            break;
                        }
                    }
                    if (isReplaced) break;
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

                            addEdge(v1, vertexAvail[0]);
                            addEdge(v2, vertexAvail[1]);
                            break;
                        }
                    }
                    if (isReplaced) break;
                }

            } else {
                int v1;
                int v2;
                while (true) {
                    int rv1 = vertexAvail[random.nextInt(vertexAvail.length)];
                    int[] vNotConnect = Arrays.stream(vertexAvail).filter(v -> !hasEdge(v, rv1) && v != rv1).toArray();
                    if (vNotConnect.length == 0) {
                        continue;
                    } else {
                        v1 = rv1;
                        v2 = vNotConnect[random.nextInt(vNotConnect.length)];
                        break;
                    }
                }
                addEdge(v1, v2);
            }
        }

        System.out.println("done here");
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
