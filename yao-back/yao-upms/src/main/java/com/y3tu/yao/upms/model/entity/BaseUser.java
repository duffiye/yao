package com.y3tu.yao.upms.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: 基础用户信息
 * Description:
 * date: 2019年11月27日23:40:11
 *
 * @author zht
 */
@Data
public class BaseUser implements Serializable {
    /**
     * .id
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;

    /**
     * 删除时间
     */
    private Date deletedAt;

    /**
     * openID
     */
    private String openID;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 用户名
     */
    private String username;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 
     * This field corresponds to the database table base_user
     */
    private static final long serialVersionUID = 1L;

}