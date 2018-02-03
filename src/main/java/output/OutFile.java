package output;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OutFile {

    public Path path;


    public OutFile() {
        path = Paths.get("report.txt");
    }

    public OutFile(String fileName) {
        path = Paths.get(fileName);
//        byte[] strToBytes = "haha".getBytes();

//        Files.write(path, strToBytes);
    }

    public void append(String text) {
        try {
            byte[] textToBytes = text.getBytes();
            Files.write(path, textToBytes);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            OutFile a = new OutFile("src/output/FUckla.txt");
        }catch (Exception e){
            e.getStackTrace();
        }
    }
}
