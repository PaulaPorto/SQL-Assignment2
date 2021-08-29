package nz.ac.wgtn.swen301.assignment2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemAppender extends AppenderSkeleton implements MemAppenderMBean {
    private List<LoggingEvent> list = new ArrayList<>(); //list of logging events.
    private long maxSize = 1000; //the max size allowed for the list of logging events.
    private long discarded = 0;  //initialize the discarded count.

    /**
     * Appends the logging events. Adds them to a list.
     *
     * @param loggingEvent being appended to the list.
     */
    @Override
    public void append(LoggingEvent loggingEvent) {
        list.add(loggingEvent);
        while (list.size() > maxSize){
            list.remove(0);
            discarded ++;
        }
    }

    /**
     * Gets the current logs in the list.
     *
     * @return list of logging events.
     */
    public List<LoggingEvent> getCurrentLogs(){
        return Collections.unmodifiableList(list);
    }

    /**
     * Gets an array of String logs from the list of logs.
     * @return String[] of logs
     */
    @Override
    public String[] getLogs() {
        PatternLayout patternLayout = new PatternLayout();
        LoggingEvent le;
        String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++){
            le = list.get(i);
            array[i] = patternLayout.format(le);
        }
        return array;
    }

    /**
     * The number of currently stored logs.
     *
     * @return long count
     */
    @Override
    public long getLogCount() {
        return list.size();
    }

    /**
     * Discards logging events if the list goes over the maxSize
     * and counts how many events were discarded.
     *
     * @return the ammount of discarded logging events.
     */
    public long getDiscardedLogCount(){
        return discarded;
    }

    /**
     * Exports stored log events to a JSON file.
     *
     * @param fileName for the JSON file.
     */
    public void exportToJSON(String fileName){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        ArrayNode an = mapper.createArrayNode();
        try {
            JSONLayout jsonLayout = new JSONLayout();
            for (int i = 0; i < list.size(); i++) {
                JsonNode n = mapper.readTree(jsonLayout.format(list.get(i)));
                an.add(n);
            }
            mapper.writerWithDefaultPrettyPrinter().
                    writeValue(Paths.get(fileName + ".json").toFile(), an);
        } catch (Exception e){
        }
    }

    public void addMBean(){
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("nz.ac.wgtn.swen301.assignment2:type=MemAppender");
            mbs.registerMBean(this, name);
        }catch (Exception e){

        }
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    /**
     * Getter method for the maxSize field.
     *
     * @return the max size.
     */
    public long getMaxSize(){
        return maxSize;
    }

    /**
     * Setter method for the max size field.
     *
     * @param count of discarded logs.
     */
    public void setMaxSize(long count){
        maxSize = count;
    }
}
