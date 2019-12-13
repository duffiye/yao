package com.y3tu.yao.upms.mapper;

import com.y3tu.yao.upms.model.entity.RoleActionEntry;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * ClassName: ActionEntryMapper
 * Description:
 * date: 2019/11/21 15:09
 *
 * @author zht
 */
public interface RoleActionEntryMapper {

    /**
     * 功能描述 :根据菜单ID查询按钮列表
     *
     * @param roleId
     * @return java.util.List<com.y3tu.yao.common.vo.sys.ActionEntryVO>
     * @author zht
     * @date 2019/11/21
     */
    List<RoleActionEntry> selectByRoleId(Integer roleId);

    /**
     * 功能描述 :根据actionId 查询列表
     *
     * @param actionId
     * @return java.util.List<com.xft.upms.model.entity.RoleActionEntry>
     * @author zht
     * @date 2019/11/25
     */
    List<RoleActionEntry> selectByActionId(Integer actionId);


    /**
     * 功能描述 :批量插入
     *
     * @param list
     * @return void
     * @author zht
     * @date 2019/11/24
     */
    void batchInsert(@Param("list") List<RoleActionEntry> list);

    /**
     * 功能描述 : 根据roleId 删除关联记录
     *
     * @param roleId
     * @return void
     * @author zht
     * @date 2019/11/24
     */
    void deleteByRoleId(Integer roleId);

}
