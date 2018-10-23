package com.tompy.response;

import com.tompy.common.Builder;

public interface ResponseBuilder extends Builder<Response> {
    ResponseBuilder source(String source);

    ResponseBuilder text(String text);
}
