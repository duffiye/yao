package com.y3tu.yao.common.vo.sys;

import com.y3tu.yao.common.vo.sys.RoleVO;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Data
public class UserVO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 删除时间
     */
    private Date deletedAt;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * uid
     */
    private String udi;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 简介
     */
    private String mobile;

    /**
     *
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private Integer state;

    /**
     * 用户名
     */
    private String username;

    /**
     * 部门Id
     */
    private String departmentID;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 角色列表
     */
    private List<RoleVO> roles;

    /**
     * 资源权限列表
     */
    private Set<MenuVO> menus;



}
