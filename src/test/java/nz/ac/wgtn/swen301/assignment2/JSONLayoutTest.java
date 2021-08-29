package nz.ac.wgtn.swen301.assignment2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Test;

public class JSONLayoutTest {

    /**
     * Tests level WARN when Logging event is correct.
     * @throws Exception
     */
    @Test
    public void warnTest() throws Exception {
        JSONLayout jsonLayout = new JSONLayout();
        Logger logger = Logger.getLogger(JSONLayout.class);
        long startTime = System.currentTimeMillis();
        String message = "Warning, something went wrong!";
        LoggingEvent le = new LoggingEvent(this.getClass().toString(), logger, startTime,
                Level.WARN, message, Thread.currentThread().getName(),
                null, null, null, null);
        String layout = jsonLayout.format(le);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jNode = mapper.readTree(layout);

        assert jNode.get("logger").asText().equals("nz.ac.wgtn.swen301.assignment2.JSONLayout");
        assert jNode.get("level").asText().equals("WARN");
        assert jNode.get("starttime").asLong() == startTime;
        assert jNode.get("thread").asText().equals(le.getThreadName());
        assert jNode.get("message").asText().equals("Warning, something went wrong!");
        assert jNode != null;
    }

    /**
     * Tests level ERROR when Logging event is correct.
     * @throws Exception
     */
    @Test
    public void errorTest() throws Exception {
        JSONLayout jsonLayout = new JSONLayout();
        Logger logger = Logger.getLogger(JSONLayout.class);
        long startTime = System.currentTimeMillis();
        String message = "Sorry, there appears to be an error!";
        LoggingEvent le = new LoggingEvent(this.getClass().toString(), logger, startTime,
                Level.ERROR, message, Thread.currentThread().getName(),
                null, null, null, null);
        String layout = jsonLayout.format(le);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jNode = mapper.readTree(layout);

        assert jNode.get("logger").asText().equals("nz.ac.wgtn.swen301.assignment2.JSONLayout");
        assert jNode.get("level").asText().equals("ERROR");
        assert jNode.get("starttime").asLong() == startTime;
        assert jNode.get("thread").asText().equals(le.getThreadName());
        assert jNode.get("message").asText().equals("Sorry, there appears to be an error!");
        assert jNode != null;
    }

    /**
     * Tests for a null logging event.
     * @throws Exception
     */
    @Test
    public void nullTest() throws Exception {
        JSONLayout jsonLayout = new JSONLayout();
        LoggingEvent le = null;
        String layout = jsonLayout.format(le);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jNode = mapper.readTree(layout);

        assert layout.equals("");
        assert jNode.get("logger") == null;
        assert jNode.get("level") == null;
        assert jNode.get("starttime") == null;
        assert jNode.get("thread") == null;
        assert jNode.get("message") == null;
    }

    /**
     * Tests if ignoresThrowable() returns false;
     * @throws Exception
     */
    @Test
    public void ignoresThrowableTest() throws Exception {
        JSONLayout jsonLayout = new JSONLayout();
        boolean bool = jsonLayout.ignoresThrowable();
        assert bool == false;
        jsonLayout.activateOptions();

    }
}
