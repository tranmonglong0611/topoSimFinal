package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.PrintStream;

/*
    author tamolo
    date 3/22/18
*/
public class TestJavaFx extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainScene.fxml"));
//
//        PrintStream con=new PrintStream(new TextAreaOutputStream(root));
//        System.setOut(con);
//        System.setErr(con);
        primaryStage.setTitle("Simulation");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
