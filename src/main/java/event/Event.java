package event;

/**
 * Created by tranmonglong0611 on 20/11/2017.
 */
public abstract class Event {

    long timeStart;
    int id;

    public Event(int id, long timeStart) {
        this.timeStart = timeStart;
        this.id = id;
    }

    public abstract void execute();
    public abstract String info();

}
