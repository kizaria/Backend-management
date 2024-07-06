package com.lys.web.goods_report.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods_order.entity.OrderVo;
import com.lys.web.goods_report.entity.GoodsReport;
import com.lys.web.goods_report.entity.ReportVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author kiza
* @description 针对表【goods_report】的数据库操作Mapper
* @createDate 2024-01-24 16:16:36
* @Entity generator.domain.GoodsReport
*/

@Mapper
public interface GoodsReportMapper extends BaseMapper<GoodsReport> {
    //查询后台举报
    Page<ReportVo> getList(Page<ReportVo> page, @Param("goodsName") String goodsName);

    //前台我的举报
    Page<Goods> getMyReport(Page<Goods> page, @Param("userId") Long userId);
}




