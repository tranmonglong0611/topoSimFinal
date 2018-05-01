package application.visualizeController;

import application.GraphVisualize;
import application.layout.FatTreeLayout;
import application.layout.Layout;
import application.layout.RandomLayout;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import topo.fatTree.FatTreeGraph;
import topo.jellyFish.JellyFishGraph;

import java.io.IOException;

/*
    author tamolo
    date 3/24/18
*/
public class MainSceneController {

    @FXML
    private Button btnFt;
    @FXML
    private Button btnSw;
    @FXML
    private Button btnS2;
    @FXML
    private Button btnJf;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private Button btnImport;
    @FXML
    private Button btnExport;

    @FXML
    public void graphMaking(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;

        if(event.getSource()==btnFt) {
            stage = new Stage();
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));
            Text scenetitle = new Text("FatTree Graph Config");
            scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            grid.add(scenetitle, 0, 0, 2, 1);

            Label kLabel = new Label("k:");
            grid.add(kLabel, 0, 1);

            TextField kInput= new TextField();
            grid.add(kInput, 1, 1);

            Scene scene = new Scene(grid, 350, 150);

            Button btn = new Button("OK");
            btn.setDefaultButton(true);
            HBox hbBtn = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(btn);
            grid.add(hbBtn, 1, 4);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        int k = Integer.parseInt(kInput.getText());
                        if(k > 20 || k < 0 || k % 2 == 1) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Dialog");
                            alert.setHeaderText("This K value is too big or too small or is an odd");
                            alert.setContentText("You need to enter the even integer k from 4 to 20");
                            alert.showAndWait();
                        }else {
                            System.out.println("huhu");
                            FatTreeGraph graph = new FatTreeGraph(k);
                            GraphVisualize graphVisualize = new GraphVisualize(graph);
                            mainBorderPane.setCenter(graphVisualize.getShowingGraphField());

                            FatTreeLayout layout = new FatTreeLayout(graphVisualize);
                            layout.execute();

                            System.out.printf("done");
                        }
                        stage.close();

                    }catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Wrong Input Format");
                        alert.setContentText("You need to enter a number!");
                        alert.showAndWait();
                    }
                }
            });
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnFt.getScene().getWindow());
            stage.showAndWait();
        }
        else if(event.getSource() == btnJf) {
            stage = new Stage();
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));
            Text scenetitle = new Text("JellyFish Graph Config");
            scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            grid.add(scenetitle, 0, 0, 2, 1);

            Label nSwitchLabel = new Label("Number of switches: ");
            grid.add(nSwitchLabel, 0, 1);
            TextField nSwitchInput= new TextField();
            grid.add(nSwitchInput, 1, 1);

            Label nPortLabel = new Label("Number of ports: ");
            grid.add(nPortLabel, 0, 2);
            TextField nPortInput = new TextField();
            grid.add(nPortInput, 1, 2);

            Label nHostPerSwitchLabel = new Label("Number of hosts per switch: ");
            grid.add(nHostPerSwitchLabel, 0, 3);
            TextField nHostPerSwitchInput = new TextField();
            grid.add(nHostPerSwitchInput, 1, 3);

            Scene scene = new Scene(grid, 450, 300);

            Button btn = new Button("OK");
            btn.setDefaultButton(true);
            HBox hbBtn = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(btn);
            grid.add(hbBtn, 1, 4);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        int nSwitch = Integer.parseInt(nSwitchInput.getText());
                        int nPort = Integer.parseInt(nPortInput.getText());
                        int nHostPSwitch = Integer.parseInt(nHostPerSwitchInput.getText());
                        JellyFishGraph graph = new JellyFishGraph(nSwitch, nPort, nPort - nHostPSwitch);
                        GraphVisualize graphVisualize = new GraphVisualize(graph);
                        mainBorderPane.setCenter(graphVisualize.getShowingGraphField());

                        Layout layout = new RandomLayout(graphVisualize);
                        layout.execute();

                        stage.close();

                    }catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Wrong Input Format");
                        alert.setContentText("You need to enter a number!");
                        alert.showAndWait();
                    }
                }
            });
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnJf.getScene().getWindow());
            stage.showAndWait();
        }
        else if(event.getSource() == btnS2) {
            stage = new Stage();
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));
            Text scenetitle = new Text("Space Shuffle Graph Config");
            scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
            grid.add(scenetitle, 0, 0, 2, 1);

            Label nSwitchLabel = new Label("Number of switches: ");
            grid.add(nSwitchLabel, 0, 1);
            TextField nSwitchInput= new TextField();
            grid.add(nSwitchInput, 1, 1);

            Label nPortLabel = new Label("Number of ports: ");
            grid.add(nPortLabel, 0, 2);
            TextField nPortInput = new TextField();
            grid.add(nPortInput, 1, 2);

            Label nDimensionLabel = new Label("Number of dimension: ");
            grid.add(nDimensionLabel, 0, 3);
            TextField nDimensionInput = new TextField();
            grid.add(nDimensionInput, 1, 3);

            Scene scene = new Scene(grid, 450, 300);

            Button btn = new Button("OK");
            btn.setDefaultButton(true);
            HBox hbBtn = new HBox(10);
            hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
            hbBtn.getChildren().add(btn);
            grid.add(hbBtn, 1, 4);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        int nSwitch = Integer.parseInt(nSwitchInput.getText());
                        int nPort = Integer.parseInt(nPortInput.getText());
                        int nDimension = Integer.parseInt(nDimensionInput.getText());
                        JellyFishGraph graph = new JellyFishGraph(nSwitch, nPort, nDimension);
                        GraphVisualize graphVisualize = new GraphVisualize(graph);
                        mainBorderPane.setCenter(graphVisualize.getShowingGraphField());

                        Layout layout = new RandomLayout(graphVisualize);
                        layout.execute();

                        stage.close();

                    }catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Dialog");
                        alert.setHeaderText("Wrong Input Format");
                        alert.setContentText("You need to enter a number!");
                        alert.showAndWait();
                    }
                }
            });
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(btnJf.getScene().getWindow());
            stage.showAndWait();
        }
        else if(event.getSource() == btnSw) {
                stage = new Stage();
                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(25, 25, 25, 25));
                Text scenetitle = new Text("SmallWorld Graph Config");
                scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                grid.add(scenetitle, 0, 0, 2, 1);

                Label nSwitchLabel = new Label("Number of switches: ");
                grid.add(nSwitchLabel, 0, 1);
                TextField nSwitchInput= new TextField();
                grid.add(nSwitchInput, 1, 1);

                Label nPortLabel = new Label("Number of ports: ");
                grid.add(nPortLabel, 0, 2);
                TextField nPortInput = new TextField();
                grid.add(nPortInput, 1, 2);

                Label nHostPerSwitchLabel = new Label("Number of hosts per switch: ");
                grid.add(nHostPerSwitchLabel, 0, 3);
                TextField nHostPerSwitchInput = new TextField();
                grid.add(nHostPerSwitchInput, 1, 3);

                Scene scene = new Scene(grid, 450, 300);

                Button btn = new Button("OK");
                btn.setDefaultButton(true);
                HBox hbBtn = new HBox(10);
                hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
                hbBtn.getChildren().add(btn);
                grid.add(hbBtn, 1, 4);
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            int nSwitch = Integer.parseInt(nSwitchInput.getText());
                            int nPort = Integer.parseInt(nPortInput.getText());
                            int nHostPSwitch = Integer.parseInt(nHostPerSwitchInput.getText());
                            JellyFishGraph graph = new JellyFishGraph(nSwitch, nPort, nPort - nHostPSwitch);
                            GraphVisualize graphVisualize = new GraphVisualize(graph);
                            mainBorderPane.setCenter(graphVisualize.getShowingGraphField());

                            Layout layout = new RandomLayout(graphVisualize);
                            layout.execute();

                            stage.close();

                        }catch (NumberFormatException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error Dialog");
                            alert.setHeaderText("Wrong Input Format");
                            alert.setContentText("You need to enter a number!");
                            alert.showAndWait();
                        }
                    }
                });
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initOwner(btnJf.getScene().getWindow());
                stage.showAndWait();

        }
    }
}