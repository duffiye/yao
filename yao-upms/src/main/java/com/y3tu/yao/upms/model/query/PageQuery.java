package com.y3tu.yao.upms.model.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * ClassName: PageRequest
 * Description:
 * date: 2019/11/22 18:02
 *
 * @author zht
 */
@Data
public class PageQuery {

    @JsonProperty(value = "page_num",defaultValue = "1")
    private Integer pageNum;

    @JsonProperty(value = "page_size",defaultValue = "10")
    private Integer pageSize;
}
