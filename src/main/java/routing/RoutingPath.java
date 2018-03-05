package routing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tranmonglong0611 on 29/11/2017.
 */
public class RoutingPath {

    public List<Integer> path;

    public RoutingPath() {
        path = new ArrayList<>();
    }
    public String toString() {
        String result = "";
        for(int i = 0; i < path.size(); i++) {
            result += (path.get(i) + "--");
        }
        return result;
    }
}
