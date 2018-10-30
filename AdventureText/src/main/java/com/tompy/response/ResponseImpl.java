package com.tompy.response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;

public class ResponseImpl implements Response, Comparable<Response>, Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger(ResponseImpl.class);
    private static long counter = 0;
    private long sequence;
    private String source;
    private String text;

    private ResponseImpl(String source, String text) {
        this.source = source;
        this.text = text;
        sequence = counter++;
    }

    public static ResponseBuilderFactory createBuilderFactory() {
        return ResponseImpl::createBuilder;
    }

    public static ResponseBuilder createBuilder() {
        return new ResponseImpl.ResponseBuilderImpl();
    }

    @Override
    public long getSequence() {
        return sequence;
    }

    @Override
    public String render() {
        LOGGER.info("Rendering [{}-{}]", new String[]{source, text});
        return text;
    }

    @Override
    public int compareTo(Response other) {
        return (int) (other.getSequence() - this.getSequence());
    }

    public static class ResponseBuilderImpl implements ResponseBuilder {
        private String source;
        private String text;

        @Override
        public ResponseBuilder source(String source) {
            this.source = source;
            return this;
        }

        @Override
        public ResponseBuilder text(String text) {
            this.text = text;
            return this;
        }

        @Override
        public Response build() {
            return new ResponseImpl(source, text);
        }
    }

}
