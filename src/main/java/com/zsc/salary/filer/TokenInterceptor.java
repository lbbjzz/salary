package com.zsc.salary.filer;

import com.zsc.salary.utils.RedisUtil;
import com.zsc.salary.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Token拦截器
 * </p>
 *
 * @author D
 * @version 1.0
 * @date 2020/7/14 9:13
 */
@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private TokenUtil tokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("Authentication-Token");
        String userId = request.getHeader("Authentication-adminId");
        log.error("token = " + token);
        log.error("userId = " + userId);

        //处于登录状态
        if(!"".equals(token) && token != null && userId != null) {
            log.error("进来了");
            String key = "token:" + userId;
            if(!redisUtil.hasKey(key)) {
                log.info("redisUtil");
                response.setStatus(520);
                return false;
            }
            try {
                Claims claims = tokenUtil.parseJwt(token);
            } catch (ExpiredJwtException | IllegalArgumentException e) {
                e.printStackTrace();
                log.info("IllegalArgumentException");
                response.setStatus(520);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
