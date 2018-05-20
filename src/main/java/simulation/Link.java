package simulation;

import common.Format;
import simulation.event.Event;
import simulation.event.EventSim;

/**
 * Created by tranmonglong0611 on 20/11/2017.
 */
public class Link {
    public Node u;
    public Node v;
    public long bandwidth;
    public long availTime;
    public long cableLength;

    public Link(Node u, Node v) {
        this.u = u;
        this.v = v;
        this.bandwidth = Config.LINK_BANDWIDTH;
        this.availTime = 0;
        this.cableLength = Config.LINK_CABLE_LENGTH;
    }

    public long serialLatency(Packet packet) {
        return (long)(1e9 * packet.size / this.bandwidth);
    }
    public long propagationLatency() {
        return (long)(cableLength / Config.PROPAGATION_VELOCITY);
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
//                    return p.toString() + "\tDelay at link: " + u.id + " --- " +v.id;
                    return String.format(Format.LeftAlignFormat, p.startNode, p.endNode, "Delay at link: " + u.id + " --- " +v.id, this.timeStart);
                }
            });
        }else {
            //link is avail now
            availTime = currentTime + serialLatency(p) + propagationLatency();
            s.numEvent++;
            s.addEvent(new Event(s.numEvent, availTime) {
                @Override
                public void execute() {
                    output.process(p, s);
                }

                @Override
                public String info() {
                    return String.format(Format.LeftAlignFormat, p.startNode, p.endNode, "Delay At Node " + output.id, this.timeStart);
//                    return p.toString() + "\tDelay At Node " + output.id;
                }
            });

        }

    }

    public void reset() {
        this.availTime = 0;
    }
}
