package tamolo.simulation;

import tamolo.topo.Topology;
import tamolo.topo.routing.RoutingAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tranmonglong0611 on 29/11/2017.
 */
public class Network {
    private Topology graph;
    private List<Host> hosts;
    private List<Switch> switches;
    private Map<Integer, Host> hostById;
    private Map<Integer, Switch> switchById;

    public Network(Topology graph, RoutingAlgorithm routingAlgorithm) {
        this.graph = graph;
        // construct hosts, switches and links and routing algorithm
        hosts = new ArrayList<>();
        switches = new ArrayList<>();
        hostById = new HashMap<>();
        switchById = new HashMap<>();

        for (int hid : graph.hosts()) {
            Host host = new Host(hid, routingAlgorithm);
            hosts.add(host);
            hostById.put(hid, host);
        }

        for (int sid : graph.switches()) {
            Switch sw = new Switch(sid, routingAlgorithm);
            switches.add(sw);
            switchById.put(sid, sw);
        }

        // link from switch to host
        for (Host host : hosts) {
            // get neighbor switch
            int nsid = graph.adj(host.id)
                    .get(0);
            Switch csw = switchById.get(nsid);

            // add to both
            host.link = new Link(host, csw);

            if (!csw.links.containsKey(host.id)) {
                csw.links.put(host.id, new Link(host, csw));
            }
        }

        // link from switch to switch
        for (Switch sw : switches) {
            int swid = sw.id;
            for (int nsid : graph.adj(swid)) {
                if (graph.isSwitchVertex(nsid)) {
                    Switch other = switchById.get(nsid);
                    // create new link
                    // add to both
                    if (!other.links.containsKey(swid)) {
                        other.links.put(swid, new Link(sw, other));
                    }
                    if (!sw.links.containsKey(nsid)) {
                        sw.links.put(nsid, new Link(sw, other));
                    }
                }
            }
        }
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public List<Switch> getSwitches() {
        return switches;
    }

    public Host getHostById(int id) {
        return hostById.get(id);
    }

    public void reset() {
        for (Host host : hosts) {
            host.reset();
        }

        for (Switch sw: switches) {
            sw.reset();
        }
    }
}