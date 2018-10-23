package com.tompy.response;

/**
 * A response to be displayed
 */
public interface Response {

    /**
     * The priority of the response
     * @return
     */
    long getSequence();

    /**
     * The final string representation of the response
     *
     * @return
     */
    String render();
}
