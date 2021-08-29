package nz.ac.wgtn.swen301.assignment2.example;

import nz.ac.wgtn.swen301.assignment2.JSONLayout;
import nz.ac.wgtn.swen301.assignment2.MemAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import java.util.Random;

public class LogRunner {
    public static void main (String[] args){
        String[] message = {"Hi there!", "Processed", "Logging event created"};
        Level[] levels = {Level.WARN, Level.ERROR, Level.DEBUG, Level.FATAL, Level.ALL,
        Level.INFO, Level.TRACE, Level.OFF};

        MemAppender m = new MemAppender();
        Logger logger = Logger.getLogger(JSONLayout.class.toString());
        long startTime = System.currentTimeMillis();
        m.addMBean();
        int t = 120;

        try {
            while (t > 0) {
                Random r = new Random();
                LoggingEvent le = new LoggingEvent(JSONLayout.class.getName(), logger, startTime,
                        levels[r.nextInt(levels.length)], message[r.nextInt(message.length)],
                        Thread.currentThread().getName(),
                        null, null, null, null);
                m.append(le);
                Thread.sleep(1000);
                t --;
            }
        }catch (Exception e){

        }
    }
}
