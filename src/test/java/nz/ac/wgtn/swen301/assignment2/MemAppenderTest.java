package nz.ac.wgtn.swen301.assignment2;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MemAppenderTest {

    /**
     * Tests if the append method is working correctly.
     * Appends two logging events and checks if they are added to the list.
     */
    @Test
    public void appendTest() {
        MemAppender m = new MemAppender();
        Logger logger = Logger.getLogger(JSONLayout.class);
        long startTime = System.currentTimeMillis();
        List<LoggingEvent> logs = new ArrayList<>();

        String message1 = "fatal";
        LoggingEvent le1 = new LoggingEvent(JSONLayout.class.getName(), logger, startTime,
                Level.FATAL, message1, Thread.currentThread().getName(),
                null, null, null, null);

        String message2 = "debug";
        long startTime1 = System.currentTimeMillis();
        LoggingEvent le2 = new LoggingEvent(JSONLayout.class.getName(), logger, startTime1,
                Level.DEBUG, message2, Thread.currentThread().getName(),
                null, null, null, null);

        m.append(le1);
        logs.add(le1);
        m.append(le2);
        logs.add(le2);

        assert m.getCurrentLogs() != null;
        assert m.getCurrentLogs().equals(logs);
        assert m.getDiscardedLogCount() == 0;
    }

    /**
     * Tests if the logs appended are returned via getCurrentLogs method.
     */
    @Test
    public void getCurrentLogsTest() {
        MemAppender m = new MemAppender();
        String message1 = "Info";
        Logger logger = Logger.getLogger(JSONLayout.class);
        long startTime1 = System.currentTimeMillis();
        LoggingEvent le1 = new LoggingEvent(JSONLayout.class.getName(), logger, startTime1,
                Level.INFO, message1, Thread.currentThread().getName(),
                null, null, null, null);
        List<LoggingEvent> logs = new ArrayList<>();
        logs.add(le1);
        m.append(le1);
        List currents = m.getCurrentLogs();
        assert currents != null;
    }

    /**
     * Tests if the getDiscardedLogCount method is working correctly with a maxSize of 1000.
     * Appends a log 1100 times and checks that 100 logs have been discarded.
     */
    @Test
    public void getDiscardedTest_1() {
        MemAppender m = new MemAppender();
        Logger logger = Logger.getLogger(JSONLayout.class);
        long startTime = System.currentTimeMillis();
        String message = "Trace";

        LoggingEvent le = new LoggingEvent(JSONLayout.class.getName(), logger, startTime,
                Level.TRACE, message, Thread.currentThread().getName(),
                null, null, null, null);
        for (int i = 0; i < 1100; i++){
            m.append(le);
        }
        assert m.getDiscardedLogCount() == 100;
        assert m.getMaxSize() == 1000;
    }

    /**
     * Tests if the getDiscardedLogCount method is working correctly with a maxSize of 600.
     * Appends a log 1000 times and checks that 400 logs have been discarded.
     */
    @Test
    public void getDiscardedTest_2() {
        MemAppender m = new MemAppender();
        m.setMaxSize(600);
        Logger logger = Logger.getLogger(JSONLayout.class);
        long startTime = System.currentTimeMillis();
        String message = "Warning, there seems to be a problem.";

        LoggingEvent le = new LoggingEvent(JSONLayout.class.getName(), logger, startTime,
                Level.WARN, message, Thread.currentThread().getName(),
                null, null, null, null);
        for (int i = 0; i < 1000; i++){
            m.append(le);
        }
        assert m.getDiscardedLogCount() == 400;
        assert m.getMaxSize() == 600;
    }

    /**
     * Tests if the exportToJSON method is working correctly.
     */
    @Test
    public void exportTest_1() {
        try {
            MemAppender m = new MemAppender();
            Logger logger = Logger.getLogger(JSONLayout.class);
            long startTime = System.currentTimeMillis();

            String message1 = "Fatal error";
            LoggingEvent le1 = new LoggingEvent(JSONLayout.class.getName(), logger, startTime,
                    Level.FATAL, message1, Thread.currentThread().getName(),
                    null, null, null, null);

            String message2 = "Debug";
            long startTime2 = System.currentTimeMillis();
            LoggingEvent le2 = new LoggingEvent(JSONLayout.class.getName(), logger, startTime2,
                    Level.DEBUG, message2, Thread.currentThread().getName(),
                    null, null, null, null);

            m.append(le1);
            m.append(le2);
            m.exportToJSON("file1");

            Path path = Paths.get("file1.json");

            assert Files.exists(path);
            Files.delete(path);

        }catch (Exception e){

        }
    }

    /**
     * Tests if the requiresLayoutTest returns a false boolean.
     */
    @Test
    public void requiresLayoutTest() {
        MemAppender m = new MemAppender();
        boolean bool = m.requiresLayout();
        assert bool == false;
        m.close();
    }

    /**
     * Tests if the requiresLayoutTest returns a false boolean.
     */
    @Test
    public void getLogsTest() {
        MemAppender m = new MemAppender();
        Logger logger = Logger.getLogger(JSONLayout.class);

        long startTime = System.currentTimeMillis();
        String message1 = "Fatal error";
        LoggingEvent le1 = new LoggingEvent(JSONLayout.class.getName(), logger, startTime,
                Level.FATAL, message1, Thread.currentThread().getName(),
                null, null, null, null);

        String message2 = "Debug";
        long startTime1 = System.currentTimeMillis();
        LoggingEvent le2 = new LoggingEvent(JSONLayout.class.getName(), logger, startTime1,
                Level.DEBUG, message2, Thread.currentThread().getName(),
                null, null, null, null);

        m.append(le1);
        m.append(le2);

        String [] array = m.getLogs();
        assert array != null;
    }

    /**
     * Tests if the getLogCount returns the correct long value.
     */
    @Test
    public void getLogCount_1() {
        MemAppender m = new MemAppender();
        Logger logger = Logger.getLogger(JSONLayout.class);

        long startTime = System.currentTimeMillis();
        String message1 = "Fatal error";
        LoggingEvent le1 = new LoggingEvent(JSONLayout.class.getName(), logger, startTime,
                Level.FATAL, message1, Thread.currentThread().getName(),
                null, null, null, null);

        String message2 = "Debug";
        long startTime1 = System.currentTimeMillis();
        LoggingEvent le2 = new LoggingEvent(JSONLayout.class.getName(), logger, startTime1,
                Level.DEBUG, message2, Thread.currentThread().getName(),
                null, null, null, null);

        String message3 = "Warning! Something went wrong";
        LoggingEvent le3 = new LoggingEvent(JSONLayout.class.getName(), logger, startTime,
                Level.WARN, message3, Thread.currentThread().getName(),
                null, null, null, null);

        String message4 = "Trace";
        LoggingEvent le4 = new LoggingEvent(JSONLayout.class.getName(), logger, startTime,
                Level.TRACE, message4, Thread.currentThread().getName(),
                null, null, null, null);

        m.append(le1);
        m.append(le2);
        m.append(le3);
        m.append(le4);
        long count = m.getLogCount();

        assert count == 4;
    }

    /**
     * Tests if the getLogCount returns the correct long value.
     */
    @Test
    public void getLogCount_2() {
        MemAppender m = new MemAppender();
        Logger logger = Logger.getLogger(JSONLayout.class);
        long startTime = System.currentTimeMillis();
        String message1 = "Fatal error";

        LoggingEvent le1 = new LoggingEvent(JSONLayout.class.getName(), logger, startTime,
                Level.FATAL, message1, Thread.currentThread().getName(),
                null, null, null, null);

        String message2 = "Debug";
        long startTime1 = System.currentTimeMillis();
        LoggingEvent le2 = new LoggingEvent(JSONLayout.class.getName(), logger, startTime1,
                Level.DEBUG, message2, Thread.currentThread().getName(),
                null, null, null, null);

        String message3 = "Warning! Something went wrong";
        LoggingEvent le3 = new LoggingEvent(JSONLayout.class.getName(), logger, startTime,
                Level.WARN, message3, Thread.currentThread().getName(),
                null, null, null, null);

        for (int i = 0; i < 999; i++){
            m.append(le3);
        }
        m.append(le1);
        m.append(le2);
        m.append(le3);
        m.addMBean();
        long count = m.getLogCount();

        assert count == 1000;
    }
}
