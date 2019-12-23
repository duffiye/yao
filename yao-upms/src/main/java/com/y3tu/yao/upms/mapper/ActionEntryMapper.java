package com.y3tu.yao.upms.mapper;

import com.y3tu.yao.feign.vo.ActionEntryVO;
import com.y3tu.yao.upms.model.entity.ActionEntry;
import com.y3tu.yao.upms.model.query.action.ActionEntryAddQuery;
import com.y3tu.yao.upms.model.query.action.ActionEntryQuery;
import com.y3tu.yao.upms.model.query.action.ActionEntryUpdateQuery;
import com.y3tu.yao.upms.model.vo.ActionEntryQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClassName: ActionEntryMapper
 * Description:
 * date: 2019/11/21 15:09
 *
 * @author zht
 */
public interface ActionEntryMapper {

    /**
     * 功能描述 :根据菜单ID查询按钮列表
     *
     * @param ids
     * @return java.util.List<com.y3tu.yao.common.vo.sys.ActionEntryVO>
     * @author zht
     * @date 2019/11/21
     */
    List<ActionEntryVO> selectByMenuIds(@Param("ids") List<Integer> ids);


    /**
     * 功能描述 :查询所有按钮
     *
     * @param
     * @return java.util.List<com.y3tu.yao.common.vo.sys.ActionEntryVO>
     * @author zht
     * @date 2019/11/23
     */
    List<ActionEntryVO> selectAll();

    /**
     * 功能描述 :批量插入
     *
     * @param list
     * @return void
     * @author zht
     * @date 2019/11/24
     */
    void batchInsert(@Param("list") List<ActionEntry> list);

    /**
     * 功能描述 :根据菜单ID删除
     *
     * @param menuId
     * @return void
     * @author zht
     * @date 2019/11/24
     */
    void deleteByMenuId(Integer menuId);


    /**
     * 功能描述 :根据条件分页查询按钮/动作信息
     *
     * @param query
     * @return java.util.List<com.y3tu.yao.upms.model.vo.ActionEntryQueryVO>
     * @author zht
     * @date 2019/11/25
     */
    List<ActionEntryQueryVO> selectByCondition(ActionEntryQuery query);


    /**
     * 功能描述 :插入按钮表
     *
     * @param query
     * @return void
     * @author zht
     * @date 2019/11/25
     */
    void insert(ActionEntryAddQuery query);

    /**
     * 功能描述 :插入按钮表
     *
     * @param query
     * @return void
     * @author zht
     * @date 2019/11/25
     */
    void update(ActionEntryUpdateQuery query);


    /**
     * 功能描述 :删除
     *
     * @param id
     * @return void
     * @author zht
     * @date 2019/11/25
     */
    void deleteById(Integer id);
}
