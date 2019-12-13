package com.y3tu.yao.upms.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName: 会员信息
 * Description:
 * date: 2019年11月27日23:40:11
 *
 * @author zht
 */
@Data
public class STFStaff implements Serializable {
    /**
     * id
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
     * open_id
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
     * 名称
     */
    private String name;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * avatar
     */
    private String avatar;

    /**
     * 性别
     */
    private String gender;

    /**
     * This field corresponds to the database table cst_member
     */
    private static final long serialVersionUID = 1L;

}