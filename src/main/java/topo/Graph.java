package topo;

import common.Format;
import javafx.util.Pair;

import java.io.*;
import java.util.*;

/**
 * Created by tranmonglong0611 on 20/11/2017.
 */
public abstract class Graph implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    protected int numV;
    protected int numE;
    protected List<Integer>[] adj;

    public void addEdge(int v, int w) {

        if (adj[v] == null) {
            adj[v] = new ArrayList<>();
        }
        if (adj[w] == null) {
            adj[w] = new ArrayList<>();
        }
        if (hasEdge(v, w) ) {
            return;
        }

        numE++;
        adj[v].add(w);
        adj[w].add(v);
    }


    public abstract List<Integer> hosts();

    public abstract List<Integer> switches();

    public List<Integer> adj(int v) {
        return adj[v];
    }


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

    public List<Pair<Integer, Integer>> removeNode(int u) {

        List<Pair<Integer, Integer>> result = new ArrayList<>();

        for(int i = 0; i < adj(u).size(); i++) {
            result.add(new Pair(u, adj(u).get(i)));
        }
        while(adj(u).size() > 0) {
            removeEdge(u, adj(u).get(0));
        }
        return result;

    }

    public void removeEdge(int u, int v) {
        if (!hasEdge(u, v)) {
//            System.out.println("Edge" + u + "--" + v + "do not exist to delete");
        } else {

            adj[u].remove(adj[u].indexOf(v));
            adj[v].remove(adj[v].indexOf(u));
            numE--;
        }
    }

    //using BFS
    public List<Integer> shortestPath(int u, int v) {
        Queue<Integer> queue = new LinkedList<Integer>();
        List<Integer> path = new ArrayList<>();
        boolean[] visited = new boolean[this.numV];
        int[] trace = new int[this.numV];
        queue.add(u);
        visited[u] = true;
        trace[u] = -1;

        while (!queue.isEmpty()) {
            int uNode = queue.remove();
            if (uNode == v) {
                path.add(v);
                while (trace[v] != -1) {
                    v = trace[v];
                    path.add(v);
                }
                Collections.reverse(path);
                break;
            }

//            for(int i = 0; i < this.adj(uNode).size(); i++) {
//                System.out.print(this.adj(uNode).get(i) + "----");
//            }
//            System.out.println();

            for (int vNode : this.adj(uNode)) {
                if (!visited[vNode]) {
                    visited[vNode] = true;
                    trace[vNode] = uNode;
                    queue.add(vNode);
                }
            }
        }

        return path;
    }


    public void writeToFile(String path) {

        try {
            FileOutputStream f = new FileOutputStream(new File(path));
            ObjectOutputStream o = new ObjectOutputStream(f);


            // Write objects to file
            o.writeObject(this);

            o.close();
            f.close();


        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }


    public String toString() {
        String result = "";
        result += String.format("+--------------------------------------------------------------------------+%n");
        result += String.format("|                          Graph Info                                      |%n");
        result += String.format("+--------------------------------------------------------------------------+%n");
        result += String.format("| Vertex               | Adjacency Vertex                                  |%n");
        result += String.format("+--------------------------------------------------------------------------+%n");


        for (int i = 0; i < numV; i++) {
            String vertex = Integer.toString(i);

            String adjacency = "";
            for (int j = 0; j < adj[i].size() - 1; j++) {
                adjacency += adj[i].get(j) + " - ";
            }
            adjacency += adj[i].get(adj[i].size() - 1);

            result += String.format(Format.GraphFormat, vertex, adjacency);
            result += String.format("+--------------------------------------------------------------------------+%n");
        }
        return result;
    }


    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
