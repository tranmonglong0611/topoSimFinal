import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import topo.FatTree.FatTreeExp;
import topo.FatTree.FatTreeGraph;
import topo.FatTree.FatTreeRouting;
import topo.SpaceShuffle.SpaceShuffleGraph;
import topo.SpaceShuffle.SpaceShuffleRouting;

public class TestGraph {



    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(FatTreeExp.class.getName());
        SpaceShuffleGraph ftGraph = new SpaceShuffleGraph(10, 7,  2);
        SpaceShuffleRouting ftRouting = new SpaceShuffleRouting(ftGraph);
        logger.info(ftGraph.toString());
    }
}
