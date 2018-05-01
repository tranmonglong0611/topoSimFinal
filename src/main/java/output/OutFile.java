package output;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OutFile {

    public static PrintWriter out;
    public static PrintWriter getFile() {
        if(out != null) {
            return out;
        }else {
            try {
                FileWriter fw = new FileWriter("myfile.txt", false);
                BufferedWriter bw = new BufferedWriter(fw);
                out = new PrintWriter(bw);
                return out;
            }catch (Exception e) {
                LogManager.getLogger(OutFile.class.getName());
            }
        }
        LogManager.getLogger(OutFile.class.getName()).error("Cannot get result file");
        return null;
    }


    public static void main(String[] args) {
        OutFile.getFile().append("thu nghiem ti");
        OutFile.getFile().append("lam gi ma cang voi tao");
        OutFile.getFile().close();
    }
}
