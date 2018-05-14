package topo.smallWorld;

import org.junit.jupiter.api.Test;

class GridTopologyTest {

    @Test
    public void gridGraphContruct() {
        GridTopology gg = new GridTopology(4, 4, "torus");
        System.out.println(gg.toString());
    }
}