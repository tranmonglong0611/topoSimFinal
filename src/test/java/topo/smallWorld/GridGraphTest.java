package topo.smallWorld;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridGraphTest {

    @Test
    public void gridGraphContruct() {
        GridGraph gg = new GridGraph(4, 4, "torus");
        System.out.println(gg.toString());
    }
}