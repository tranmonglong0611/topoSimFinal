package visualize;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class HostCell extends Cell {

    public HostCell(int id) {
        super(id);

        StackPane view = new StackPane();
        Rectangle rec = new Rectangle(5, 5);
        Text text = new Text(Integer.toString(this.cellId));

        rec.setStroke(Color.RED);
        rec.setFill(Color.RED);

        view.getChildren().addAll(rec, text);
        setView(view);
    }
}
