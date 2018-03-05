package visualize;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class Cell extends Pane {
    int cellId;

    Node view;

    public Cell(int id) {
        this.cellId = id;
    }

    public void setView(Node view) {
        this.view = view;
        getChildren().add(view);
    }

    public Node getView() {
        return this.view;
    }

    public int getCellId() {
        return cellId;
    }
}
