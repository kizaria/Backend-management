package com.lys.web.goods_collect.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods_collect.entity.CollectParm;
import com.lys.web.goods_collect.entity.GoodsCollect;
import com.lys.web.goods_collect.mapper.GoodsCollectMapper;
import com.lys.web.goods_collect.service.GoodsCollectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author kiza
* @description 针对表【goods_collect】的数据库操作Service实现
* @createDate 2024-01-23 23:26:55
*/
@Service
public class GoodsCollectServiceImpl extends ServiceImpl<GoodsCollectMapper, GoodsCollect>
    implements GoodsCollectService {

    @Override
    @Transactional
    public Page<Goods> getMyCollect(CollectParm parm) {
        Page<Goods> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        return this.baseMapper.getMyCollect(page,parm.getUserId());
    }

    @Override
    public List<Goods> getSumCollect() {
        return this.baseMapper.getSumCollect();
    }


    @Override
    public Integer getCount(Long goodsId) {
        return this.baseMapper.getCount(goodsId);
    }
}




