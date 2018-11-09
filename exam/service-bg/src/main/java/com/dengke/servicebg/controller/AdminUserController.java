package com.dengke.servicebg.controller;

import com.dengke.entity.AdminUser;
import com.dengke.entity.common.*;
import com.dengke.servicebg.service.AdminUserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/user",produces = "application/json;charset=UTF-8")
public class AdminUserController extends  BaseController{


    @Resource
    private AdminUserService adminUserService;

    @RequestMapping("/getToken")
    public String getToken(HttpServletRequest request){
        try {
            String token = token(request);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"",token);
        }catch (Exception e){
            log.error("获取token失败",e);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"","");
        }
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, @RequestBody AdminUser user){
        try{
            String userName = user.getUserName();
            String password = user.getPassword();
            if(Utils.existsBlank(userName,password)){
                return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"用户名或密码错误","");
            }
            AdminUser realUser = adminUserService.getUser(userName);
            if(realUser == null){
                return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"用户名或密码错误","");
            }
            if(!password.equals(MD5Util.md5(token(request)+realUser.getPassword()))){
                return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"用户名或密码错误","");
            }
            request.getSession().setAttribute(Constants.SESSION_USER_LOGIN,1);
            request.getSession().setAttribute(Constants.SESSION_USER_NAME,realUser);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"登陆成功","");
        }catch (Exception e){
            log.error("登陆错误",e);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"用户名或密码错误","");

        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        try {
            request.getSession().invalidate();
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"","");
        }catch (Exception e){
            log.error("注销账户失败",e);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"","");
        }
    }

    @RequestMapping("/noLogin")
    public String noLogin(){
        return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.NO_LOGIN,"您未登录","");
    }

    @RequestMapping("/getUserName")
    public String getUserName(HttpServletRequest request){
        try {
            AdminUser user = (AdminUser)request.getSession().getAttribute(Constants.SESSION_USER_NAME);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"",user.getUserName());
        }catch (Exception e) {
            log.error("获取用户名出错",e);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED, "", "");
        }
    }
}
