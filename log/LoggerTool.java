package shop.log;

import org.apache.log4j.Logger;
/**
 * Created by Administrator on 2016/7/29.
 */
public class LoggerTool {
    private static Logger logger = Logger.getLogger(LoggerTool.class);
    public static Logger getDefaultLogger() {
        return logger;
    }
}