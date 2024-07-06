package com.lys.web.goods_collect.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods_collect.entity.CollectParm;
import com.lys.web.goods_collect.entity.GoodsCollect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kiza
* @description 针对表【goods_collect】的数据库操作Service
* @createDate 2024-01-23 23:26:55
*/
public interface GoodsCollectService extends IService<GoodsCollect> {
    //获取我的收藏
    Page<Goods> getMyCollect(CollectParm parm);

    //新版网页人气收藏
    List<Goods> getSumCollect();

    Integer getCount(Long goodsId);
}
