package com.lys.web.notice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lys.annotation.Auth.Auth;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.notice.entity.Notice;
import com.lys.web.notice.service.NoticeService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;


    /**
     * 前台首页  商品审核通知，需要登录，无需log
     * @param userId
     * @return
     */
    @GetMapping("/getNotice")
    public ResultVo getNotice(Long userId){
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notice::getUserId,userId);
        List<Notice> list = noticeService.list(queryWrapper);

        LambdaUpdateWrapper<Notice> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Notice::getReaded,"1")
                .eq(Notice::getUserId,userId);
        noticeService.update(updateWrapper);
        if(list!=null) {
            return ResultUtils.success("查询成功", list);
        }else {
            return ResultUtils.error("查询失败");
        }
    }

    /**
     * 前台获取是否有未读通知，需要登录，无需log
     * @param userId
     * @return
     */
    @GetMapping("/getRead")
    public ResultVo getRead(Long userId){
        LambdaQueryWrapper<Notice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notice::getUserId,userId)
                .eq(Notice::getReaded,"0");
        List<Notice> list = noticeService.list(queryWrapper);

        if(!list.isEmpty()) {
            return ResultUtils.success("查询成功", 1);
        }else {
            return ResultUtils.success("查询失败",0);
        }
    }
}
