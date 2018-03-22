package topo.spaceShuffle;

import org.junit.jupiter.api.Test;
import sun.jvm.hotspot.memory.Space;

import static org.junit.jupiter.api.Assertions.*;

class SpaceShuffleGraphTest {

    @Test
    void nDimension() {
        SpaceShuffleGraph tester = new SpaceShuffleGraph(9, 6, 2);
        System.out.printf(tester.toString());
        assertEquals(2, tester.nDimension(), "dimension is 2");
    }

    @Test
    void standPoint() {
    }

    @Test
    void distance() {
    }
}