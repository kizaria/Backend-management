package com.lys.web.HomeGoods.entity;

import com.lys.web.goods.entity.Goods;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页产品要返回实体类
 */
@Data
public class HomeGoods {
    private Long categoryId;
    private String images;
    private String categoryName;
    private List<Goods> GoodsList=new ArrayList<>();
}
