package com.lys.web.goods_report.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods_order.entity.OrderVo;
import com.lys.web.goods_report.entity.GoodsReport;
import com.lys.web.goods_report.entity.ReportParm;
import com.lys.web.goods_report.entity.ReportVo;
import com.lys.web.goods_report.mapper.GoodsReportMapper;
import com.lys.web.goods_report.service.GoodsReportService;
import org.springframework.stereotype.Service;

/**
* @author kiza
* @description 针对表【goods_report】的数据库操作Service实现
* @createDate 2024-01-24 16:16:36
*/
@Service
public class GoodsReportServiceImpl extends ServiceImpl<GoodsReportMapper, GoodsReport>
    implements GoodsReportService {

    @Override
    public Page<ReportVo> getList(ReportParm parm) {
        Page<ReportVo> page=new Page<>(parm.getCurrentPage(),parm.getPageSize());
        return this.baseMapper.getList(page,parm.getGoodsName());
    }

    @Override
    public Page<Goods> getMyReport(ReportParm parm) {
        Page<Goods> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        return this.baseMapper.getMyReport(page,parm.getUserId());
    }
}




