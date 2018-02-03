package network;

/**
 * Created by tranmonglong0611 on 20/11/2017.
 */
public class Packet {

    public int size;
    public int startNode;
    public int endNode;
    public long startTime;
    public long endTime;

    public Packet(int startNode, int endNode, long startTime) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.size = Config.PACKET_SIZE;
        this.startTime = startTime;
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
