package application;

import application.cell.CellLayer;
import application.cell.CellType;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import topo.Topology;

public class GraphVisualize {

    private Model model;

    private Group canvas;
    private Topology graph;
    private BorderPane showingGraphField;

//    MouseGestures mouseGestures;

    /**
     * the pane wrapper is necessary or else the scrollpane would always align
     * the top-most and left-most child to the top and left eg when you drag the
     * top child down, the entire scrollpane would move down
     */
    CellLayer cellLayer;

    public GraphVisualize() {

        this.model = new Model();

        canvas = new Group();
        cellLayer = new CellLayer();

        canvas.getChildren().add(cellLayer);

//        mouseGestures = new MouseGestures(this);

        showingGraphField = new BorderPane(canvas);
//
//        scrollPane.setFitToWidth(true);
//        scrollPane.setFitToHeight(true);
    }
    public Topology getGraph() {
        return this.graph;
    }
    public GraphVisualize(Topology a) {
        this.graph = a;
        this.model = new Model();

        canvas = new Group();
        cellLayer = new CellLayer();

        canvas.getChildren().add(cellLayer);

//        mouseGestures = new MouseGestures(this);

        showingGraphField = new BorderPane(canvas);
//
//        scrollPane.setFitToWidth(true);
//        scrollPane.setFitToHeight(true);

        this.addGraphComponents(a);
    }
    public BorderPane getShowingGraphField() {
        return this.showingGraphField;
    }

    public Pane getCellLayer() {
        return this.cellLayer;
    }

    public Model getModel() {
        return model;
    }

    public void beginUpdate() {
    }

    public void endUpdate() {

        // add components to graph pane
        getCellLayer().getChildren().addAll(model.getAddedEdges());
        getCellLayer().getChildren().addAll(model.getAddedCells());

        // remove components from graph pane
        getCellLayer().getChildren().removeAll(model.getRemovedCells());
        getCellLayer().getChildren().removeAll(model.getRemovedEdges());

        // enable dragging of cells
//        for (Cell cell : model.getAddedCells()) {
//            mouseGestures.makeDraggable(cell);
//        }

        // every cell must have a parent, if it doesn't, then the graphParent is
        // the parent
        getModel().attachOrphansToGraphParent(model.getAddedCells());

        // remove reference to graphParent
        getModel().disconnectFromGraphParent(model.getRemovedCells());

        // merge added & removed cells with all cells
        getModel().merge();

    }

//    public double getScale() {
//        return this.scrollPane.getScaleValue();
//    }

    private void addGraphComponents(Topology graph) {

        Model model = this.getModel();

        this.beginUpdate();
        for (int i = 0; i < graph.getNumV(); i++) {
            if (graph.isHostVertex(i)) model.addCell(Integer.toString(i), CellType.HOST);
            else {
                model.addCell(Integer.toString(i), CellType.SWITCH);
            }

        }
        for (int i = 0; i < graph.getNumV(); i++) {
            for (int j = 0; j < graph.adj(i).size(); j++) {
                model.addEdge(Integer.toString(i), Integer.toString(graph.adj(i).get(j)));
            }
        }
        this.endUpdate();

    }
}