package topo;

import org.junit.jupiter.api.Test;
import topo.fatTree.FatTreeTopology;
import topo.fatTree.FatTreeRouting;
import topo.jellyFish.JellyFishTopology;
import topo.jellyFish.K_ShortestPathRouting;
import topo.smallWorld.SmallWorldTopology;
import topo.spaceShuffle.SpaceShuffleTopology;
import topo.spaceShuffle.SpaceShuffleRouting;

import static org.junit.jupiter.api.Assertions.*;

class TheoryParamTest {

    @Test
    void diameterTest() {
        FatTreeTopology graph = new FatTreeTopology(12);
        FatTreeRouting routing = new FatTreeRouting(graph);

        TheoryParam test = new TheoryParam(graph, routing);

        assertEquals(test.diameter(), 6);
    }

    @Test
    void diameterTest2() {
        JellyFishTopology graph = new JellyFishTopology(100, 12, 8);
        K_ShortestPathRouting routing = new K_ShortestPathRouting(graph, 2);

        TheoryParam test = new TheoryParam(graph, routing);
        System.out.println(test.diameter());
    }

    @Test
    void diameterTest3() {
        SpaceShuffleTopology graph = new SpaceShuffleTopology(100, 14, 2);
        SpaceShuffleRouting routing = new SpaceShuffleRouting(graph);
        TheoryParam test = new TheoryParam(graph, routing);
        System.out.println(test.diameter());
    }

    @Test
    void diameterTest4() {
        SmallWorldTopology graph = new SmallWorldTopology(20, 20, "torus", new double[]{1.6, 1.6});
//        SmallWorld
    }


    @Test
    void averageLengthPathTest() {
        FatTreeTopology graph = new FatTreeTopology(4);
        FatTreeRouting routing = new FatTreeRouting(graph);

        TheoryParam test = new TheoryParam(graph, routing);

        System.out.println(test.averageLengthPath());
    }

    @Test
    void averageLengthPathTest2() {
        JellyFishTopology graph = new JellyFishTopology(100, 16, 12);
        K_ShortestPathRouting routing = new K_ShortestPathRouting(graph, 2);

        TheoryParam test = new TheoryParam(graph, routing);

        System.out.println(test.averageLengthPath());
    }
    @Test
    void averageLengthPathTest3() {
        SpaceShuffleTopology graph = new SpaceShuffleTopology(100, 16, 6);
        SpaceShuffleRouting routing = new SpaceShuffleRouting(graph);
        TheoryParam test = new TheoryParam(graph, routing);
        System.out.println(test.averageLengthPath());
    }
}