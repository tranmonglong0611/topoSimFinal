package tamolo.topo;

import tamolo.common.Format;
import javafx.util.Pair;

import java.io.*;
import java.util.*;

/**
 * Created by tranmonglong0611 on 20/11/2017.
 */
public abstract class Topology implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    protected int numV;
    protected int numE;
    protected List<Integer>[] adj;
    public String type;

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

    public abstract String generalInfo();

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
        } else {

            adj[u].remove(adj[u].indexOf(v));
            adj[v].remove(adj[v].indexOf(u));
            numE--;
        }
    }
    private void getAllPathFromParent(int source, int des, List<List<Integer>> result, Map<Integer, List<Integer>> parents, ArrayList<Integer> path) {

        path.add(0, des);

        if(des == source) {
            result.add(new ArrayList<>(path));
        }
        for(int xxx : parents.get(des)) {
            getAllPathFromParent(source, xxx, result, parents, path);
        }
        path.remove(0);
    }

    //https://www.quora.com/Can-I-get-all-the-shortest-paths-from-source-node-to-destination-in-graph-using-bfs
    public List<List<Integer>> allShortestPath(int u, int v) {
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, List<Integer>> parents = new HashMap<>();

        int[] distance = new int[this.numV];
        Arrays.fill(distance, Integer.MAX_VALUE);

        queue.add(u);
        distance[u] = 0;
        parents.put(u, new ArrayList<Integer>());

        while(!queue.isEmpty()) {
            int uNode = queue.remove();
            if(uNode == v) {
                break;
            }
            for(int neighbor : this.adj[uNode]) {
                if(distance[uNode] + 1 < distance[neighbor]) {
                    queue.add(neighbor);
                    if(parents.get(neighbor) == null) {
                        parents.put(neighbor, new ArrayList<Integer>(){{add(uNode);}});
                    }else {
                        parents.get(neighbor).add(uNode);
                    }
                    distance[neighbor] = distance[uNode] + 1;
                }else if(distance[uNode] + 1 == distance[neighbor]) {
                    if(parents.get(neighbor) == null) {
                        parents.put(neighbor, new ArrayList<Integer>(){{add(uNode);}});
                    }else {
                        parents.get(neighbor).add(uNode);
                    }
                }else {
                }
            }
        }

//        for(Map.Entry<Integer, List<Integer>> entry : parents.entrySet()) {
//            System.out.println("NOde: " + entry.getKey());
//            List<Integer> a = entry.getValue();
//            System.out.println();
//            for(int i : a) {
//                System.out.pri + "-");
//            }
//            System.out.println();
//        }
        List<List<Integer>> result = new ArrayList<>();
        getAllPathFromParent(u, v, result, parents, new ArrayList<Integer>());
        return result;
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Topology readFromFile(String path) {
        Topology a = null;
        try {
            FileInputStream f = new FileInputStream(new File(path));
            ObjectInputStream o = new ObjectInputStream(f);
            // Write objects to file
            a = (Topology) o.readObject();
            o.close();
            f.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }


    public String toString() {
        String result = "";
        result += String.format("+-------------------------------------------------------------------------------------------------+%n");
        result += String.format("|                          Topology Info                                                          |%n");
        result += String.format("+-------------------------------------------------------------------------------------------------+%n");
        result += String.format("|                                 %-63s |%n", this.generalInfo());
        result += String.format("+-------------------------------------------------------------------------------------------------+%n");

        result += String.format("| Vertex               | Type                 |                 Adjacency Vertex                  |%n");
        result += String.format("+-------------------------------------------------------------------------------------------------+%n");


        for (int i = 0; i < numV; i++) {
            String vertex = Integer.toString(i);

            String adjacency = "";
            for (int j = 0; j < adj[i].size() - 1; j++) {
                adjacency += adj[i].get(j) + " - ";
            }
            adjacency += adj[i].get(adj[i].size() - 1);

            String type = isHostVertex(i) ? "Host" : "Switch";

            result += String.format(Format.GraphFormat, vertex, type, adjacency);
            result += String.format("+-------------------------------------------------------------------------------------------------+%n");
        }
        return result;
    }

    public String graphInfo() {
        String result = "";
        String format = "%-50s%-20s%n";
        result += String.format(format, "Type                         :        ", this.type);
        result += String.format(format, "Number of vertexs  :        ", Integer.toString(this.numV));
        result += String.format(format, "Number of edges    :        ", Integer.toString(this.numE));

        return result;
    }


    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
