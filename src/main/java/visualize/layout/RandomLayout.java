package visualize.layout;

import visualize.GraphVisualize;
import visualize.cell.Cell;

import java.util.List;
import java.util.Random;



public class RandomLayout extends Layout {

    GraphVisualize graph;

    Random rnd = new Random();

    public RandomLayout(GraphVisualize graph) {
        this.graph = graph;
    }

    public void execute() {
        List<Cell> cells = graph.getModel().getAllCells();
        for (Cell cell : cells) {
            double x = rnd.nextDouble() * ConfigResolution.WIDTH;
            double y = rnd.nextDouble() * ConfigResolution.HEIGHT;
            cell.relocate(x, y);
        }

    }

}
