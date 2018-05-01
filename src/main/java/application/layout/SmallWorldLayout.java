package application.layout;

import application.cell.Cell;
import application.GraphVisualize;

import java.util.List;
import java.util.Random;



public class SmallWorldLayout extends Layout {

    GraphVisualize graph;

    Random rnd = new Random();

    public SmallWorldLayout(GraphVisualize graph) {

        this.graph = graph;

    }

    public void execute() {


        List<Cell> cells = graph.getModel().getAllCells();
        double degree = 360 / cells.size();
        double radian = Math.toRadians(degree);
        double centerX = 1024 / 2;
        double centerY = 700 / 2;

        double radius = 200;

        for(int i = 0; i < cells.size(); i++) {
            double x = centerX + Math.cos(radian * i) * radius;
            double y = centerY + Math.sin(radian * i) * radius;

            cells.get(i).relocate(x, y);
        }

    }

}
