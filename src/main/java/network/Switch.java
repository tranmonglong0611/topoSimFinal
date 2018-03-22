package network;

import event.Event;
import event.EventSim;
import routing.RoutingAlgorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tranmonglong0611 on 20/11/2017.
 */
public class Switch extends Node {
    public Map<Integer, Link> links = new HashMap<>();
    private RoutingAlgorithm ra;

    public Switch(int id, RoutingAlgorithm ra) {
        super(id);
        this.ra = ra;
    }


//    @Override
//    public void clear() {
//        for (Map.Entry<Integer, Link> link: this.links.entrySet()) {
//            link.getValue().clear();
//        }
//    }

    @Override
    public void process(Packet packet, EventSim sim) {

        long currentTime = sim.getTime();
        int nextId = ra.next(packet.startNode, id, packet.endNode);
//        StdOut.printf("%d %d\n", id, nextId);
        sim.addEvent(new Event(++sim.numEvent, currentTime + Config.DELAY_AT_SWITCH) {
            @Override
            public void execute() {
                links.get(nextId).handle(packet, sim, Switch.this);
            }


            @Override
            public String info() {
                Link link = links.get(nextId);
                int input = Switch.this.id;
                int output = link.u.id + link.v.id - input;

                return packet.toString() + "TransferringAtLink: " + input + " --- " + output;

            }
        });

    }


    public void reset() {
        for(Map.Entry<Integer, Link> link : links.entrySet()) {
            link.getValue().reset();
        }
    }
}
