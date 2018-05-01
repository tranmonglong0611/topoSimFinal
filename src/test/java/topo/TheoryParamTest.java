package topo;

import org.junit.jupiter.api.Test;
import topo.fatTree.FatTreeGraph;
import topo.fatTree.FatTreeRouting;
import topo.jellyFish.JellyFishGraph;
import topo.jellyFish.K_ShortestPathRouting;
import topo.smallWorld.SmallWorldGraph;
import topo.spaceShuffle.SpaceShuffleGraph;
import topo.spaceShuffle.SpaceShuffleRouting;

import static org.junit.jupiter.api.Assertions.*;

class TheoryParamTest {

    @Test
    void diameterTest() {
        FatTreeGraph graph = new FatTreeGraph(12);
        FatTreeRouting routing = new FatTreeRouting(graph);

        TheoryParam test = new TheoryParam(graph, routing);

        assertEquals(test.diameter(), 6);
    }

    @Test
    void diameterTest2() {
        JellyFishGraph graph = new JellyFishGraph(100, 12, 8);
        K_ShortestPathRouting routing = new K_ShortestPathRouting(graph, 2);

        TheoryParam test = new TheoryParam(graph, routing);
        System.out.println(test.diameter());
    }

    @Test
    void diameterTest3() {
        SpaceShuffleGraph graph = new SpaceShuffleGraph(100, 14, 2);
        SpaceShuffleRouting routing = new SpaceShuffleRouting(graph);
        TheoryParam test = new TheoryParam(graph, routing);
        System.out.println(test.diameter());
    }

    @Test
    void diameterTest4() {
        SmallWorldGraph graph = new SmallWorldGraph(20, 20, "torus", new double[]{1.6, 1.6});
//        SmallWorld
    }


    @Test
    void averageLengthPathTest() {
        FatTreeGraph graph = new FatTreeGraph(4);
        FatTreeRouting routing = new FatTreeRouting(graph);

        TheoryParam test = new TheoryParam(graph, routing);

        System.out.println(test.averageLengthPath());
    }

    @Test
    void averageLengthPathTest2() {
        JellyFishGraph graph = new JellyFishGraph(100, 16, 12);
        K_ShortestPathRouting routing = new K_ShortestPathRouting(graph, 2);

        TheoryParam test = new TheoryParam(graph, routing);

        System.out.println(test.averageLengthPath());
    }
    @Test
    void averageLengthPathTest3() {
        SpaceShuffleGraph graph = new SpaceShuffleGraph(100, 16, 6);
        SpaceShuffleRouting routing = new SpaceShuffleRouting(graph);
        TheoryParam test = new TheoryParam(graph, routing);
        System.out.println(test.averageLengthPath());
    }
}