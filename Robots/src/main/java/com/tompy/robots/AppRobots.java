package com.tompy.robots;

import com.tompy.execution.Execution;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppRobots {

    private final static Logger LOGGER = LogManager.getLogger(AppRobots.class);

    public static void main(String[] args) {
        AppRobots a = new AppRobots();
        System.exit(a.go(args));
    }

    public int go(String[] args) {
        return new Execution().execute(Robots.getFactory());
    }
}
