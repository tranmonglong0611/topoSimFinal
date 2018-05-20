package simulation;

/**
 * Created by tranmonglong0611 on 26/11/2017.
 */
public class Config {

    //bandwidth of link set to 1gbps
    public static final long LINK_BANDWIDTH = (int)1e9;

    //length of the link 5m
    public static final long LINK_CABLE_LENGTH = 5;

    //packet delay at host 100ns
    public static final long DELAY_AT_HOST = 100 ;

    //packet delay at switch 100ns
    public static final long DELAY_AT_SWITCH = 100;

    //packet size ~ 1mb
    public static final long PACKET_SIZE = (int) 1e6;

    // Velocity of link 0.2 m/ns
    public static final double PROPAGATION_VELOCITY = 1.0 / 5;

    public static final long NUM_PACKET_SEND = 1000;
}
