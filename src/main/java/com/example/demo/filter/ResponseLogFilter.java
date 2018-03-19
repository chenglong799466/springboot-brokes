package com.example.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author chenglong
 * @date 2018.3.19
 */
public class ResponseLogFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(ResponseLogFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ByteArrayOutputStream copy = new ByteArrayOutputStream();
        HttpServletResponse responseWrapper = new HttpServletResponseWrapper((HttpServletResponse) response);

        String url = "";
        String qs = "";
        if (request instanceof HttpServletRequest) {
            url = ((HttpServletRequest)request).getRequestURI().toString();
            qs = "?" + ((HttpServletRequest)request).getQueryString();
        }

        try {
            chain.doFilter(request, response);
        } finally {
            String content = String.format(
                    "%d %s%s %s %s",
                    responseWrapper.getStatus(),
                    url,
                    qs,
                    responseWrapper.getHeaderNames().stream()
                            .map(name -> String.format("%s: %s", name, responseWrapper.getHeaders(name)))
                            .collect(Collectors.toList()),
                    copy.toString("UTF-8"));

            try {
                logger.info(content);
            } catch (Exception e) {
                logger.error("log response error", e);
            }

        }

    }
}
