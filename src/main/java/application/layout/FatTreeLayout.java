package application.layout;

import application.GraphVisualize;
import application.cell.Cell;
import topo.Graph;
import topo.fatTree.FatTreeGraph;

import java.util.List;

/*
    author tamolo
    date 3/26/18
*/
public class FatTreeLayout extends Layout{

    GraphVisualize graphVisualize;
    public FatTreeLayout(GraphVisualize a) {
        this.graphVisualize = a;
    }

    @Override
    public void execute() {
        List<Cell> cells = graphVisualize.getModel().getAllCells();
        final FatTreeGraph graph = (FatTreeGraph)graphVisualize.getGraph();
        final int k = graph.getK();
        final int numHostPerPod = k + k * k / 4;
        final int widthPerPod = ConfigResolution.WIDTH / k;
        int[] startPos = new int[k];
        startPos[0] = 10;
        for(int i = 1; i < k; i++) {
            startPos[i] = startPos[i-1] + widthPerPod;
        }


        int x = 10;
        int yCore = ConfigResolution.HEIGHT / 8;
        int yAggSwitch = yCore + ConfigResolution.HEIGHT / 4;
        int yEdgeSwitch = yAggSwitch + ConfigResolution.HEIGHT / 4;
        int yHost = yEdgeSwitch + ConfigResolution.HEIGHT / 4;
        for(int i = 0; i < k; i++) {
            int startX = startPos[i];
            for(int j = i * numHostPerPod; j < i * numHostPerPod + k * k / 4; j++)  {
                cells.get(j).relocate(startX, yHost);
                startX += widthPerPod / (k * k / 4);
            }
            for(int j = i * numHostPerPod + k * k / 4; j < i * numHostPerPod + k * k / 4 + k / 2; j++) {
                int index = (j % numHostPerPod) % (k / 2);
                int leftIndex = numHostPerPod * i + index * k / 2;
                int rightIndex = leftIndex + k / 2 - 1;
                double xx = (cells.get(leftIndex).getLayoutX() + cells.get(rightIndex).getLayoutX())/ 2;
                cells.get(j).relocate(xx, yEdgeSwitch);
                cells.get(j + k / 2).relocate(xx, yAggSwitch);
            }
        }
//
        for(int i = numHostPerPod * k; i < graph.getNumV(); i++) {
            int index = i - numHostPerPod * k;
            double widthPCorePod = ConfigResolution.WIDTH / (k * k / 4);
            cells.get(i).relocate( 10 + index * widthPCorePod, yCore);
        }
    }
}
