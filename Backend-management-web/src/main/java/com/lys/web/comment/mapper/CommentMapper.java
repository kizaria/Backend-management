package com.lys.web.comment.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lys.web.comment.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
* @author kiza
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2024-04-08 15:12:31
* @Entity generator.domain.Comment
*/

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}




