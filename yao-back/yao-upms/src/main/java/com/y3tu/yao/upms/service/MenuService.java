package com.y3tu.yao.upms.service;


import com.y3tu.yao.feign.vo.MenuVO;

import java.util.List;

public interface MenuService {

    List<MenuVO> getCurrentMenuByUID(Integer uid);
}
