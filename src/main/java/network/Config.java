package network;

/**
 * Created by tranmonglong0611 on 26/11/2017.
 */
public class Config {

    //bandwidth of link set to 1gbps
    public static final int LINK_BANDWIDTH = (int)1e9;

    //length of the link
    public static final int LINK_CABLE_LENGTH = 5;

    //packet delay at host 100ns
    public static final int DELAY_AT_HOST = 100 ;

    //packet delay at switch 100ns
    public static final int DELAY_AT_SWITCH = 100;

    //packet size ~ 1mb
    public static final int PACKET_SIZE = (int) 1e6;

    // Velocity of link m/ns
    public static final double PROPAGATION_VELOCITY = 1.0 / 5;

    public static final int NUM_PACKET_SEND = 1000;
}
