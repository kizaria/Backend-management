package com.lys.web.goods_report.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lys.annotation.Auth.Auth;
import com.lys.annotation.Log.HoneyLogs;
import com.lys.annotation.Log.LogType;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods_collect.entity.CollectParm;
import com.lys.web.goods_report.entity.GoodsReport;
import com.lys.web.goods_report.entity.ReportParm;
import com.lys.web.goods_report.entity.ReportVo;
import com.lys.web.goods_report.service.GoodsReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/api/report")
public class GoodsReportController {

    @Autowired
    private GoodsReportService goodsReportService;

    /**
     *前台用户举报商品，需要登录，无需log
     * @param goodsReport
     * @return
     */
    //举报商品
    @PostMapping("/report")
    @Auth
    public ResultVo report(@RequestBody GoodsReport goodsReport){
        //判断是否举报过
        LambdaQueryWrapper<GoodsReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsReport::getGoodsId,goodsReport.getGoodsId())
                .eq(GoodsReport::getReportUser,goodsReport.getReportUser());
        GoodsReport one = goodsReportService.getOne(queryWrapper);
        if(one!=null){
            return ResultUtils.error("您已经举报过，请勿重复举报");
        }
        goodsReport.setReportTime(new Date());
        if(goodsReportService.save(goodsReport)){
            return ResultUtils.success("举报成功");
        }
        return ResultUtils.error("举报失败");
    }


    /**
     * 小程序api，现已废弃
     * @param goodsReport
     * @return
     */
    //查询是否举报过
    @GetMapping("/hasReport")
    @Auth
    public ResultVo hasReport(GoodsReport goodsReport){
        //判断是否举报过
        LambdaQueryWrapper<GoodsReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsReport::getGoodsId,goodsReport.getGoodsId())
                .eq(GoodsReport::getReportUser,goodsReport.getReportUser());
        GoodsReport one = goodsReportService.getOne(queryWrapper);
        if(one!=null){
            return ResultUtils.success("查询成功",1);
        }else {
            return ResultUtils.success("查询成功",0);
        }
    }

    /**
     * 现为前台我的举报，需要登录，无需log
     * @param parm
     * @return
     */
    //小程序我的举报列表
    @GetMapping("/getMyReport")
    @Auth
    public ResultVo getMyReport(ReportParm parm){
        Page<Goods> list =goodsReportService.getMyReport(parm);
        return ResultUtils.success("查询成功",list);
    }


    /**
     * 后台所有举报列表，需要登录，无需log
     * @param parm
     * @return
     */
    //举报列表
    @GetMapping("/getList")
    @Auth
    public ResultVo getList(ReportParm parm){
        Page<ReportVo> list=goodsReportService.getList(parm);
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 后台处理举报，需要登录，需要log
     * @param goodsReport
     * @return
     */
    //处理举报
    @PostMapping("/doReport")
    @Auth
    @HoneyLogs(operation = "商品投诉",type = LogType.UPDATE)
    public ResultVo doReport(@RequestBody GoodsReport goodsReport){
        LambdaUpdateWrapper<GoodsReport> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(GoodsReport::getStatus,"1")
                .eq(GoodsReport::getReportId,goodsReport.getReportId());
        if(goodsReportService.update(updateWrapper)){
            return ResultUtils.success("处理成功");
        }
        return ResultUtils.error("处理失败");
    }

    /**
     * 后台驳回举报，需要登录，需要log
     * @param goodsReport
     * @return
     */
    //驳回举报
    @PostMapping("/doNotReport")
    @Auth
    @HoneyLogs(operation = "商品投诉",type = LogType.UPDATE)
    public ResultVo doNotReport(@RequestBody GoodsReport goodsReport){
        LambdaUpdateWrapper<GoodsReport> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(GoodsReport::getStatus,"2")
                .eq(GoodsReport::getReportId,goodsReport.getReportId());
        if(goodsReportService.update(updateWrapper)){
            return ResultUtils.success("处理成功");
        }
        return ResultUtils.error("处理失败");
    }

    /**
     * 后台删除举报，需要登录，需要log
     * @param reportId
     * @return
     */
    //删除投诉
    @DeleteMapping("/{reportId}")
    @Auth
    @HoneyLogs(operation = "商品投诉",type = LogType.DELETE)
    public ResultVo delete(@PathVariable Long reportId) {
        if (goodsReportService.removeById(reportId)) {
            return ResultUtils.success("删除成功");
        } else {
            return ResultUtils.error("删除失败");
        }
    }

    /**
     * 前台用户取消举报，需要登录，无需log
     * @param parm
     * @return
     */
    //小程序取消举报
    @PostMapping("/cancelReport")
    @Auth
    public ResultVo cancelReport(@RequestBody ReportParm parm){
        LambdaQueryWrapper<GoodsReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsReport::getReportUser,parm.getUserId())
                .eq(GoodsReport::getGoodsId,parm.getGoodsId());
        if(goodsReportService.remove(queryWrapper)){
            return ResultUtils.success("取消成功");
        } else {
            return ResultUtils.error("取消失败");
        }
    }

    /**
     * 前台用户修改举报内容，需要登录，无需log
     * @param goodsReport
     * @return
     */
    //修改举报原因
    @PostMapping("/editReport")
    @Auth
    public ResultVo editReport(@RequestBody GoodsReport goodsReport){
        LambdaUpdateWrapper<GoodsReport> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(GoodsReport::getReportUser,goodsReport.getReportUser())
                .eq(GoodsReport::getGoodsId,goodsReport.getGoodsId())
                .set(GoodsReport::getReason,goodsReport.getReason());
        if(goodsReportService.update(updateWrapper)){
            return ResultUtils.success("修改成功");
        } else {
            return ResultUtils.error("修改失败");
        }
    }

}
