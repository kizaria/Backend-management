package com.lys.web.goods_report.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods_report.entity.GoodsReport;
import com.lys.web.goods_report.entity.ReportParm;
import com.lys.web.goods_report.entity.ReportVo;
import org.apache.ibatis.annotations.Param;

/**
* @author kiza
* @description 针对表【goods_report】的数据库操作Service
* @createDate 2024-01-24 16:16:36
*/
public interface GoodsReportService extends IService<GoodsReport> {
    //查询举报
    Page<ReportVo> getList(ReportParm parm);
    //小程序我的举报
    Page<Goods> getMyReport(ReportParm parm);
}
