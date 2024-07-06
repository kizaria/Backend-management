package com.lys.web.friend.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lys.web.chat.entity.Imsingle;
import com.lys.web.friend.entity.Friend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kiza
* @description 针对表【friend】的数据库操作Service
* @createDate 2024-04-14 20:12:43
*/
public interface FriendService extends IService<Friend> {
    //查询未读
    List<Imsingle> getRead(String touser);
}
