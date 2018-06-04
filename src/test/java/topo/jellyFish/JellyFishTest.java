package topo.jellyFish;

import org.junit.jupiter.api.Test;
import tamolo.topo.jellyFish.JellyFishTopology;

/*
    author tamolo
    date 5/1/18
*/
public class JellyFishTest {
    @Test
    void jellyFishGraphConstruct() {
        JellyFishTopology graph = new JellyFishTopology(10, 8, 4);
        System.out.println(graph.toString());
    }
}
