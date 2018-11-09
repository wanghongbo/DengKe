package com.dengke.servicebg.controller;

import com.dengke.entity.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class BaseController {

    @Resource
    protected RedisTemplate redisTemplate;

    protected static Logger log = LoggerFactory.getLogger(BaseController.class);

    /**
     * 获取token，如果没有则生成后返回
     * @param request
     * @return
     */
    protected String token(final HttpServletRequest request) {
        String token = (String)request.getSession().getAttribute(Constants.SESSION_TOKEN);
        if(StringUtils.isBlank(token)){
            token =  UUID.randomUUID().toString();
            request.getSession().setAttribute(Constants.SESSION_TOKEN,token);
        }
        return token;
    }

    /**
     * 获取客户端真实IP
     * @param request
     * @return
     */
    protected String getIp(final HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
//	    String headers = JSON.toJSONString( request.getHeaderNames());
//	    System.out.println(ip);
//	    System.out.println(headers);
//	    System.out.println(request.getHeader("Referer"));
//	    Enumeration<String> e = request.getHeaderNames();
//	    while(e.hasMoreElements()){
//	    	System.out.println(request.getHeader(e.nextElement()));
//	    }
        return ip;
    }
}
