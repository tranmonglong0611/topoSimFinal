package visualize;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        HostCell a = new HostCell(1);
        root.setCenter(a);

        Scene scene = new Scene(root, 1024, 768);
        primaryStage.setScene(scene);
        primaryStage.show();

        a.relocate(1000, 500);


    }
}
