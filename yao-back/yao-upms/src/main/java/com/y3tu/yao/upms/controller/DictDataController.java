package com.y3tu.yao.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.yao.common.constants.ServerNameConstants;
import com.y3tu.yao.log.starter.annotation.Log;
import com.y3tu.yao.log.starter.constant.ActionTypeEnum;
import com.y3tu.yao.upms.model.entity.Dict;
import com.y3tu.yao.upms.model.entity.DictData;
import com.y3tu.yao.upms.service.DictDataService;
import com.y3tu.yao.upms.service.DictService;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.web.annotation.MethodMapping;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.tool.web.base.pojo.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 字典数据
 *
 * @author y3tu
 */
@RestController
@RequestMapping("/dictData")
@Log(serverName = ServerNameConstants.UPMS_SERVER, moduleName = "字典管理")
public class DictDataController extends BaseController<DictDataService, DictData> {
    @Autowired
    private DictService dictService;

    @Autowired
    private DictDataService dictDataService;

    @PostMapping("/page")
    @Log(actionName = "查看字典明细分页数据")
    @Override
    public R page(@RequestBody PageInfo pageInfo) {
        return R.success(dictDataService.page(pageInfo));
    }

    @MethodMapping("/getByCode/{code}")
    @Log(actionName = "根据字典编码查看字典明细数据")
    public R getByType(@PathVariable String code) {
        Dict dict = dictService.getOne(new QueryWrapper<Dict>().eq("code", code));
        if (dict == null) {
            return R.error("字典编码不存在");
        }
        return R.success(dictDataService.list(new QueryWrapper<DictData>().eq("dict_id", dict.getId())));
    }

    @MethodMapping(method = RequestMethod.POST)
    @Log(actionName = "新增字典明细数据", actionType = ActionTypeEnum.ADD)
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
