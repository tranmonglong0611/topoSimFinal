package topo;

import java.util.*;

/**
 * Created by tranmonglong0611 on 20/11/2017.
 */
public abstract class Graph {

    protected int numV;
    protected int numE;
    protected List<Integer>[] adj;

    public void addEdge(int v, int w) {

        if(hasEdge(v, w)) return;
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

            adj[u].remove(adj[u].indexOf(v));
            adj[v].remove(adj[v].indexOf(u));
            numE--;
        }
    }
    public List<Integer> shortestPath(int u, int v) {
        Queue<Integer> queue = new LinkedList<Integer>();
        List<Integer> path = new ArrayList<>();
        boolean[] visited = new boolean[this.numV];
        int[] trace = new int[this.numV];
        queue.add(u);
        visited[u] = true;
        trace[u] = -1;
        while(!queue.isEmpty()) {
            int uNode = queue.remove();
            if (uNode == v) {
                path.add(v);
                while(trace[v] != -1) {
                    v = trace[v];
                    path.add(v);
                }
                Collections.reverse(path);
                break;
            }

            for (int vNode:this.adj(uNode)) {
                if (!visited[vNode]) {
                    visited[vNode] = true;
                    trace[vNode] = uNode;
                    queue.add(vNode);
                }
            }
        }
        return path;
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
