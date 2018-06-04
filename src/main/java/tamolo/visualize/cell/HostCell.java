package tamolo.visualize.cell;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class HostCell extends Cell {

    public HostCell(String id) {
        super( id);

        StackPane view = new StackPane();

        Circle rec = new Circle( 5);
        Text text = new Text(this.cellId);
        text.setFont(new Font(5));

        rec.setStroke(Color.RED);
        rec.setFill(Color.RED);

        view.getChildren().addAll(rec, text);
        setView( view);

    }

}