package tamolo.topo.fatTree;

import org.junit.jupiter.api.Test;
import tamolo.topo.routing.RoutingAlgorithm;
import tamolo.topo.routing.RoutingPath;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FatTreeRoutingTest {



    @Test
    public void next() throws Exception {
        FatTreeTopology fatTreeTopology = new FatTreeTopology(4);
        FatTreeRouting ra = new FatTreeRouting(fatTreeTopology);

        assertEquals(ra.next(2, 2, 17), 5);
        assertEquals(ra.next(2, 5, 17), 6);
        assertEquals(ra.next(2, 6, 17), 33);
        assertEquals(ra.next(2, 33, 17), 22);
        assertEquals(ra.next(2, 22, 17), 20);
        assertEquals(ra.next(2, 20, 17), 17);
    }

    @Test
    public void path() throws Exception {
        FatTreeTopology fatTreeTopology = new FatTreeTopology(4);
        RoutingAlgorithm ra = new FatTreeRouting(fatTreeTopology);

        List<Integer> trace = ra.path(2, 17).path;
        Integer[] traceResult = trace.toArray(new Integer[0]);

        assertArrayEquals(traceResult, new Integer[]{5, 6, 33, 22, 20});
    }

    @Test
    public void routingTolerance() throws Exception {
        FatTreeTopology fatTreeTopology = new FatTreeTopology(4);
        ArrayList<Integer> errorSwitch = new ArrayList<>();
//        errorSwitch.add(
        FatTreeRoutingFaultTolerance ra = new FatTreeRoutingFaultTolerance(fatTreeTopology, errorSwitch);
        RoutingPath trace = ra.path(2, 17);
        System.out.println(trace);
    }
}