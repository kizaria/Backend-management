package com.lys.web.goods_order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 后台返回的所需的实体类
 */
@Data
public class OrderVo {
    private Long UserId;
    private String orderId;
    private String goodsName;
    private String image;
    private String gName;
    private String nickName;
    private String goodsPrice;
    private String price;
    private String orderStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
}
