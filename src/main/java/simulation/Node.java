package simulation;

import simulation.event.EventSim;

/**
 * Created by tranmonglong0611 on 20/11/2017.
 */
public abstract class Node {

    public int id;

    public Node(int id) {
        this.id = id;
    }
    public abstract void process(Packet packet, EventSim e);
}
