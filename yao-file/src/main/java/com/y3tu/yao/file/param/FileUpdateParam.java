package com.y3tu.yao.file.param;

import lombok.Data;

/**
 * ClassName: FileUpdateParam
 * Description:
 * date: 2020/2/18 15:40
 *
 * @author zht
 */
@Data
public class FileUpdateParam {
    String selectColumnName;
    String updateColumnName;
    String tableName;
}
