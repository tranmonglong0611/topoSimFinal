package visualize;


import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SwitchCell extends Cell{

    public SwitchCell(int id) {
        super(id);

        StackPane view = new StackPane();
        Rectangle rec = new Rectangle(7, 7);
        Text text = new Text(Integer.toString(this.cellId));

        rec.setStroke(Color.LIGHTBLUE);
        rec.setFill(Color.LIGHTBLUE);

        view.getChildren().addAll(rec, text);
        setView(view);
    }
}
