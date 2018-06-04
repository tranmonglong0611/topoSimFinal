import tamolo.topo.fatTree.FatTreeRouting;
import tamolo.topo.fatTree.FatTreeTopology;
/*
    author tamolo
    date 5/1/18
*/
public class Trash {
    public static void main(String[] args) {
        FatTreeTopology topo = new FatTreeTopology(4);
        FatTreeRouting routing = new FatTreeRouting(topo);
        Experiment.doSim(topo, routing, 100, true, true, true);
    }

}
