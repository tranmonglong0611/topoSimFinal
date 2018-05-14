package application.layout;

import application.cell.Cell;
import application.GraphVisualize;
import topo.smallWorld.SmallWorldTopology;

import java.util.List;


public class SmallWorldLayout extends Layout {

    GraphVisualize graphVisualize;


    public SmallWorldLayout(GraphVisualize graph) {

        this.graphVisualize = graph;

    }

    public void execute() {
        List<Cell> cells = graphVisualize.getModel().getAllCells();
        final SmallWorldTopology graph = (SmallWorldTopology)graphVisualize.getGraph();
        double x;
        double y = 10;

        for(int i = 0; i < graph.nRow; i++) {
            x = 10;
            for(int j = 0; j < graph.nCol; j++) {
                cells.get(j + i * graph.nCol).relocate(x, y);
                x += ConfigResolution.WIDTH * 1.0 / graph.nCol;
                System.out.println(x + "====" + y + "=======" + (j + i * j));
            }
            y += ConfigResolution.HEIGHT * 1.0 / graph.nRow;
        }


//        double degree = 360 / cells.size();
//        double radian = Math.toRadians(degree);
//        double centerX = 1024 / 2;
//        double centerY = 700 / 2;
//
//        double radius = 200;

//        for(int i = 0; i < cells.size(); i++) {
//            double x = centerX + Math.cos(radian * i) * radius;
//            double y = centerY + Math.sin(radian * i) * radius;
//
//            cells.get(i).relocate(x, y);
//        }

    }

}
