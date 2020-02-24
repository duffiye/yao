package com.y3tu.yao.file.mapper;

import com.y3tu.yao.file.entity.UpdateUrl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * ClassName: UrlMapper
 * Description:
 * date: 2020/2/18 11:29
 *
 * @author zht
 */
@Mapper
public interface UrlMapper {

    List<UpdateUrl> selectUrl(@Param("columnName") String columnName,
                              @Param("tableName") String tableName,
                              @Param("limit") Integer limit,
                              @Param("offset") Integer offset);

    int updateUrl(@Param("list") List<UpdateUrl> list,
                  @Param("columnName") String columnName,
                  @Param("tableName") String tableName);

    int getMaxId(@Param("tableName") String tableName);

    int getMinId(@Param("tableName") String tableName);

}
