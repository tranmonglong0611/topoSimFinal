package event;

import network.Config;
import network.Packet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import output.OutFile;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * Created by tranmonglong0611 on 20/11/2017.
 */
public class EventSim {

    private long systemTime = 0;
    public long timeLimit;
    public long numReceived;
    public long numSent;
    public long numEvent;
    public long totalTimePacketTravel;
    public boolean isTracing;



    //single even queue
    PriorityQueue<Event> que;


    public EventSim(long timeLimit, boolean isTracing) {
        this.timeLimit = timeLimit;
        this.isTracing = isTracing;
        que = new PriorityQueue<>((e1, e2) -> {
           if(e1.timeStart < e2.timeStart) return -1;
           else if(e1.timeStart > e2.timeStart) return 1;
           else return 0;
        });
    }

    public void process() {

        while(que.size() > 0) {
            Iterator ite = que.iterator();

            if(this.isTracing) {
                OutFile.getFile().format("+-----------------+-----------------+-----------------------------------------------+----------------+%n");
                OutFile.getFile().format("| Start Node      | End Node        | Info                                          | EventTime      |%n");
                OutFile.getFile().format("+-----------------+-----------------+-----------------------------------------------+----------------+%n");
            }

            while(ite.hasNext()) {
                Event event = (Event)ite.next();
                if(this.isTracing)
                    OutFile.getFile().append(event.info());
            }


            if(this.isTracing) {
                OutFile.getFile().format("+-----------------+-----------------+-----------------------------------------------+----------------+%n");
                OutFile.getFile().append("\n\n\n");
            }
            if(timeLimit < systemTime ){
                break;
            }
            Event e = que.poll();
            systemTime = e.timeStart;
            e.execute();
        }
    }

    public void addEvent(Event e) {
        que.add(e);
    }

    public long getTime() {
        return this.systemTime;
    }
    public void setTime(long time) {
        this.systemTime = time;
    }


    public long averagePacketTravel() {
        System.out.println(totalTimePacketTravel);
        System.out.println(numReceived);
        long averageTime = totalTimePacketTravel / numReceived;
        return averageTime;
    }

//    Throughput (bits/sec)= sum (number of successful packets)*(average packet_size))/Total Time sent in delivering that amount of data.
    public double throughput() {
        long receivedByte = numReceived * Config.PACKET_SIZE;
        return (receivedByte * 1.0 / systemTime) * 1e9;
    }
}
