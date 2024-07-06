package com.lys.web.goods_help.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lys.annotation.Auth.Auth;
import com.lys.annotation.Log.HoneyLogs;
import com.lys.annotation.Log.LogType;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.announcement.entity.Announcement;
import com.lys.web.announcement.entity.annParm;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods_category.entity.GoodsCategory;
import com.lys.web.goods_help.entity.GoodsHelp;
import com.lys.web.goods_help.entity.HelpParm;
import com.lys.web.goods_help.entity.HelpParmAdmin;
import com.lys.web.goods_help.service.GoodsHelpService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/help")
public class GoodsHelpController {
    @Autowired
    private GoodsHelpService goodsHelpService;

    /**
     * 前台发布求购信息，需要登录，无需log
     * @param goodsHelp
     * @return
     */
    //发布求购信息
    @PostMapping("/release")
    @Auth
    public ResultVo release(@RequestBody GoodsHelp goodsHelp){
        goodsHelp.setTime(new Date());
        if(goodsHelpService.save(goodsHelp)){
            return ResultUtils.success("发布成功,等待管理员审核");
        }else {
            return ResultUtils.error("发布失败");
        }
    }

    /**
     * 前台求购页面获取，无需登录，无需log
     * @return
     */
    //获取求购信息
    @GetMapping("/getHelp")
    public ResultVo getHelp(){
        List<GoodsHelp> list = goodsHelpService.getHelp();
        if(list!=null){
            return ResultUtils.success("查询成功",list);
        }else {
            return ResultUtils.error("查询失败");
        }
    }

    /**
     * 根据id获取详细求购信息,无需登录，无需log
     * @param id
     * @return
     */
    //根据id获取求购信息
    @GetMapping("/getHelpById")
    public ResultVo getHelpById(Long id){
        GoodsHelp helpById = goodsHelpService.getHelpById(id);
        if(helpById!=null){
            return ResultUtils.success("查询成功",helpById);
        }else {
            return ResultUtils.error("查询失败");
        }
    }


    /**
     * 前台我的求购信息，需要登录，无需log
     * @param helpParm
     * @return
     */
    //我的求购信息
    @GetMapping("/getMyHelp")
    @Auth
    public ResultVo getMyHelp(HelpParm helpParm){
        Page<GoodsHelp> page = new Page<>(helpParm.getCurrentPage(), helpParm.getPageSize());
        //构造查询条件
        LambdaQueryWrapper<GoodsHelp> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper
                .eq(GoodsHelp::getUserId,helpParm.getUserId());
        Page<GoodsHelp> list = goodsHelpService.page(page, queryWrapper);
        return ResultUtils.success("查询成功",list);
    }

    /**
     * 前台 我的求购信息里对信息删除，需要登录，无需log
     * @param id
     * @return
     */
    //删除我的求购信息
    @Auth
    @GetMapping("/deleteMyHelp")
    public ResultVo deleteMyHelp(Long id){

        if(goodsHelpService.removeById(id)){
            return ResultUtils.success("删除成功");
        }else {
            return ResultUtils.error("删除失败");
        }
    }

    /**
     * 后台edit编辑 求购信息，需要登录，需要log
     * @param goodsHelp
     * @return
     */
    //更新
    @PostMapping("/edit")
    @Auth
    public ResultVo edit(@RequestBody GoodsHelp goodsHelp){
        if(goodsHelpService.updateById(goodsHelp)){
            return ResultUtils.success("编辑成功");
        }else{
            return ResultUtils.error("编辑失败");
        }
    }

    /**
     * 前台edit编辑 求购信息，需要登录，无需log
     * @param goodsHelp
     * @return
     */
    //更新
    @PostMapping("/Myedit")
    @Auth
    public ResultVo Myedit(@RequestBody GoodsHelp goodsHelp){
        if(goodsHelpService.updateById(goodsHelp)){
            return ResultUtils.success("编辑成功");
        }else{
            return ResultUtils.error("编辑失败");
        }
    }

    /**
     * 前台搜索框，无需登录，无需log
     * @param title
     * @return
     */
    //根据标题模糊搜索
    @GetMapping("/searchHelp")
    public ResultVo searchHelp(String title){
        List<GoodsHelp> list = goodsHelpService.searchHelp(title);
        if(list!=null){
            return ResultUtils.success("查询成功",list);
        }else{
            return ResultUtils.error("查询失败");
        }
    }

    /**
     * 后台求购信息列表，需要登录，无需log
     * @param helpParmAdmin
     * @return
     */
    //列表
    @GetMapping("/getlist")
    @Auth
    public ResultVo getList(HelpParmAdmin helpParmAdmin) {
        //构造分页对象
        Page<GoodsHelp> page = new Page<>(helpParmAdmin.getCurrentPage(), helpParmAdmin.getPageSize());
        //构造查询条件
        LambdaQueryWrapper<GoodsHelp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(helpParmAdmin.getTitle()), GoodsHelp::getTitle, helpParmAdmin.getTitle());
        Page<GoodsHelp> list = goodsHelpService.page(page, queryWrapper);
        return ResultUtils.success("查询成功", list);
    }

}
