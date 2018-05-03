package routing;

import java.util.HashMap;
import java.util.Map;

/*
    author tamolo
    date 3/22/18
*/
public class  RoutingTable {
    private HashMap<Integer, Integer> table;

    public RoutingTable() {
        table = new HashMap<Integer, Integer>();
    }

    public HashMap<Integer, Integer> getTable() {
        return table;
    }

    public void addRoute(int destination, int nextHop) {
        table.put(destination, nextHop);
    }

    public int getNextNode(int u) {
        return table.get(u);
    }

    public int size() {
        return table.size();
    }

    public String toString() {
        String result = "";
        for(Map.Entry<Integer, Integer> map : table.entrySet()) {
            result += "(" +  map.getKey() + "-" + map.getValue() + ")";
        }
        return result;
    }
}
