package nz.ac.wgtn.swen301.assignment2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

public class JSONLayout extends Layout {
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Formats the logging event according to JSON.
     */
    @Override
    public String format(LoggingEvent loggingEvent) {
        String j = "";
        try {
            ObjectNode n = mapper.createObjectNode();
            n.put("logger", loggingEvent.getLoggerName());
            n.put("level", loggingEvent.getLevel().toString());
            n.put("starttime", Long.toString(loggingEvent.getTimeStamp()));
            n.put("thread", loggingEvent.getThreadName());
            n.put("message", loggingEvent.getMessage().toString());

            j = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(n);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return j;
    }

    @Override
    public boolean ignoresThrowable() {
        return false;
    }

    @Override
    public void activateOptions() {

    }
}
