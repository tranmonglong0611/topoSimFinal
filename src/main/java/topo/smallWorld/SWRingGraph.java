package topo.smallWorld;

import topo.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class SWRingGraph extends Graph {

    private List<Integer> switches;


    //k must be even. 0 < beta < 1
    public SWRingGraph(int N, int K, double beta) {
        this.numV = N;
        this.numE = N * K / 2;

        adj = (List<Integer>[]) new List[numV];
        for (int v = 0; v < numV; v++) {
            adj[v] = new ArrayList<Integer>();
        }

        //phase1: buildRingLattice
        for (int i = 0; i < numV; i++) {
            for (int j = i + 1; j < numV; j++) {
                int temp = Math.abs(i - j) % (N - 1 - K / 2);
                if (temp <= K / 2 && temp > 0) {
                    addEdge(i, j);
                }
            }
        }

        //phase2: rewrite with beta
        for (int i = 0; i < numV; i++) {
            List<Integer> neighbors = new ArrayList<>(adj[i]);
            neighbors.add(i);
            for (int j = 0; j < neighbors.size(); j++) {
                if (neighbors.get(j) <= i) continue;
                else {
                    if (Math.random() < beta) {

                        List<Integer> replaceNeighbors = getAllValueExcept(this.switches(), neighbors);
                        int randomV = replaceNeighbors.get((int) (Math.random() * replaceNeighbors.size()));

                        removeEdge(i, neighbors.get(j));
                        addEdge(i, randomV);
                    }
                }
            }
        }


        this.hosts();
        this.switches();
    }

    private void buildRingLattice() {

    }

    private void rewriteWithBeta() {

    }

    private List<Integer> getAllValueExcept(List<Integer> allList, List<Integer> exceptList) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < allList.size(); i++) {
            int value = allList.get(i);
            if (!exceptList.contains(value))
                result.add(value);
        }

        return result;
    }


    @Override
    public List<Integer> hosts() {
        return null;
    }

    @Override
    public List<Integer> switches() {
        if (switches != null) return switches;

        else {
            switches = new ArrayList<>();
            for (int i = 0; i < this.numV; i++) {
                switches.add(i);
            }

            return switches;
        }
    }

    @Override
    public boolean isSwitchVertex(int v) {
        return switches.contains(v);
    }

    @Override
    public boolean isHostVertex(int v) {
        return false;
    }
}
