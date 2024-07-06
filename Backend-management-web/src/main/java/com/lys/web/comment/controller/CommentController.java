package com.lys.web.comment.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lys.annotation.Auth.Auth;
import com.lys.utils.ResultUtils;
import com.lys.utils.ResultVo;
import com.lys.web.comment.entity.Comment;
import com.lys.web.comment.service.CommentService;
import com.lys.web.goods.entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论表，仅适用前端  评论因为审核压力过大，不进行审核
 */
@CrossOrigin
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private  CommentService commentService;


    /**
     * 求购信息评论,需要登录，无需log
     * @param comment
     * @return
     */
    //将评论保存
    @PostMapping("/save")
    @Auth
    public ResultVo save(@RequestBody Comment comment){
        comment.setCreatime(new Date());
        if(commentService.save(comment)){
            return ResultUtils.success("发布成功！");
        }else{
            return ResultUtils.error("发布失败！");
        }

    }

    /**
     * 求购信息评论，无需登录，无需log
     * @param id
     * @return
     */
    //获取所有评论
    @GetMapping("/getComment")
    public ResultVo getComment(Long id){
        LambdaUpdateWrapper<Comment> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(Comment::getForeignId,id);
        List<Comment> list = commentService.list(queryWrapper);
        List<Comment> root = list.stream().filter(item -> item.getPid() == null).collect(Collectors.toList());
        for (Comment comment : root) {
            comment.setChildren(list.stream().filter(item -> comment.getId() .equals(item.getPid())).collect(Collectors.toList()));
        }
        return ResultUtils.success("查询成功",root);
    }
}
