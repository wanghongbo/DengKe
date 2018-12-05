package com.dengke.servicebg.service;

import com.dengke.dao.SubjectMapper;
import com.dengke.entity.Subject;
import com.dengke.entity.SubjectType;
import com.dengke.entity.common.Constants;
import com.dengke.entity.common.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SubjectService {

    protected  static Logger log = LoggerFactory.getLogger(SubjectService.class);

    @Resource
    private SubjectMapper subjectMapper;



    public List<SubjectType> getAllTypes(){
        try {
            return subjectMapper.getAllTypes();
        }catch (Exception e){
            log.error("获取题目类型出错",e);
            return null;
        }
    }

    public Page.PagedData<Subject> getSubjects(String type,String status,int pageNo,int pageSize){
        try {
            int totalCount = subjectMapper.getCount(type,status);
            Page.Paging paging = Page.getPaging(pageNo,pageSize,totalCount);
            List<Subject> subjects = subjectMapper.getPage(type,status,paging.startIndex,paging.pagesize);
            return new Page.PagedData<Subject>(paging,subjects);
        }catch (Exception e){
            log.error("获取题目出错",e);
            return null;
        }
    }

    public void addSubject(Subject subject){
        try {
            subjectMapper.add(subject);
        }catch (Exception e){
            log.error("新增题目出错",e);
            throw  e;
        }
    }

    public void updateSubject(Subject subject){
        try {
            subjectMapper.update(subject);
        }catch (Exception e){
            log.error("编辑题目出错",e);
            throw  e;
        }
    }

    public void deleteSubject(long id){
        try {
            subjectMapper.updateStatus(id,Constants.SUBJECT_STATUS_DEL);
        }catch (Exception e){
            log.error("删除题目出错",e);
            throw  e;
        }
    }

    public void emptySubjects(String type){
        try{
            subjectMapper.emptySubjects(type,Constants.SUBJECT_STATUS_DEL);
        }catch (Exception e){
            log.error("清空题库出错",e);
            throw  e;
        }
    }
}
