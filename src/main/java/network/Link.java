package network;

import event.Event;
import event.EventSim;

/**
 * Created by tranmonglong0611 on 20/11/2017.
 */
public class Link {
    Node u;
    Node v;
    public int bandwidth;
    public long availTime;

    public Link(Node u, Node v) {
        this.u = u;
        this.v = v;
        this.bandwidth = Config.BANDWIDTH;
        this.availTime = 0;
    }

    public long serialLatency(Packet packet) {
        return packet.size / this.bandwidth;
    }

    public void handle(Packet p, EventSim s, Node input) {

        long currentTime = s.getTime();
        Node output = (u == input) ? v : u;

        if(availTime > currentTime) {
            //link is busy
            s.numEvent++;
            s.addEvent(new Event(s.numEvent, availTime){

                @Override
                public void execute() {
                    Link.this.handle(p, s, input);
                }

                @Override
                public String info() {
                    return p.toString() + "\tTransferringAtLink: " + u.id + " --- " +v.id;
                }
            });
        }else {
            //link is avail now
            availTime = currentTime + serialLatency(p);
            s.numEvent++;
            s.addEvent(new Event(s.numEvent, availTime) {
                @Override
                public void execute() {
                    output.process(p, s);
                }

                @Override
                public String info() {
                    return p.toString() + "\tCurrentNode " + output.id;
                }
            });

        }

    }

    public void reset() {
        this.availTime = 0;
    }
}
