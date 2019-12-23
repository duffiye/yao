package com.y3tu.yao.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.web.annotation.MethodMapping;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.tool.web.base.pojo.PageInfo;
import com.y3tu.yao.feign.constant.ServerNameConstants;
import com.y3tu.yao.upms.model.entity.Dict;
import com.y3tu.yao.upms.model.entity.DictData;
import com.y3tu.yao.upms.service.DictDataService;
import com.y3tu.yao.upms.service.DictService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 字典数据
 *
 * @author y3tu
 */
@RestController
@RequestMapping("/dictData")
public class DictDataController extends BaseController<DictDataService, DictData> {
    @Autowired
    private DictService dictService;

    @Autowired
    private DictDataService dictDataService;

    @PostMapping("/page")
    @Override
    public R page(@RequestBody PageInfo pageInfo) {
        return R.success(dictDataService.page(pageInfo));
    }

    @MethodMapping("/getByCode/{code}")
    public R getByType(@PathVariable String code) {
        Dict dict = dictService.getOne(new QueryWrapper<Dict>().eq("code", code));
        if (dict == null) {
            return R.error("字典编码不存在");
        }
        return R.success(dictDataService.list(new QueryWrapper<DictData>().eq("dict_id", dict.getId())));
    }

    @MethodMapping(method = RequestMethod.POST)
    @Override
    public R save(@RequestBody DictData dictData) {
        Dict dict = dictService.getById(dictData.getDictId());
        if (dict == null) {
            return R.error("字典类型id不存在");
        }
        dictDataService.save(dictData);
        return R.success();
    }


}
