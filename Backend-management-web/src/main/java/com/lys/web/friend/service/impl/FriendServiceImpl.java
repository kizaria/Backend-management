package com.lys.web.friend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lys.web.chat.entity.Imsingle;
import com.lys.web.friend.entity.Friend;
import com.lys.web.friend.mapper.FriendMapper;
import com.lys.web.friend.service.FriendService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author kiza
* @description 针对表【friend】的数据库操作Service实现
* @createDate 2024-04-14 20:12:43
*/
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend>
    implements FriendService {

    @Override
    public List<Imsingle> getRead(String touser) {
        return this.baseMapper.getRead(touser);
    }
}




