package com.dengke.servicefg.controller;


import com.dengke.entity.SubjectType;
import com.dengke.entity.common.JsonObjectUtil;
import com.dengke.entity.common.RtnConstants;
import com.dengke.servicefg.service.SubjectService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/subject",produces = "application/json;charset=UTF-8")
public class SubjectController extends BaseController {
    @Resource
    private SubjectService subjectService;

    @RequestMapping("/getTypes")
    public String getTypes(HttpServletRequest request){
        try {
            List<SubjectType> subjectTypes = subjectService.getAllTypes();
            if(CollectionUtils.isEmpty(subjectTypes)){
                return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"","");
            }
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.OK,"",subjectTypes);
        }catch (Exception e){
            log.error("获取题目类型出错",e);
            return JsonObjectUtil.getRtnAndDataJsonObject(RtnConstants.FAILED,"","");
        }
    }
}
