package application.cell;


import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class SwitchCell extends Cell {

    public SwitchCell(String id) {
        super( id);

        StackPane view = new StackPane();

        Rectangle rec = new Rectangle( 20,20);
        Text text = new Text(this.cellId);

        rec.setStroke(Color.LIGHTSKYBLUE);
        rec.setFill(Color.LIGHTSKYBLUE);

        view.getChildren().addAll(rec, text);
        setView( view);

    }

}