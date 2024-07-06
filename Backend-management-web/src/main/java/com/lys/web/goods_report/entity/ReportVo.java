package com.lys.web.goods_report.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 后台返回实体类
 */
@Data
public class ReportVo {
    private Long reportId;
    private String goodsName;
    private String image;
    private String reason;
    private String nickName;
    private String status;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reportTime;
}
