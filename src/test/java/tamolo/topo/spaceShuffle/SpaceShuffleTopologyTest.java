package tamolo.topo.spaceShuffle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpaceShuffleTopologyTest {

    @Test
    void nDimension() {
        SpaceShuffleTopology tester = new SpaceShuffleTopology(9, 6, 2);
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