package com.y3tu.cloud.log.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.y3tu.cloud.log.model.entity.Log;
import com.y3tu.cloud.log.model.query.LogQuery;
import com.y3tu.tool.web.base.mapper.BaseMapper;


public interface LogMapper extends BaseMapper<Log> {

    /**
     * 日志信息分页查询
     *
     * @param query
     */
    IPage<Log> pageByQuery(LogQuery query);
}
