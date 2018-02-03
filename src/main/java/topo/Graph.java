package topo;

import java.util.List;

/**
 * Created by tranmonglong0611 on 20/11/2017.
 */
public abstract class Graph {

    protected int numV;
    protected int numE;
    protected List<Integer>[] adj;

    public void addEdge(int v, int w) {

        numE++;
        adj[v].add(w);
        adj[w].add(v);
    }


    public abstract List<Integer> hosts();
    public abstract List<Integer> switches();
    public List<Integer> adj(int v) { return adj[v]; }


    public abstract boolean isHostVertex(int v);
    public abstract boolean isSwitchVertex(int v);

    public boolean hasEdge(int u, int v) {
        return adj[u].contains(v);
    }

    public int getNumV() {
        return numV;
    }

    public int getNumE() {
        return numE;
    }
    public int degree(int u) {
        return adj[u].size();
    }

    public void removeEdge(int u, int v) {
        if(!hasEdge(u, v)) {
            System.out.println("Edge do not exist to delete");
        }
        else {
            adj[u].remove(v);
            adj[v].remove(u);
            numE--;
        }
    }


    public String toString() {
        String result = "";

        for(int i = 0; i < numV; i++) {
            String temp = i + "-";

            for (int j = 0; j < adj[i].size(); j++) {
                temp += adj[i].get(j) + "-";
            }
            temp += "\n";
            result += temp;
        }
        return result;
    }
}
