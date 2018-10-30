package com.dengke.servicebg.controller;


import com.dengke.entity.common.JsonObjectUtil;
import com.dengke.entity.common.RtnConstants;
import com.dengke.entity.common.Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/user",produces = "application/json;charset=UTF-8")
public class UserController extends BaseController{

    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        try {
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            if(Utils.existsBlank(userName,password)){
                return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"用户名或密码错误","");
            }

            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"","");
        }catch (Exception e){
            log.error("登录错误",e);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"用户名或密码错误","");
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"","");
    }

    @RequestMapping("/noLogin")
    public String noLogin(){
        return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.NO_LOGIN,"您未登录","");
    }

}
