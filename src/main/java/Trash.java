import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Stack;

/*
    author tamolo
    date 5/1/18
*/
public class Trash {
    final static Logger logger = LogManager.getLogger(Trash.class);

    public static void main(String[] args) {

        String leftAlignFormat = "| %-15s | %-15s | %-45s | %-15s|%n";

        System.out.format("+-----------------+-----------------+-----------------------------------------------+----------------+%n");
        System.out.format("| Start Node      | End Node        | Info                                          | EventTime      |%n");
        System.out.format("+-----------------+-----------------+-----------------------------------------------+----------------+%n");
        for (int i = 0; i < 5; i++) {
            System.out.format(leftAlignFormat, "00", "03", "Transferring At Link 4--7", "14");
        }
        System.out.format("+-----------------+-----------------+-----------------------------------------------+----------------+%n");

    }

    private void runMe(String parameter){

        if(logger.isDebugEnabled()){
            logger.debug("This is debug : " + parameter);
        }

        if(logger.isInfoEnabled()){
            logger.info("This is info : " + parameter);
        }

        logger.warn("This is warn : " + parameter);
        logger.error("This is error : " + parameter);
        logger.fatal("This is fatal : " + parameter);

    }

}
