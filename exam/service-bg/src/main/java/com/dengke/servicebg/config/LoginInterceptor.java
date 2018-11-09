package com.dengke.servicebg.config;

import com.dengke.entity.common.Constants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class LoginInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handle)throws Exception {
        Object login = request.getSession().getAttribute(Constants.SESSION_USER_LOGIN);
        if (login == null) {
            request.getRequestDispatcher("/user/noLogin").forward(request, response);
            return false;
        }
        return true;
    }
}
