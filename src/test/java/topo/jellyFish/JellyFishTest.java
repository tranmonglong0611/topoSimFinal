package topo.jellyFish;

import org.junit.jupiter.api.Test;

/*
    author tamolo
    date 5/1/18
*/
public class JellyFishTest {
    @Test
    void jellyFishGraphConstruct() {
        JellyFishGraph graph = new JellyFishGraph(12, 3, 3);
        System.out.println(graph.toString());
    }
}
