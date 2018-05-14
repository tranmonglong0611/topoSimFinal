package application;


import application.cell.CellType;
import application.layout.Layout;
import application.layout.RandomLayout;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import topo.jellyFish.JellyFishTopology;


public class Main extends Application {

    GraphVisualize graph = new GraphVisualize();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        graph = new GraphVisualize();


        Scene scene = new Scene(root, 1024, 768);
//        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        root.setCenter(graph.getShowingGraphField());

        primaryStage.setScene(scene);
        primaryStage.show();

        addGraphComponents();

        Layout layout = new RandomLayout(graph);
        layout.execute();
    }

    private void addGraphComponents() {

        Model model = graph.getModel();

        graph.beginUpdate();
        JellyFishTopology temp = new JellyFishTopology(5, 4, 2);
        for (int i = 0; i < temp.getNumV(); i++) {
            if (temp.isHostVertex(i)) model.addCell(Integer.toString(i), CellType.HOST);
            else {
                model.addCell(Integer.toString(i), CellType.SWITCH);
            }

        }
        for (int i = 0; i < temp.getNumV(); i++) {
            for (int j = 0; j < temp.adj(i).size(); j++) {
                model.addEdge(Integer.toString(i), Integer.toString(temp.adj(i).get(j)));
            }
        }
        graph.endUpdate();

    }

    public static void main(String[] args) {
        launch(args);
    }
}