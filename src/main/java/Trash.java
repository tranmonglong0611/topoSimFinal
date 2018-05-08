import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import topo.Graph;
import topo.fatTree.FatTreeGraph;
import topo.smallWorld.SmallWorldGraph;

import javax.swing.text.StyleContext;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/*
    author tamolo
    date 5/1/18
*/
public class Trash {
    final static Logger logger = LogManager.getLogger(Trash.class);

    public static void main(String[] args) {
//        FatTreeGraph b = new FatTreeGraph(16);
        SmallWorldGraph a = (SmallWorldGraph) Graph.readFromFile("FatTree16.bat");

        System.out.println(a.toString());

//        b.writeToFile("FatTree16.bat");
    }

}
