package com.tompy.maze;


import com.tompy.execution.Execution;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Hello world!
 */
public class AppMaze {
    private final static Logger LOGGER = LogManager.getLogger(AppMaze.class);

    public static void main(String[] args) {
        AppMaze a = new AppMaze();
        System.exit(a.go(args));
    }

    public int go(String[] args) {
        return new Execution().execute(Maze.getFactory());
    }
}
