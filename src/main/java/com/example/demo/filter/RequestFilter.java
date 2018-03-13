package com.example.demo.filter;

import com.example.demo.model.RespCode;
import com.example.demo.model.RespEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.regex.Pattern;

/**
 * @author ChengLong
 * @date 2018.3.2
 */
public class RequestFilter extends GenericFilterBean {

    private static final Map<String, String> API_WHITE_LIST = new HashMap<String, String>() {
        {
            put("/users", "POST");
            put("/hello", "POST");
            put("/users", "GET");
            put("/hello", "GET");

        }
    };

    private static final Pattern[] API_WHITE_LIST_PATTERNS = new Pattern[]{
            Pattern.compile("/users/[a-z]*(/.+)*"),
            Pattern.compile("/hello/[a-z]*(/.+)*"),
            Pattern.compile("/druid/.*")

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

        // api是否在白名单中
        String reqURI = req.getRequestURI();
        String method = req.getMethod();
        for (Pattern p : API_WHITE_LIST_PATTERNS) {
            if (p.matcher(reqURI).matches()) {
                chain.doFilter(request, response);
                return;
            }
        }
        this.sendResponse(resp);

    }

    /**
     * 返回鉴权失败的response
     *
     * @param resp
     */
    public void sendResponse(HttpServletResponse resp) {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-type", "application/json;charset=UTF-8");

        RespEntity respEntity = new RespEntity();
        respEntity.setRespCode(RespCode.UNAUTHORIZED);

        ObjectMapper mapper = new ObjectMapper();
        String respStr = null;
        PrintWriter writer = null;
        try {
            respStr = mapper.writeValueAsString(respEntity);
            writer = resp.getWriter();
        } catch (IOException e) {
            logger.info("Return response IOException : " + e);
        }
        writer.print(respStr);
    }


}