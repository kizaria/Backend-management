package com.lys.web.goods_help.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods_help.entity.GoodsHelp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kiza
* @description 针对表【goods_help】的数据库操作Mapper
* @createDate 2024-04-06 17:14:48
* @Entity generator.domain.GoodsHelp
*/

@Mapper
public interface GoodsHelpMapper extends BaseMapper<GoodsHelp> {
    //查询求购信息
    List<GoodsHelp> getHelp();

    //根据id查询求购信息
    GoodsHelp  getHelpById(@Param("id") Long id);

    //根据标题模糊搜索
    List<GoodsHelp> searchHelp(@Param("title") String title);
}




