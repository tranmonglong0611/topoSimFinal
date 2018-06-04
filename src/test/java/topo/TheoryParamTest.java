package topo;

import org.junit.jupiter.api.Test;
import tamolo.topo.TheoryParam;
import tamolo.topo.fatTree.FatTreeTopology;
import tamolo.topo.fatTree.FatTreeRouting;
import tamolo.topo.jellyFish.JellyFishTopology;
import tamolo.topo.jellyFish.K_ShortestPathRouting;
import tamolo.topo.smallWorld.SmallWorldTopology;
import tamolo.topo.spaceShuffle.SpaceShuffleTopology;
import tamolo.topo.spaceShuffle.SpaceShuffleRouting;

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