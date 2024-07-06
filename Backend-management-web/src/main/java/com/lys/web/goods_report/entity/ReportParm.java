package com.lys.web.goods_report.entity;


import lombok.Data;

/**
 * 后台接收所需实体类
 */
@Data
public class ReportParm {
    private Long userId;
    private Long currentPage;
    private Long pageSize;
    private String goodsName;
    private String goodsId;
}
