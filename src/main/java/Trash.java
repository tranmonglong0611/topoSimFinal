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

        Trash obj = new Trash();
        obj.runMe("mkyong");

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
