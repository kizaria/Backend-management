package com.lys.web.friend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lys.annotation.Auth.Auth;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.chat.entity.Imsingle;
import com.lys.web.chat.service.ImsingleService;
import com.lys.web.friend.entity.Friend;
import com.lys.web.friend.service.FriendService;
import com.lys.web.wx_user.entity.WxUser;
import com.lys.web.wx_user.service.WxUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前端进行聊天时，查询好友
 */
@CrossOrigin
@RestController
@RequestMapping("/api/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private ImsingleService imsingleService;

    @Autowired
    private WxUserService wxUserService;


    /**
     * 获取用户的好友，需要登录，无需log
     * @param userId
     * @return
     */
    //获取用户的好友
    @GetMapping("/get")
    @Auth
    public ResultVo get(Long userId){
        LambdaQueryWrapper<Friend> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Friend::getUserid,userId);
        List<Friend> list = friendService.list(queryWrapper);

        WxUser user = wxUserService.getById(userId);


        LambdaQueryWrapper<Imsingle> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Imsingle::getTouser,user.getUsername());
        List<Imsingle> list1 = imsingleService.list(queryWrapper1);

        List<Imsingle> read = friendService.getRead(user.getUsername());

        return ResultUtils.success("查询成功",list);
    }

    /**
     * 添加好友，需要登录，无需log
     * @param friend
     * @return
     */
    //当用户点击商品页面时，添加好友
    @PostMapping("/save")
    @Auth
    public ResultVo save(@RequestBody Friend friend){

        LambdaQueryWrapper<Friend> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Friend::getUserid,friend.getUserid())
                .eq(Friend::getFriendid,friend.getFriendid());
        Friend one = friendService.getOne(queryWrapper);
        if(one!=null){
            LambdaUpdateWrapper<Friend> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(Friend::getUseravatar,friend.getUseravatar())
                            .set(Friend::getFriendavatar,friend.getFriendavatar())
                                    .eq(Friend::getUsername,friend.getUsername())
                                            .eq(Friend::getFriendname,friend.getFriendname());
            friendService.update(updateWrapper);
        }else {
            if(friendService.save(friend)){
                return ResultUtils.success("设置成功");
            }else{
                return ResultUtils.error("设置失败");
            }
        }
        return ResultUtils.success("设置成功");
    }

    /**
     * 查询未读消息，需要登录，无需log
     * @param userId
     * @return
     */
    //查询未读消息,返回Imsingle类型数据，好让前端查询具体人
    @GetMapping("/read")
    @Auth
    public ResultVo read(Long userId){
        LambdaQueryWrapper<Friend> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Friend::getUserid,userId);
        List<Friend> list = friendService.list(queryWrapper);

        WxUser user = wxUserService.getById(userId);


        LambdaQueryWrapper<Imsingle> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Imsingle::getTouser,user.getUsername());
        List<Imsingle> list1 = imsingleService.list(queryWrapper1);

        List<Imsingle> read = friendService.getRead(user.getUsername());

        return ResultUtils.success("查询成功",read);
    }
}
