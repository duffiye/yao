package com.y3tu.yao.file.param;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class FileParam {
    @NotNull(message = "bucket不能为空")
    private String bucket;

    @NotNull(message = "fid不能为空")
    private String fid;

    @NotNull(message = "过期时间不能为空")
    @Min(value = 30, message = "过期时间不能小于30秒")
    private Integer expire = 60;
}
