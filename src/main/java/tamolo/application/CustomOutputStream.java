package tamolo.application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.OutputStream;

/*
    author tamolo
    date 5/5/18
*/
public class CustomOutputStream extends OutputStream {
    private TextArea textArea;

    public CustomOutputStream(TextArea textArea) {
        this.textArea = textArea;
        this.textArea.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,
                                Object newValue) {
                textArea.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
                //use Double.MIN_VALUE to scroll to the top
            }
        });
    }

    @Override
    public void write(int b) throws IOException {
        // redirects data to the text area
//        textArea.append(String.valueOf((char)b));
//        System.out.println("write function running");
        textArea.appendText(String.valueOf((char)b));
        // scrolls the text area to the end of data
//        textArea.setCaretPosition(textArea.getDocument().getLength());
//        textArea.
    }

}