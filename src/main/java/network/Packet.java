package network;

import routing.RoutingPath;

/**
 * Created by tranmonglong0611 on 20/11/2017.
 */
public class Packet {

    public int size;
    public int startNode;
    public int endNode;
    public long startTime;
    public long endTime;
    public RoutingPath routingPath;

    public Packet(int startNode, int endNode, long startTime) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.size = Config.PACKET_SIZE;
        this.startTime = startTime;
        this.routingPath = null;
    }

    //used for jellyfish. Path need to be insert into the packet
    public Packet(int startNode, int endNode, long startTime, RoutingPath path) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.size = Config.PACKET_SIZE;
        this.startTime = startTime;
        this.routingPath = path;
    }




    public long getTravelTime() {
        return endTime - startTime;
    }

    public String toString() {
        String hehe = "" + this.startNode;
        if(this.startNode < 10) {
            hehe = "0" + this.startNode;
        }
        return "\n" + "StartNode:" + hehe +
                "\t\tEndNode: " + this.endNode + "\t\t";
    }

}
