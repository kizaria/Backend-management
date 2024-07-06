package com.lys.web.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods.entity.GoodsExcel;
import com.lys.web.goods_report.entity.ReportVo;
import com.lys.web.home.entity.ChartVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kiza
* @description 针对表【goods】的数据库操作Mapper
* @createDate 2024-01-13 20:55:16
* @Entity generator.domain.Goods
*/
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    //首页饼图
    List<ChartVo> getList();

    //导出数据
    List<GoodsExcel> exportGoods();
}




