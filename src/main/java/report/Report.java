package report;

import org.apache.logging.log4j.LogManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Report {

    public static PrintWriter traceFile;
    public static PrintWriter resultFile;
    public static PrintWriter graphFile;
    public static PrintWriter topoTheoryParamFile;
    public static Date date = new Date() ;
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM_HH-mm-ss") ;
    public static String folderName = "report_" + dateFormat.format(date);

    public static PrintWriter getTraceFile() {
        if(traceFile != null) {
            return traceFile;
        }else {
            try {
                new File("./" + folderName).mkdirs();
                FileWriter fw = new FileWriter(folderName + "/tracing.txt", false);
                BufferedWriter bw = new BufferedWriter(fw);
                traceFile = new PrintWriter(bw);
                return traceFile;
            }catch (Exception e) {
                LogManager.getLogger(Report.class.getName());
            }
        }
        LogManager.getLogger(Report.class.getName()).error("Cannot get result file");
        return null;
    }

    public static PrintWriter getResultFile() {
        if(resultFile != null) {
            return resultFile;
        }else {
            try {
                new File("./" + folderName).mkdirs();
                FileWriter fw = new FileWriter(folderName + "/report.txt", false);
                BufferedWriter bw = new BufferedWriter(fw);
                resultFile = new PrintWriter(bw);
                return resultFile;
            }catch (Exception e) {
                LogManager.getLogger(Report.class.getName());
            }
        }
        LogManager.getLogger(Report.class.getName()).error("Cannot get result file");
        return null;
    }

    public static PrintWriter getTopoInfoFile() {
        if(graphFile != null) {
            return graphFile;
        }else {
            try {
                new File("./" + folderName).mkdirs();
                FileWriter fw = new FileWriter(folderName + "/topoInfo.txt", false);
                BufferedWriter bw = new BufferedWriter(fw);
                graphFile = new PrintWriter(bw);
                return graphFile;
            }catch (Exception e) {
                LogManager.getLogger(Report.class.getName());
            }
        }
        LogManager.getLogger(Report.class.getName()).error("Cannot get result file");
        return null;
    }

    public static PrintWriter getTopoTheoryParam() {
        if(topoTheoryParamFile != null) {
            return topoTheoryParamFile;
        }else {
            try {
                new File("./" + folderName).mkdirs();
                FileWriter fw = new FileWriter(folderName + "/topoTheory.txt", false);
                BufferedWriter bw = new BufferedWriter(fw);
                topoTheoryParamFile = new PrintWriter(bw);
                return topoTheoryParamFile;
            }catch (Exception e) {
                LogManager.getLogger(Report.class.getName());
            }
        }
        LogManager.getLogger(Report.class.getName()).error("Cannot get result file");
        return null;
    }

    public static void endSim() {
        LogManager.getLogger(Report.class.getName()).info("All info of the simulation was saved to " + folderName);

        if(traceFile != null) {
            traceFile.close();
        }
        if(resultFile != null) {
            resultFile.close();
        }
        if(graphFile != null) {
            graphFile.close();
        }
        if(topoTheoryParamFile != null) {
            topoTheoryParamFile.close();
        }
    }

    public static void main(String[] args) {
        Report.getTraceFile().append("thu nghiem ti");
        Report.getTraceFile().append("lam gi ma cang voi tao");
        Report.getTraceFile().close();
    }
}
