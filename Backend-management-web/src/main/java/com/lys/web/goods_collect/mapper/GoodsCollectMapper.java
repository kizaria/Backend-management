package com.lys.web.goods_collect.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods_collect.entity.GoodsCollect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kiza
* @description 针对表【goods_collect】的数据库操作Mapper
* @createDate 2024-01-23 23:26:54
* @Entity generator.domain.GoodsCollect
*/

@Mapper
public interface GoodsCollectMapper extends BaseMapper<GoodsCollect> {
    //我的收藏
    Page<Goods> getMyCollect(Page<Goods> page, @Param("userId") Long userId);

    //新版网页按照收藏人数排序，首页
    List<Goods> getSumCollect();

    //新版网页收藏人数
    Integer getCount(@Param("goodsId") Long goodsId);
}




