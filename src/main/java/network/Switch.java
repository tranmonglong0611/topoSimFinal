package network;

import common.Format;
import event.Event;
import event.EventSim;
import output.OutFile;
import routing.RoutingAlgorithm;
import routing.RoutingPath;

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
        int nextId;


        if(packet.routingPath == null) {
            nextId = ra.next(packet.startNode, id, packet.endNode);
        }
        //for just jellyfish experiment
        else {
            nextId = packet.routingPath.path.get(packet.routingPath.path.indexOf(this.id) + 1);
        }

        sim.addEvent(new Event(++sim.numEvent, currentTime + Config.DELAY_AT_SWITCH) {
            @Override
            public void execute() {
                if(nextId != -1) {
                    links.get(nextId).handle(packet, sim, Switch.this);
                } else {
                    if(sim.isTracing)
                        OutFile.getFile().append("\nCan Not Send Packet From: " + packet.startNode + " to " + packet.endNode + "\n");
                }
            }


            @Override
            public String info() {
                if(nextId != -1) {
                    Link link = links.get(nextId);
                    int input = Switch.this.id;
                    int output = link.u.id + link.v.id - input;
                    return String.format(Format.LeftAlignFormat, packet.startNode, packet.endNode, "Transferring At Link: " + input + " --- " + output, this.timeStart);
                } else {
                    return String.format(Format.LeftAlignFormat, packet.startNode, packet.endNode, "Error" , this.timeStart);
                }

            }
        });

    }


    public void reset() {
        for(Map.Entry<Integer, Link> link : links.entrySet()) {
            link.getValue().reset();
        }
    }

    //set the switch to be error
    public void setError() {

    }
}
