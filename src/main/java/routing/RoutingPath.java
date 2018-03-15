package routing;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tranmonglong0611 on 29/11/2017.
 */
public class RoutingPath implements Cloneable, Comparable<RoutingPath>{

    public List<Integer> path;

    public RoutingPath() {
        path = new ArrayList<>();
    }


    public RoutingPath(List<Integer> list) {
        this.path = list;
    }

    public int size() {
        return path.size();
    }

    public int get(int i ) {
        return path.get(i);
    }

    //get the new path until i th (exclusive i element)
    public RoutingPath cloneTo(int i) {

        List<Integer> list = new ArrayList<>();
        for(int j = 0; j < i; j++) {
            list.add(path.get(j));
        }


        return new RoutingPath(list);
    }


    //get i th edge in path
    public Pair<Integer, Integer> getEdge(int i) {
        return new Pair(path.get(i), path.get(i+1));
    }

    public List<Pair<Integer, Integer>> getEdges() {
        List<Pair<Integer, Integer>> result = new ArrayList<>();

        for(int i = 0; i < path.size() - 1; i++) {
            result.add(getEdge(i));
        }
        return result;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void addPath(RoutingPath p2) {
        this.path.addAll(p2.path);
    }

    public String toString() {
        String result = "";
        for(int a : path) {
            result += "--" + a;
        }

        return result;
    }

    public int compareTo(RoutingPath path2) {
        return Integer.compare(this.size(), path2.size());
//        if (this.size() == path2.size())
//            return 0;
//        if (this.size() > path2.size())
//            return 1;
//        return -1;
    }

    public boolean isSame(RoutingPath path2) {
        if(this.path.size() != path2.path.size()) {
            return false;
        }else {
            for(int i = 0; i < this.path.size(); i++) {
                if(this.path.get(i) != path2.path.get(i))
                    return false;
            }

            return true;
        }
    }

}
