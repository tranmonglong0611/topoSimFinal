package application;


import application.cell.CellType;
import application.layout.Layout;
import application.layout.RandomLayout;
import application.layout.SmallWorldLayout;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import topo.fatTree.FatTreeGraph;
import topo.smallWorld.SWRingGraph;


public class Main extends Application {

    Graph graph = new Graph();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        graph = new Graph();


        Scene scene = new Scene(root, 1024, 768);
//        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        root.setCenter(graph.getScrollPane());

        primaryStage.setScene(scene);
        primaryStage.show();

        addGraphComponents();

        Layout layout = new SmallWorldLayout(graph);
        layout.execute();
    }

    private void addGraphComponents() {

        Model model = graph.getModel();

        graph.beginUpdate();

//        model.addCell("Cell A", CellType.RECTANGLE);
//        model.addCell("Cell B", CellType.RECTANGLE);
//        model.addCell("Cell C", CellType.RECTANGLE);
//        model.addCell("Cell D", CellType.TRIANGLE);
//        model.addCell("Cell E", CellType.TRIANGLE);
//        model.addCell("Cell F", CellType.RECTANGLE);
//        model.addCell("Cell G", CellType.RECTANGLE);


//        model.addEdge("Cell A", "Cell B");
//        model.addEdge("Cell A", "Cell C");
//        model.addEdge("Cell B", "Cell C");
//        model.addEdge("Cell C", "Cell D");
//        model.addEdge("Cell B", "Cell E");
//        model.addEdge("Cell D", "Cell F");
//        model.addEdge("Cell D", "Cell G");

//        for(int i = 0; i < 100; i++) {
//            model.addCell("Cell" + i, CellType.RECTANGLE);
//        }
//
//        for(int i = 0; i < 200; i++) {
//            long a1 = Math.round(Math.random() * 99);
//            long a2 = Math.round(Math.random() * 99);
//            if(a1 != a2) {
//                System.out.println(a1 + "===" + a2);
//                model.addEdge("Cell" + a1, "Cell" + a2);
//            }
//        }

        SWRingGraph temp = new SWRingGraph(15, 4, 0.2);
        for (int i = 0; i < temp.getNumV(); i++) {
            if (temp.isHostVertex(i)) model.addCell(Integer.toString(i), CellType.HOST);
            else {
                model.addCell(Integer.toString(i), CellType.SWITCH);
            }

        }
        for (int i = 0; i < temp.getNumV(); i++) {
            System.out.print(i);
            for (int j = 0; j < temp.adj(i).size(); j++) {
                System.out.print("====" + temp.adj(i).get(j));
                model.addEdge(Integer.toString(i), Integer.toString(temp.adj(i).get(j)));
            }
            System.out.println();
        }




        graph.endUpdate();

    }

    public static void main(String[] args) {
        launch(args);
    }
}