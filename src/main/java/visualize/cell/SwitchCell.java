package visualize.cell;


import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class SwitchCell extends Cell {

    public SwitchCell(String id) {
        super( id);

        StackPane view = new StackPane();

        Circle rec = new Circle(10);
        Text text = new Text(this.cellId);
        text.setFont(new Font(10));

        rec.setStroke(Color.LIGHTSKYBLUE);
        rec.setFill(Color.LIGHTSKYBLUE);

        view.getChildren().addAll(rec, text);
        setView( view);

    }

}