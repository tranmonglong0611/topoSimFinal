package application.cell;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class HostCell extends Cell {

    public HostCell(String id) {
        super( id);

        StackPane view = new StackPane();

        Rectangle rec = new Rectangle( 5,5);
        Text text = new Text(this.cellId);

        rec.setStroke(Color.DODGERBLUE);
        rec.setFill(Color.DODGERBLUE);

        view.getChildren().addAll(rec, text);
        setView( view);

    }

}