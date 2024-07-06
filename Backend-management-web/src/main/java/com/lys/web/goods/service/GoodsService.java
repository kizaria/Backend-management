package com.lys.web.goods.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods.entity.GoodsExcel;
import com.lys.web.home.entity.ChartVo;

import java.util.List;

/**
* @author kiza
* @description 针对表【goods】的数据库操作Service
* @createDate 2024-01-13 20:55:16
*/
public interface GoodsService extends IService<Goods> {
    //首页饼图
    List<ChartVo> getList();

    //导出数据
    List<GoodsExcel> exportGoods();
}
