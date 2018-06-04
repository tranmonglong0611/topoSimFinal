package tamolo.topo.fatTree;

import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import tamolo.topo.routing.RoutingAlgorithm;
import tamolo.topo.routing.RoutingPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    author tamolo
    date 5/8/18
*/
public class FatTreeRoutingFaultTolerance extends RoutingAlgorithm{
    protected Map<Pair<Integer, Integer>, RoutingPath> precomputedPaths = new HashMap<>();

    FatTreeTopology graph;
    List<Integer> listErrorSwitch;
    //Map<Integer, List<Integer>> edgeRouting;
    //key la aggre vertex. Map co key la edge index, value la edge vertex
    public Map<Integer, Map<Integer, Integer>> aggreDownRouting;

    //key la core vertex. Map co key la pod, value la agg vertex
    public Map<Integer, Map<Integer, Integer>> coreRouting;

    //key = 2 => list cac core switch khong the connect toi pod = 2
    public Map<Integer, List<Integer>> coreAvailEachPod;


    private List<Integer> getAggAvailPerPod(int podIndex) {
        int k = graph.getK();
        int offset = graph.numSwitchEachPod * podIndex;
        ArrayList<Integer> result = new ArrayList<>();
        for (int a = 0; a < k / 2; a++) {
            int agg = offset + k * k / 4 + k / 2 + a;
            if (listErrorSwitch.contains(agg)) continue;
            else result.add(agg);
        }

        return result;
    }
    //get list of core switch connect to aggSwitch
    //same indexAgg will get same result list
    private List<Integer> getAllCoreSwitch(int indexAgg) {
        ArrayList<Integer> result = new ArrayList<>();
        int k = graph.getK();

        int startCoreSwitchIndex = graph.numSwitchEachPod * graph.getK();

        int startIndex = startCoreSwitchIndex + indexAgg * k / 2;
        for(int i = startIndex; i < startIndex + k / 2; i++) {
            result.add(i);
        }
        return result;
    }

    public void handleCoreAvailEachPod() {
        int k = graph.getK();

        ArrayList<Integer> tempArray = new ArrayList<>();
        int startCoreSwitchIndex = graph.numSwitchEachPod * k;
        for(int ii = startCoreSwitchIndex; ii < startCoreSwitchIndex + k * k / 4; ii++ ){
            tempArray.add(ii);
        }
        for(int i = 0; i < graph.getK(); i++) {
            coreAvailEachPod.put(i, new ArrayList<>(tempArray));
        }
        if(listErrorSwitch.size() == 0) {
            return;
        }


        for(int errorSwitch : listErrorSwitch) {
            int type = graph.switchType(errorSwitch);
            if(type == FatTreeTopology.CORE) {
                for (int i = 0; i < graph.getK(); i++) {
                    int index = coreAvailEachPod.get(i).indexOf(errorSwitch);
                    if(index != -1)
                        coreAvailEachPod.get(i).remove(index);
                }
            }
            else if(type == FatTreeTopology.EDGE ) {

            }else if(type == FatTreeTopology.AGG) {
                int pod = graph.podBelongTo(errorSwitch);
                int indexAgg = getIndexAggSwitch(errorSwitch);

                List<Integer> allCoreSwitch = getAllCoreSwitch(indexAgg);
                for(int s : allCoreSwitch) {
                    if(coreAvailEachPod.get(pod).contains(s)) {
                        coreAvailEachPod.get(pod).remove(coreAvailEachPod.get(pod).indexOf(s));
                    }
                }
//                coreAvailEachPod.get(pod).removeAll(getAllCoreSwitch(indexAgg));
            }
        }
    }

    //get index of agg switch between all agg switch in a pod
    private int getIndexAggSwitch(int vertex) {
        int pod = graph.podBelongTo(vertex);
        int k = graph.getK();
        return vertex - (pod * graph.numSwitchEachPod + k * k  / 4 + k / 2);
    }

    public FatTreeRoutingFaultTolerance(FatTreeTopology graph, List<Integer> listErrorSwitch) {
        this.graph = graph;
        this.listErrorSwitch = listErrorSwitch;
        int k = graph.getK();
        aggreDownRouting = new HashMap<>();
        coreRouting = new HashMap<>();
        coreAvailEachPod = new HashMap<>();


        //---- xu ly di xuong----//
        //aggre switch
        for (int p = 0; p < k; p++) {
            int offset = graph.numSwitchEachPod * p;
            for (int a = 0; a < k / 2; a++) {
                int agg = offset + k * k / 4 + k / 2 + a;

                if(listErrorSwitch.contains(agg)) continue;

                HashMap<Integer, Integer> table = new HashMap<>();
                for (int e = 0; e < k / 2; e++) {
                    int edgeSwitch = offset + k * k / 4 + e;

                    if(!listErrorSwitch.contains(edgeSwitch)) {

                        table.put(e, edgeSwitch);
                    }
                }
                aggreDownRouting.put(agg, table);
            }
        }

        //core switch
        for (int c = 0; c < k * k / 4; c++) {
            int core = k * k * k / 4 + k * k + c;
            HashMap<Integer, Integer> table = new HashMap<>();
            for (int p = 0; p < k; p++) {
                int offset = graph.numSwitchEachPod * p;
                int agg = (c / (k / 2)) + k / 2 + k * k / 4 + offset;
                if(!listErrorSwitch.contains(agg)) {

                    table.put(p, agg);
                }
            }
            coreRouting.put(core, table);
        }

        // get agg switch avail per pod
        for (int p = 0; p < k; p++) {
            int offset = graph.numSwitchEachPod * p;
            for (int e = 0; e < k / 2; e++) {
                int edgeSwitch = offset + k * k / 4 + e;

            }
        }

        //---done xu ly di xuong----//
        handleCoreAvailEachPod();
    }

    //return -1 if the switch need to be sent is error
    @Override
    public int next(int source, int current, int destination) {
        int k = graph.getK();

        if(graph.isHostVertex(current)) {
            if(listErrorSwitch.contains(graph.adj(current).get(0))) {
                return -1;
            }else {
                return graph.adj(current).get(0);
            }


        }else if (graph.adj(current).contains(destination)) {
            return destination;
        }else {
            int type = graph.switchType(current);
            if(type == FatTreeTopology.CORE) {
                int podDes = graph.podBelongTo(destination);
                if(coreRouting.get(current).containsKey(podDes)) {
                    return coreRouting.get(current).get(podDes);
                }
                else {
                    return -1;
                }
            }else if(type == FatTreeTopology.AGG) {
                int podDes = graph.podBelongTo(destination);
                int podCur = graph.podBelongTo(current);
                if(podDes == podCur) {
                    //di xuong
                    int indexHost = (destination - podDes * graph.numSwitchEachPod)/ (k / 2);
                    if(aggreDownRouting.get(current).containsKey(indexHost)) {
                        return aggreDownRouting.get(current).get(indexHost);
                    }else {
                        return -1;
                    }
                }
                else {
                    //di len
                    int indexAgg = getIndexAggSwitch(current);
                    List<Integer> all = getAllCoreSwitch(indexAgg);
                    List<Integer> allAvail = coreAvailEachPod.get(podDes);
                    for(int core : all) {
                        if(allAvail.contains(core)) {
                            return core;
                        }
                    }
//                    LogManager.getLogger(FatTreeRoutingFaultTolerance.class.getName()).error("NO core Switch avail");
                    return -1;
                }
            }else if(type == FatTreeTopology.EDGE) {
                //xu li di len.di xuong da xu ly o tren cung function
                //chi can gui random cho 1 thang agg la xong
                int podCur = graph.podBelongTo(current);
                List<Integer> listAvailAgg = getAggAvailPerPod(podCur);

                if(listAvailAgg.size() == 0) return -1;
                return listAvailAgg.get((int)(Math.random() * listAvailAgg.size()));
            }else {

            }
        }
        return 0;
    }

    @Override
    public RoutingPath path(int source, int destination) {
        if (precomputedPaths.containsKey(new Pair<>(source, destination))) {
            return precomputedPaths.get(new Pair<>(source, destination));
        } else {
            RoutingPath rp = new RoutingPath();
            int current = source;
            rp.path.add(current);
            while (current != destination) {
                if (current != source) {
                    rp.path.add(current);
                }
                current = next(source, current, destination);
                if(current == -1) {
                    LogManager.getLogger(FatTreeRoutingFaultTolerance.class.getName()).error("Can not get the way from " + source +  "to " + destination);
                    return null;
                }
            }
            rp.path.add(destination);
//            rp.path.add(destination);
            precomputedPaths.put(new Pair<>(source, destination), rp);
            return rp;
        }
    }
}
