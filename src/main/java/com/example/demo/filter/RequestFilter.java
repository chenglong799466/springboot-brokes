package com.example.demo.filter;

import com.example.demo.model.RespCode;
import com.example.demo.model.RespEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class RequestFilter extends GenericFilterBean {
    private static final Map<String, String> API_WHITE_LIST = new HashMap<String, String>() {
        {
            put("/users", "POST");
            put("/hello", "POST");
            put("/users", "GET");
            put("/hello", "GET");

        }
    };

    /**
     * 简单测试springMVC filter的使用
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //api是否在白名单中
        String reqURI = req.getRequestURI();
        String method = req.getMethod();
        if (API_WHITE_LIST.containsKey(reqURI) && API_WHITE_LIST.get(reqURI).equals(method)) {
            chain.doFilter(request, response);
            return;
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.setCharacterEncoding("UTF-8");
            resp.setHeader("Content-type", "application/json;charset=UTF-8");

            RespEntity respEntity = new RespEntity();
            respEntity.setRespCode(RespCode.UNAUTHORIZED);

            ObjectMapper mapper = new ObjectMapper();
            String respStr = mapper.writeValueAsString(respEntity);
            PrintWriter writer = resp.getWriter();
            writer.print(respStr);
        }
    }
}
