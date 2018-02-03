package topo;

/**
 * Created by tranmonglong0611 on 29/11/2017.
 */
public class Triplet<T, U, V> {

    private final T first;
    private final U second;
    private final V third;

    public Triplet(T first, U second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst() { return first; }
    public U getSecond() { return second; }
    public V getThird() { return third; }

    public String toString() {
        return first.toString() + "--" + second.toString() + "--" + third.toString();
    }


    public int hashCode()
    {
        return (Integer)first * 200 + (Integer)second * 100 + (Integer)third * 400;
    }

    public boolean equals( Object obj )
    {
        Triplet<T, U, V> temp = (Triplet<T, U, V>) obj;
        if((this.first == temp.first )
                && (this.second == temp.second)
                && (this.third == temp.third))
            return true;
        return false;

    }

}