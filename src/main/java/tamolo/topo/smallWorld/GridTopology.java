package tamolo.topo.smallWorld;
import tamolo.topo.Topology;

import java.util.ArrayList;
import java.util.List;

public class GridTopology extends Topology {
    public static final int HOST_PER_SWITCH = 1;
    private List<Integer> switches;
    private List<Integer> hosts;

    public final int nRow;
    public final int nCol;
    public final String baseType;
    private final int nHost;
    private final int nSwitch;

    public GridTopology(GridTopology graph) {
        this.numV = graph.getNumV();
        this.nRow = graph.nRow;
        this.nCol = graph.nCol;
        this.baseType = graph.baseType;
        this.nHost = graph.nHost;
        this.nSwitch = graph.nSwitch;
        this.numE = graph.numE;

        adj = (List<Integer>[]) new List[numV];
        for (int v = 0; v < numV; v++) {
            adj[v] = new ArrayList<Integer>();
            // reverse so that adjacency list is in same order as original
//            Stack<Integer> reverse = new Stack<Integer>();
//            for (int w : graph.adj[v]) {
//                reverse.push(w);
//            }
            for (int w : graph.adj[v]) {
                adj[v].add(w);
            }
        }
    }
    public GridTopology(int nRow, int nCol, String baseType) {
        this.nRow = nRow;
        this.nCol = nCol;
        this.baseType = baseType;
        this.nSwitch = nRow * nCol;
        this.nHost = nSwitch * HOST_PER_SWITCH;
        this.numV = nHost + nSwitch;

        adj = (List<Integer>[]) new List[numV];
        for (int v = 0; v < numV; v++) {
            adj[v] = new ArrayList<Integer>();
        }

        // Add edges between switches
        if (baseType.equals("grid")) {
            for (int i = 0; i < nSwitch; i++) {
                int col = i % nCol;
                int row = i / nCol;
                addGridEdges(i, row, col);
            }
        } else if (baseType.equals("torus")) {
            for (int i = 0; i < nSwitch; i++) {
                int col = i % nCol;
                int row = i / nCol;
                addTorusEdges(i, row, col);
            }
        }

        // Add edges between hosts and switch
        int hostId = nSwitch;
        for (int i = 0; i < nSwitch; i++) {
            for (int j = 0; j < HOST_PER_SWITCH; j++) {
                addEdge(i, hostId);
                hostId++;
            }
        }
    }

    @Override
    public List<Integer> hosts() {
        if (hosts != null) return hosts;

        hosts = new ArrayList<>();
        for (int i = nSwitch; i < numV; i++)
            hosts.add(i);

        return hosts;
    }

    @Override
    public List<Integer> switches() {
        if (switches != null) return switches;

        switches= new ArrayList<>();
        for (int i = 0; i < nSwitch; i++)
            switches.add(i);

        return switches;
    }

    @Override
    public String generalInfo() {
        return this.type + "(" + nRow + "," + nCol + "," + baseType + ")";
    }

    @Override
    public boolean isHostVertex(int v) {
        return v >= nSwitch;
    }

    @Override
    public boolean isSwitchVertex(int v) {
        return v < nSwitch;
    }

    public double pathCableLength(List<Integer> path) {
        double length = 0;

        for (int i = 0; i < path.size() - 1; i++)
//            if (isSwitchVertex(path.get(i)) && isSwitchVertex(path.get(i + 1))) {
            length += euclidDistance(path.get(i), path.get(i + 1));
//            }

        return length;
    }

    public List<Integer> getHostsOfSwitch(int sid) {
        List<Integer> result = new ArrayList<>();
        for (int i : adj(sid)) {
            if (hosts().contains(i))
                result.add(i);
        }
        return result;
    }

    public int vertexIndex(int row, int col) {
        return row * this.nCol + col;
    }

    private void addGridEdges(int curr, int row, int col) {
        if (col < nCol - 1)
            addEdge(curr, vertexIndex(row, col + 1));
        if (row < nRow - 1)
            addEdge(curr, vertexIndex(row + 1, col));
    }

    private void addTorusEdges(int curr, int row, int col) {
        addEdge(curr, vertexIndex(row, (col + 1) % nCol));
        addEdge(curr, vertexIndex((row + 1) % nRow, col));
    }

    public int distance(int u, int v) {
        if (baseType.equals("torus")) {
            int ux = u % nCol;
            int uy = u / nCol;
            int vx = v % nCol;
            int vy = v / nCol;

            int dx = Math.abs(ux - vx) <= nCol / 2 ? Math.abs(ux - vx) : nCol - Math.abs(ux - vx);
            int dy = Math.abs(uy - vy) <= nRow / 2 ? Math.abs(uy - vy) : nRow - Math.abs(uy - vy);

            return dx + dy;
        } else {
            int ux = u % nCol;
            int uy = u / nCol;
            int vx = v % nCol;
            int vy = v / nCol;

            return Math.abs(ux - vx) + Math.abs(uy - vy);
        }
    }

    public int manhattanDistance(int u, int v) {
        int ux = u % nCol;
        int uy = u / nCol;
        int vx = v % nCol;
        int vy = v / nCol;
        return Math.abs(ux - vx) + Math.abs(uy - vy);
    }

    public double euclidDistance(int u, int v) {
        int ux = u % nCol;
        int uy = u / nCol;
        int vx = v % nCol;
        int vy = v / nCol;
        return Math.sqrt(Math.pow(ux - vx, 2) + Math.pow(uy - vy, 2));
    }


    /**
     * Returns a string representation of this graph.
     *
     * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
     *         followed by the <em>V</em> adjacency lists
     */
//    public String toString() {
//        StringBuilder s = new StringBuilder();
//        s.append(numV + " vertices, " + numE + " edges \n");
//        int sumDegree = 0;
//
//        for (int v = 0; v < numV; v++) {
//            s.append(String.format("%2d:", v));
//            s.append(String.format(" degree = %2d -- ", degree(v)));
//            sumDegree += degree(v);
//            for (int w : adj[v]) {
//                s.append(String.format(" %2d", w));
//            }
//            s.append("\n");
//        }
//        s.append("\n");
//        s.append(String.format("Average degree = %f", 1.0 * sumDegree / numV));
//        return s.toString();
//    }

    public double totalCableLength() {
        double totalLength = 0;
        for (int u : switches())
            for (int v : adj(u))
                if (isSwitchVertex(v) && u < v) {
                    totalLength += euclidDistance(u, v);
                }

        return totalLength;
    }
}
