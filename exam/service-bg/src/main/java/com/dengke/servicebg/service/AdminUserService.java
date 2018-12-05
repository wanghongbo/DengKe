package com.dengke.servicebg.service;

import com.dengke.dao.AdminUserMapper;
import com.dengke.entity.AdminUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminUserService {


    protected static Logger log = LoggerFactory.getLogger(AdminUserService.class);

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private AdminUserMapper adminUserMapper;

    public AdminUser getUser(String userName){
        return adminUserMapper.getUser(userName);
    }

    public void updatePassword(String userName,String password){
        try{
            adminUserMapper.updatePassword(userName,password);
        }catch (Exception e){
            log.error("修改密码出错",e);
            throw  e;
        }
    }
}
