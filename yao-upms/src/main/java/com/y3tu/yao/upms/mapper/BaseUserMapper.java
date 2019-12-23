package com.y3tu.yao.upms.mapper;


import com.y3tu.yao.upms.model.entity.BaseUser;

public interface BaseUserMapper {
    /**
     *功能描述 :新增用户
     * @author zht
     * @date 2019/11/28
     * @param record
     * @return int
     */
    int insert(BaseUser record);


    /**
     *功能描述 :根据ID查找baseUser
     * @author zht
     * @date 2019/11/28
     * @param id
     * @return com.y3tu.yao.upms.model.entity.BaseUser
     */
    BaseUser selectById(Integer id);

}