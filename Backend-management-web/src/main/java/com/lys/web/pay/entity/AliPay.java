package com.lys.web.pay.entity;

import lombok.Data;

@Data
public class AliPay {
    //标题
    private String subject;
    //单号
    private String traceNo;
    //价格
    private String totalAmount;
}



