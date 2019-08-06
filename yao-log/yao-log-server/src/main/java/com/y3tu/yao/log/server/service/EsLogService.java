package com.y3tu.yao.log.server.service;

import com.y3tu.yao.log.server.model.dto.SearchDto;
import com.y3tu.yao.log.server.model.entity.EsLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * elasticsearch 操作日志服务
 *
 * @author y3tu
 */
public interface EsLogService {

    /**
     * 添加日志
     *
     * @param esLog
     * @return
     */
    EsLog saveLog(EsLog esLog);

    /**
     * 通过id删除日志
     *
     * @param id
     */
    void deleteLog(String id);

    /**
     * 删除全部日志
     */
    void deleteAll();

    /**
     * 分页搜索获取日志
     *
     * @param actionType 操作类型
     * @param key
     * @param searchDto
     * @param pageable
     * @return
     */
    Page<EsLog> findByCondition(Integer actionType, String key, SearchDto searchDto, Pageable pageable);
}
