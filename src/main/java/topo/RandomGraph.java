package topo;

import java.util.ArrayList;
import java.util.List;

/*
    author tamolo
    date 5/3/18
*/
public class RandomGraph extends Topology {
    public RandomGraph(int numV) {
        this.numV = numV;
        adj = (List<Integer>[]) new List[numV];
        for (int v = 0; v < numV; v++) {
            adj[v] = new ArrayList<Integer>();
        }
    }
    public RandomGraph() {
        this.numV = 9;
        adj = (List<Integer>[]) new List[numV];
        for (int v = 0; v < numV; v++) {
            adj[v] = new ArrayList<Integer>();
        }

        this.addEdge(0, 1);
        this.addEdge(1, 2);
        this.addEdge(2, 3);
        this.addEdge(3, 4);
        this.addEdge(4, 5);
        this.addEdge(5, 6);
        this.addEdge(6, 7);
        this.addEdge(7, 0);
        this.addEdge(2, 8);
        this.addEdge(8, 6);
        this.addEdge(8, 7);
        this.addEdge(1, 7);
        this.addEdge(2, 5);
        this.addEdge(3, 5);
    }

    @Override
    public List<Integer> hosts() {
        return null;
    }

    @Override
    public List<Integer> switches() {
        return null;
    }

    @Override
    public boolean isHostVertex(int v) {
        return false;
    }

    @Override
    public boolean isSwitchVertex(int v) {
        return false;
    }

    public static void main (String[] args) {
        RandomGraph a = new RandomGraph();
        System.out.println(a.toString());
        List<List<Integer>> temp = a.allShortestPath(0, 4);

        System.out.println("===============");
        for(List<Integer> list : temp) {
            System.out.println("------");
            for(int e : list) {
                System.out.print(e + "->");
            }
            System.out.println();
        }

    }
}
