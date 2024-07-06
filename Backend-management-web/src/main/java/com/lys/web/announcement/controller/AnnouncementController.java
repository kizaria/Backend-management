package com.lys.web.announcement.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lys.annotation.Auth.Auth;
import com.lys.annotation.Log.HoneyLogs;
import com.lys.annotation.Log.LogType;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.announcement.entity.Announcement;
import com.lys.web.announcement.entity.annParm;
import com.lys.web.announcement.service.AnnouncementService;
import com.lys.web.sys_user.entity.PageParm;
import com.lys.web.sys_user.entity.SysUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    /**
     * 后台新增公告，需要登录，需要log
     * @param announcement
     * @return
     */
    //用于后台新增公告
    @HoneyLogs(operation = "公告",type = LogType.ADD)
    @Auth
    @PostMapping("/save")
    public ResultVo add(@RequestBody Announcement announcement) {
        announcement.setTime(new Date());
        if (announcementService.save(announcement)) {
            return ResultUtils.success("新增成功");
        } else {
            return ResultUtils.error("新增失败");

        }
    }

    /**
     * 后台更新公告，需要登录，需要log
     * @param announcement
     * @return
     */
    //用于后台更新公告
    @HoneyLogs(operation = "公告",type = LogType.UPDATE)
    @Auth
    @PutMapping("/edit")
    public ResultVo edit(@RequestBody Announcement announcement) {
        if (announcementService.updateById(announcement)) {
            return ResultUtils.success("编辑成功");
        } else {
            return ResultUtils.error("编辑失败");
        }
    }


    /**
     * 后台删除公告，需要登录，需要log
     * @param id
     * @return
     */
    //用于后台删除公告
    @HoneyLogs(operation = "公告",type = LogType.DELETE)
    @Auth
    @DeleteMapping("/{id}")
    public ResultVo delete(@PathVariable Long id) {
        if (announcementService.removeById(id)) {
            return ResultUtils.success("删除成功");
        } else {
            return ResultUtils.error("删除失败");
        }
    }

    /**
     * 后台查询公告列表。需要登录，无需log
     * @param ann
     * @return
     */
    //后台查询公告列表，getopen可以通过
    @GetMapping("/getlist")
    @Auth
    public ResultVo getList(annParm ann) {
        //构造分页对象
        Page<Announcement> page = new Page<>(ann.getCurrentPage(), ann.getPageSize());
        //构造查询条件
        LambdaQueryWrapper<Announcement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(ann.getTitle()), Announcement::getTitle, ann.getTitle());
        Page<Announcement> list = announcementService.page(page, queryWrapper);
        return ResultUtils.success("查询成功", list);

    }

    /**
     * 前台用户公告列表，无需登录，无需log
     * @return
     */
    //前台用户公告列表，只展示getopen通过的
    @GetMapping("/getAnnouncementList")
    public ResultVo getAnnouncementList() {
        //构造查询条件
        LambdaQueryWrapper<Announcement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Announcement::getOpen,"0");
        List<Announcement> list = announcementService.list(queryWrapper);
        return ResultUtils.success("查询成功", list);

    }



}
