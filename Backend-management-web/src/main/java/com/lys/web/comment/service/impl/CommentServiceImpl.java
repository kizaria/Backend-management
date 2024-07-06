package com.lys.web.comment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lys.web.comment.entity.Comment;
import com.lys.web.comment.mapper.CommentMapper;
import com.lys.web.comment.service.CommentService;
import org.springframework.stereotype.Service;

/**
* @author kiza
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2024-04-08 15:12:31
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService {

}




