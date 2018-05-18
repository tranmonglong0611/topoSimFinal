package application.visualizeController;

import application.CustomOutputStream;
import visualize.GraphVisualize;
import visualize.layout.FatTreeLayout;
import visualize.layout.Layout;
import visualize.layout.RandomLayout;
import visualize.layout.SmallWorldLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import topo.Topology;
import topo.fatTree.FatTreeTopology;
import topo.jellyFish.JellyFishTopology;

import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.apache.logging.log4j.*;
import topo.smallWorld.SmallWorldTopology;
import topo.spaceShuffle.SpaceShuffleTopology;

/*
    author tamolo
    date 3/24/18
*/
public class MainSceneController {
    private Topology graph = null;

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
    private TextArea consoleTextArea;
    @FXML
    private Button btnConsoleUp;
    @FXML
    private Button btnConsoleDown;
    @FXML
    private Button btnConsoleHide;
    @FXML
    private Button btnConsoleClean;
    @FXML
    private BorderPane consoleBorderPane;
    @FXML
    private Button btnGraphInfo;
    @FXML
    private TextArea areaGraphInfo;


    @FXML
    public void initialize() {
        PrintStream con=new PrintStream(new CustomOutputStream(consoleTextArea));
        System.setOut(con);
        System.setErr(con);
    }

    @FXML
    public void consoleHandle(ActionEvent event) throws  IOException {
        if(event.getSource() == btnConsoleClean) {
            consoleTextArea.setText("");
        }else if(event.getSource() == btnConsoleDown) {
            consoleTextArea.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
        }else if(event.getSource() == btnConsoleUp) {
            consoleTextArea.setScrollTop(Double.MIN_VALUE); //this will scroll to the bottom
        }
    }


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
                            graph = new FatTreeTopology(k);
                            GraphVisualize graphVisualize = new GraphVisualize(graph);
                            mainBorderPane.setLeft(graphVisualize.getShowingGraphField());
                            FatTreeLayout layout = new FatTreeLayout(graphVisualize);
                            areaGraphInfo.setText(graph.graphInfo());
                            LogManager.getLogger(MainSceneController.class.getName()).info("Done make new FatTree Graph with k = " + k);
                            layout.execute();
                        }
                        stage.close();

                    }catch (NumberFormatException e) {
                        LogManager.getLogger(MainSceneController.class.getName()).info("Error when making FatTree Graph");
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
                        graph = new JellyFishTopology(nSwitch, nPort, nPort - nHostPSwitch);
                        GraphVisualize graphVisualize = new GraphVisualize(graph);
                        mainBorderPane.setLeft(graphVisualize.getShowingGraphField());
                        areaGraphInfo.setText(graph.graphInfo());
                        Layout layout = new RandomLayout(graphVisualize);
                        layout.execute();
                        LogManager.getLogger(MainSceneController.class.getName()).info("Done make JellyFish Graph");

                        stage.close();

                    }catch (NumberFormatException e) {
                        LogManager.getLogger(MainSceneController.class.getName()).info("Error when making JellyFish Graph");
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
                        graph = new SpaceShuffleTopology(nSwitch, nPort, nDimension);
                        GraphVisualize graphVisualize = new GraphVisualize(graph);
                        mainBorderPane.setLeft(graphVisualize.getShowingGraphField());
                        areaGraphInfo.setText(graph.graphInfo());

                        Layout layout = new RandomLayout(graphVisualize);
                        layout.execute();
                        LogManager.getLogger(MainSceneController.class.getName()).info("Done make new SpaceShuffle Graph");
                        stage.close();

                    }catch (NumberFormatException e) {
                        LogManager.getLogger(MainSceneController.class.getName()).info("Error when new SpaceShuffle Graph");
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

                Label nRowLabel = new Label("Number of rows: ");
                grid.add(nRowLabel, 0, 1);
                TextField nRowInput= new TextField();
                grid.add(nRowInput, 1, 1);

                Label nColLabel = new Label("Number of columns: ");
                grid.add(nColLabel, 0, 2);
                TextField nColInput = new TextField();
                grid.add(nColInput, 1, 2);

                Label nRandomLinkPerSwitchLabel = new Label("Number of random link per Switch: ");
                grid.add(nRandomLinkPerSwitchLabel, 0, 3);
                TextField nRandomLinkPerSwitchInput = new TextField();
                grid.add(nRandomLinkPerSwitchInput, 1, 3);

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
                            int nCol = Integer.parseInt(nColInput.getText());
                            int nRow = Integer.parseInt(nRowInput.getText());
                            int randomLinkPerSwitch = Integer.parseInt(nRandomLinkPerSwitchInput.getText());
                            double[] alphas = new double[randomLinkPerSwitch];
                            Arrays.fill(alphas, 1.6);
                            graph = new SmallWorldTopology(nRow, nCol, "torus", alphas);
                            GraphVisualize graphVisualize = new GraphVisualize(graph);
                            mainBorderPane.setLeft(graphVisualize.getShowingGraphField());
                            areaGraphInfo.setText(graph.graphInfo());
                            Layout layout = new SmallWorldLayout(graphVisualize);
                            layout.execute();
                            LogManager.getLogger(MainSceneController.class.getName()).info("Done make new SmallWorld Graph");
                            stage.close();
                        }catch (NumberFormatException e) {
                            LogManager.getLogger(MainSceneController.class.getName()).info("Error when make SmallWorld Graph");
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


    @FXML
    public void toggleGraphInfo() {
        if(btnGraphInfo.getText().equals("Show Graph Info")){
            btnGraphInfo.setText("Hide Graph Info");
            areaGraphInfo.setVisible(true);
        }else {
            btnGraphInfo.setText("Show Graph Info");
            areaGraphInfo.setVisible(false);
        }
    }

    @FXML
    public void exportGraph() {
        if(graph == null) {
            LogManager.getLogger(MainSceneController.class.getName()).info("There is no graph to import");
        }else {
            Date date = new Date() ;
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd_HH-mm-ss") ;
            String path = graph.type + "_" + dateFormat.format(date) + ".bat";
            graph.writeToFile(path);
            LogManager.getLogger(MainSceneController.class.getName()).info("Done export graph to file: " + path);
        }
    }

    @FXML
    public void importGraph(ActionEvent e) {
//        graph = Graph.readFromFile(path);
        Stage stage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
//        Text scenetitle = new Text("FatTree Graph Config");
//        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//        grid.add(scenetitle, 0, 0, 2, 1);

        Label kLabel = new Label("Enter file name:");
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
                    String path = kInput.getText();
                    graph = Topology.readFromFile(path);
                    GraphVisualize graphVisualize = new GraphVisualize(graph);
                    mainBorderPane.setLeft(graphVisualize.getShowingGraphField());
                    Layout layout;
                    if(graph.type == "FatTree") {
                        layout = new FatTreeLayout(graphVisualize);
                    }else if(graph.type == "SmallWorld") {
                        layout = new SmallWorldLayout(graphVisualize);
                    }else {
                        layout = new RandomLayout(graphVisualize);
                    }
                    areaGraphInfo.setText(graph.graphInfo());
                    layout.execute();
                    LogManager.getLogger(MainSceneController.class.getName()).info("Done import graph from file: " + path);
                    stage.close();

                }catch (Exception e) {
                }
            }
        });
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(btnJf.getScene().getWindow());
        stage.showAndWait();

    }
}
