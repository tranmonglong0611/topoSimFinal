import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/*
    author tamolo
    date 5/1/18
*/
public class Trash {
    final static Logger logger = LogManager.getLogger(Trash.class);

    public static void main(String[] args) {

//        String leftAlignFormat = "| %-15s | %-15s | %-45s | %-15s|%n";
//
//        System.out.format("+-----------------+-----------------+-----------------------------------------------+----------------+%n");
//        System.out.format("| Start Node      | End Node        | Info                                          | EventTime      |%n");
//        System.out.format("+-----------------+-----------------+-----------------------------------------------+----------------+%n");
//        for (int i = 0; i < 5; i++) {
//            System.out.format(leftAlignFormat, "00", "03", "Transferring At Link 4--7", "14");
//        }
//        System.out.format("+-----------------+-----------------+-----------------------------------------------+----------------+%n");

        Queue<Integer> q1 = new LinkedList<>();
        q1.add(1);
        q1.add(2);

        Queue<Integer> q2 = new LinkedList<>();
        q2.add(3);
        q2.add(4);

        q1 = q2;

        q2 = null;
        while(q1.size() > 0) {
            int a = q1.remove();
            System.out.println(a);
        }

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
