package com.dengke.dao;

import com.dengke.entity.Subject;
import com.dengke.entity.SubjectType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubjectMapper {
    List<SubjectType> getAllTypes();
    List<Subject> getSubjects(@Param("type") String type,@Param("status")String status);
    void add(Subject subject);
    void updateStatus(@Param("id")long id,@Param("status")String status);


    int getCount(@Param("type")String type,@Param("status")String status);
    List<Subject> getPage(@Param("type")String type,@Param("status")String status,@Param("start")int start,@Param("pageSize")int pageSize);
}
