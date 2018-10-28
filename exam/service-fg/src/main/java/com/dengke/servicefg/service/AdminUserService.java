package com.dengke.servicefg.service;

import com.alibaba.fastjson.JSON;
import com.dengke.dao.AdminUserMapper;
import com.dengke.entity.AdminUser;
import org.apache.http.client.utils.DateUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
