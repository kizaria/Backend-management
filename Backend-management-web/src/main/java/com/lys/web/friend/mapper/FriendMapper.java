package com.lys.web.friend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lys.web.chat.entity.Imsingle;
import com.lys.web.friend.entity.Friend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kiza
* @description 针对表【friend】的数据库操作Mapper
* @createDate 2024-04-14 20:12:43
* @Entity generator.domain.Friend
*/
@Mapper
public interface FriendMapper extends BaseMapper<Friend> {

    //查询未读
    List<Imsingle> getRead(@Param("touser") String touser);

}




