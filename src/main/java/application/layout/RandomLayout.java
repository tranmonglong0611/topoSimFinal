package application.layout;

import application.cell.Cell;
import application.Graph;

import java.util.List;
import java.util.Random;



public class RandomLayout extends Layout {

    Graph graph;

    Random rnd = new Random();

    public RandomLayout(Graph graph) {

        this.graph = graph;

    }

    public void execute() {

        List<Cell> cells = graph.getModel().getAllCells();

        for (Cell cell : cells) {

            double x = rnd.nextDouble() * 1024;
            double y = rnd.nextDouble() * 700;

            cell.relocate(x, y);

        }

    }

}
