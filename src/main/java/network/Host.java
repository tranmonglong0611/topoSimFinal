package network;

import common.Format;
import event.Event;
import event.EventSim;
import output.OutFile;
import routing.RoutingAlgorithm;

/**
 * Created by tranmonglong0611 on 20/11/2017.
 */
public class Host extends Node{
    public Link link;
    private RoutingAlgorithm ra;
    public Host(int id, RoutingAlgorithm ra) {
        super(id);
        this.ra = ra;
    }

    @Override
    public void process(Packet packet, EventSim s) {
        long sTime = s.getTime();

        if(this.id == packet.endNode) {
            packet.endTime = sTime;
            s.numReceived++;
            s.totalTimePacketTravel += packet.getTravelTime();

            if(s.isTracing) {
                OutFile.getFile().append("\nDone Send From " + packet.startNode + " to " + packet.endNode);
                OutFile.getFile().append("\nTime Travel " + packet.getTravelTime() + "\n");
            }
            return;
        }

        s.numSent++;
        s.numEvent++;

        int nextId;

        if(packet.routingPath == null) {
            nextId = ra.next(packet.startNode, id, packet.endNode);
        }else {
        //for just jellyfish experiment
            nextId = packet.routingPath.path.get(packet.routingPath.path.indexOf(this.id) + 1);
        }

        s.addEvent(new Event(s.numEvent, sTime + Config.DELAY_AT_HOST) {

            @Override
            public void execute() {
                if(nextId != -1) {
                    link.handle(packet, s, Host.this);
                }else {
                    if(s.isTracing)
                        OutFile.getFile().append("\nCan Not Send Packet From: " + packet.startNode + " to " + packet.endNode +"\n");
                }
            }

            @Override
            public String info() {
                int input = Host.this.id;
                int output = link.u.id + link.v.id - input;
                if(nextId != -1)
                    return String.format(Format.LeftAlignFormat, packet.startNode, packet.endNode, "Transferring At Link: " + input + " --- " + output, this.timeStart);
                else
                    return String.format(Format.LeftAlignFormat, packet.startNode, packet.endNode, "Error because of node: " +  output, this.timeStart);
            }
        });
    }

    public void reset() {
        link.reset();
    }
}
