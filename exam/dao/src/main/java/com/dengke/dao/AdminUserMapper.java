package com.dengke.dao;

import com.dengke.entity.AdminUser;
import org.apache.ibatis.annotations.Param;

public interface AdminUserMapper {

    AdminUser getUser(@Param("userName") String userName);

    void updatePassword(@Param("userName") String userName, @Param("password") String password);
}
