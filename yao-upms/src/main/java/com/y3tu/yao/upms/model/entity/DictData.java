package com.y3tu.yao.upms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 字典数据
 *
 * @author y3tu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_dict_data")
public class DictData extends BaseEntity<DictData> {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;
    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 删除标识
     */
    @TableField("del_flag")
    private Integer delFlag;
    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField("update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 描述
     */
    private String description;
    /**
     * 字典id
     */
    @TableField("dict_id")
    private String dictId;
    /**
     * 排序
     */
    private BigDecimal sort;
    /**
     * 状态 0：正常 1：禁用
     */
    private Integer status;
    /**
     * 名称
     */
    private String name;
    /**
     * 值
     */
    private String value;

}
