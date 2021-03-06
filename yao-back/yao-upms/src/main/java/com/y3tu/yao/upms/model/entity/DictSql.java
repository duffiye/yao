package com.y3tu.yao.upms.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.y3tu.tool.web.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


/**
 * 字典SQL配置
 *
 * @author y3tu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_dict_sql")
public class DictSql extends BaseEntity<DictSql> {

    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**
     * SQL编码
     */
    @TableField(value = "sql_code")
    private String sqlCode;
    /**
     * 名称
     */
    @TableField("sql_name")
    private String sqlName;
    /**
     * SQL内容。查询返回字段必须是
     */
    @TableField("sql_text")
    private String sqlText;
    /**
     * 值字段
     */
    @TableField("value_col")
    private String valueCol;
    /**
     * 名称字段
     */
    @TableField("name_col")
    private String nameCol;
    /**
     * 行数
     */
    @TableField("max_rows")
    private Integer maxRows;
    /**
     * 描述
     */
    private String remarks;
    /**
     * 创建人
     */
    @TableField("create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField("create_date")
    private Date createDate;
    /**
     * 更新人
     */
    @TableField("update_by")
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField("update_date")
    private Date updateDate;
    /**
     * 删除标识。1：正常，0：禁用
     */
    private Integer status;
    /**
     *
     */
    @TableField("exec_db")
    private String execDb;

}
