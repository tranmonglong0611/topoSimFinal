package network;

import event.Event;
import event.EventSim;
import output.OutFile;

/**
 * Created by tranmonglong0611 on 20/11/2017.
 */
public class Host extends Node{

    public Link link;

    public Host(int id) {
        super(id);
    }

    @Override
    public void process(Packet packet, EventSim s) {
        long sTime = s.getTime();

        if(this.id == packet.endNode) {
            packet.endTime = sTime;
            s.numReceived++;
            s.totalTimePacketTravel += packet.getTravelTime();
            OutFile.getFile().append("Done Send From " + packet.startNode + " to " + packet.endNode);
            OutFile.getFile().append("TOtal Time Travel " + s.totalTimePacketTravel);
            return;
        }

        s.numSent++;
        s.numEvent++;
        s.addEvent(new Event(s.numEvent, sTime + Config.DELAY_AT_HOST) {

            @Override
            public void execute() {
                link.handle(packet, s, Host.this);
            }


            @Override
            public String info() {
                int input = Host.this.id;
                int output = link.u.id + link.v.id - input;
                return packet.toString() + "TransferringAtLink: " + input + " --- " + output;

            }
        });
    }

    public void reset() {
        link.reset();
    }
}
