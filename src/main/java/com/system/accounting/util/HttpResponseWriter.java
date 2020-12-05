package com.system.accounting.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class HttpResponseWriter {

    private final HttpServletResponse response;

    @SneakyThrows
    public void write(Object data) {
        response.getWriter().write(new ObjectMapper().writeValueAsString(data));
    }
}
