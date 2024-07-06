package com.lys.web.goods.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class GoodsExcel implements Serializable {
    @Alias("商品id")
    private Long goodsId;
    @Alias("商品名称")
    private String goodsName;
    @Alias("商品类型")
    private String type;
    @Alias("商品类别")
    private String categoryName;
    @Alias("商品介绍")
    private String goodsDesc;
    @Alias("商品价格")
    private BigDecimal goodsPrice;
    @Alias("发布人")
    private String userName;
    @Alias("电话")
    private String phone;
    @Alias("微信")
    private String wxNum;
    @Alias("商品地址")
    private String address;
    @Alias("状态")
    private String status;
    @Alias("出售状态")
    private String sellStatus;
    @Alias("推荐首页")
    private String setIndex;
    @Alias("是否删除")
    private String deleteStatus;
    @Alias("审核")
    private String auditing;
    @Alias("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;


}
