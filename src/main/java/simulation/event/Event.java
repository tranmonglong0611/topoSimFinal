package simulation.event;

/**
 * Created by tranmonglong0611 on 20/11/2017.
 */
public abstract class Event {

    public long timeStart;
    long id;


    public Event(long id, long timeStart) {
        this.timeStart = timeStart;
        this.id = id;
    }

    public abstract void execute();
    public abstract String info();

}
