package com.lys.web.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods.entity.GoodsExcel;
import com.lys.web.goods.mapper.GoodsMapper;
import com.lys.web.goods.service.GoodsService;
import com.lys.web.home.entity.ChartVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author kiza
* @description 针对表【goods】的数据库操作Service实现
* @createDate 2024-01-13 20:55:16
*/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods>
    implements GoodsService {

    @Override
    public List<ChartVo> getList() {
        return this.baseMapper.getList();
    }

    @Override
    public List<GoodsExcel> exportGoods() {
        return this.baseMapper.exportGoods();
    }
}




