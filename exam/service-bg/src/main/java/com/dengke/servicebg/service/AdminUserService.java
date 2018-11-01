package com.dengke.servicebg.service;

import com.dengke.dao.AdminUserMapper;
import com.dengke.entity.AdminUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminUserService {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private AdminUserMapper adminUserMapper;

    public AdminUser getUser(String userName){
        return adminUserMapper.getUser(userName);
    }
}
