import topo.routing.RoutingAlgorithm;
import topo.TheoryParam;
import topo.Topology;
import topo.jellyFish.AllShortestPathRouting;
import topo.jellyFish.JellyFishTopology;

/*
    author tamolo
    date 5/15/18
*/
public class ThuNghiemThatNe {

    public static void main(String[] args) {
        JellyFishTopology topo = new JellyFishTopology(300, 8 , 4);
        AllShortestPathRouting routing = new AllShortestPathRouting(topo);
        TheoryParam theoryParam = new TheoryParam(topo, routing);
    }


    private void temp(Topology topo, RoutingAlgorithm routing) {
        TheoryParam theoryParam = new TheoryParam(topo, routing);
    }
}
