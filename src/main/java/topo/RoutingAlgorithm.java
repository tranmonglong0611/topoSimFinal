package topo;

/**
 * Created by tranmonglong0611 on 29/11/2017.
 */
public abstract class RoutingAlgorithm {

    public abstract int next(int source, int current, int destination);
    public abstract RoutingPath path(int source, int destination);
}
