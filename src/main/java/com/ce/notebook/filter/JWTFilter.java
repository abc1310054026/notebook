package com.ce.notebook.filter;

import com.ce.notebook.utils.SecurityUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt Token的校验
 *
 * @author: ce
 * @create: 2018-12-20 16:13
 **/
@WebFilter(urlPatterns = "/*", initParams = {
        @WebInitParam(name = "excludeUrl", value = "/security/login")
})
public class JWTFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);
    private static String[] excludeUrl;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Custom Filter: JWTFilter initialization started");
        excludeUrl = filterConfig.getInitParameter("excludeUrl").split(",");
        logger.info("Custom Filter: JWTFilter initializing completed");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

//        放行请求类型为OPTIONS的请求
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
//        放行包含在excludeUrl中的内容
        String path = request.getServletPath();
        for (String url : excludeUrl) {
            if (url.contains(path)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        String authorization = request.getHeader("authorization");
        if (authorization == null) {
            throw new ServletException("Authorization is null");
        }
        authorization = authorization.substring(7);
        SecurityUtils.JWTValidate(authorization);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        logger.info("JWTFilter destory...");
    }
}
