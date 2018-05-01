package event;

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
    public int numReceived;
    public int numSent;
    public int numEvent;
    public int totalTimePacketTravel;



    //single even queue
    PriorityQueue<Event> que;


    public EventSim(long timeLimit) {
        this.timeLimit = timeLimit;
        que = new PriorityQueue<>((e1, e2) -> {
           if(e1.timeStart < e2.timeStart) return -1;
           else if(e1.timeStart > e2.timeStart) return 1;
           else return 0;
        });
    }

    public void process() {

        while(que.size() > 0) {
            Iterator ite = que.iterator();
            while(ite.hasNext()) {
                Event pk = (Event)ite.next();
                OutFile.getFile().append(pk.info() + "\tEventTime " +  pk.timeStart);
//                System.out.println(pk.info() + "\tEventTime " +  pk.timeStart);
            }

            OutFile.getFile().append("\n===============");
//            System.out.printf("=================");
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



}
