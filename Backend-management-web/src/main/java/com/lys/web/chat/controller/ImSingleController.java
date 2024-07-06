package com.lys.web.chat.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lys.annotation.Auth.Auth;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.chat.entity.Imsingle;
import com.lys.web.chat.entity.User;
import com.lys.web.chat.service.ImsingleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/imsingle")
public class ImSingleController {

    @Autowired
    private ImsingleService imsingleService;


    /**
     * 查询所有消息
     */
    @PostMapping
    @Auth
    public ResultVo findByFromUsername(@RequestBody User user){
        LambdaQueryWrapper<Imsingle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Imsingle::getFromuser,user.getFromUser())
                .eq(Imsingle::getTouser,user.getToUser());

        LambdaQueryWrapper<Imsingle> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Imsingle::getFromuser,user.getToUser())
                .eq(Imsingle::getTouser,user.getFromUser());
        LambdaUpdateWrapper<Imsingle> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(Imsingle::getReaded,"1")
                .eq(Imsingle::getFromuser,user.getToUser())
                .eq(Imsingle::getTouser,user.getFromUser());
        imsingleService.update(updateWrapper);
        List<Imsingle> all=imsingleService.list(queryWrapper);
        List<Imsingle> list = imsingleService.list(queryWrapper1);
        all.addAll(list);

        all = all.stream()
                .sorted(Comparator.comparing(Imsingle::getTime))
                .collect(Collectors.toList());


        return ResultUtils.success("查询成功",all);
    }

    /**
     * 查询未读消息
     */
//    @PostMapping("/read")
//    public ResultVo read(@RequestBody User user){
//        LambdaQueryWrapper<Imsingle> queryWrapper1 = new LambdaQueryWrapper<>();
//        queryWrapper1.eq(Imsingle::getFromuser,user.getToUser())
//                .eq(Imsingle::getTouser,user.getFromUser());
//
//
//        List<Imsingle> all=imsingleService.list(queryWrapper);
//        List<Imsingle> list = imsingleService.list(queryWrapper1);
//        all.addAll(list);
//
//        all = all.stream()
//                .sorted(Comparator.comparing(Imsingle::getTime))
//                .collect(Collectors.toList());
//
//
//        return ResultUtils.success("查询成功",all);
//    }

}
