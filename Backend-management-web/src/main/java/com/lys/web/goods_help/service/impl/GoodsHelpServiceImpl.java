package com.lys.web.goods_help.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lys.web.goods_help.entity.GoodsHelp;
import com.lys.web.goods_help.mapper.GoodsHelpMapper;
import com.lys.web.goods_help.service.GoodsHelpService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author kiza
* @description 针对表【goods_help】的数据库操作Service实现
* @createDate 2024-04-06 17:14:48
*/
@Service
public class GoodsHelpServiceImpl extends ServiceImpl<GoodsHelpMapper, GoodsHelp>
    implements GoodsHelpService {

    @Override
    public List<GoodsHelp> getHelp() {
        return this.baseMapper.getHelp();
    }

    @Override
    public GoodsHelp getHelpById(Long id) {
        return this.baseMapper.getHelpById(id);
    }

    @Override
    public List<GoodsHelp> searchHelp(String title) {
        return this.baseMapper.searchHelp(title);
    }
}




