package com.dengke.common.service;

import com.dengke.common.utils.SpringUtil;
import com.dengke.entity.common.SubjectTypeEnum;

public class SubjectStrategyFatory {

    public static SubjectStrategy getStrategy(String type){
        String strategy = SubjectTypeEnum.getStrategy(type);
        return SpringUtil.getBean("subject"+strategy+"Strategy", SubjectStrategy.class);
    }
}
