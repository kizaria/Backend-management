package com.lys.web.goods_help.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lys.web.goods_help.entity.GoodsHelp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kiza
* @description 针对表【goods_help】的数据库操作Service
* @createDate 2024-04-06 17:14:48
*/
public interface GoodsHelpService extends IService<GoodsHelp> {
    //查询求购信息
    List<GoodsHelp> getHelp();

    //根据id查询求购信息
    GoodsHelp  getHelpById(Long id);

    //根据标题模糊搜索
    List<GoodsHelp> searchHelp( String title);
}
