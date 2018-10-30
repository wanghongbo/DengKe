package com.dengke.servicebg.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class LoginInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handle)throws Exception {
        String token = request.getParameter("token");
//        if (StringUtils.isBlank(token) || !redisTemplate.hasKey(Constants.CACHE_TOKEN + token)) {
//            request.getRequestDispatcher("/user/noLogin").forward(request, response);
//            return false;
//        }
        return true;
    }
}
