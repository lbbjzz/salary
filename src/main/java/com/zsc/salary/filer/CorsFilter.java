package com.zsc.salary.filer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * <p>
 * 配置跨域请求
 * </p>
 *
 * @author D
 * @version 1.0
 * @date 2020/6/6 7:46
 */
@Configuration
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 不使用*，自动适配跨域域名，避免携带Cookie时失效
        String origin = request.getHeader("Origin");
        if(StringUtils.isNotBlank(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }

        // 自适应所有自定义头
        String headers = request.getHeader("Access-Control-Request-Headers");
        if(StringUtils.isNotBlank(headers)) {
            response.setHeader("Access-Control-Allow-Headers", headers);
            response.setHeader("Access-Control-Expose-Headers", headers);
        }

        // 允许跨域的请求方法类型
        response.setHeader("Access-Control-Allow-Methods", "*");
        // 预检命令（OPTIONS）缓存时间，单位：秒
        response.setHeader("Access-Control-Max-Age", "3600");
        // 明确许可客户端发送Cookie，不允许删除字段即可
        response.setHeader("Access-Control-Allow-Credentials", "true");

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
