package com.lys.web.pay.controller;


import cn.hutool.json.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.easysdk.factory.Factory;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lys.web.goods.entity.Goods;
import com.lys.web.goods_order.entity.GoodsOrder;
import com.lys.web.goods_order.service.GoodsOrderService;
import com.lys.web.pay.entity.AliPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static org.apache.catalina.manager.Constants.CHARSET;


@CrossOrigin
@RestController
@RequestMapping("/api/alipay")

public class AliPayController {

    @Autowired
    private GoodsOrderService goodsOrderService;

    @GetMapping("/pay")
    public void pay(AliPay aliPay, HttpServletResponse httpResponse) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient(
                "https://openapi-sandbox.dl.alipaydev.com/gateway.do",
                "9021000136627123",
                "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCA2Dh2o0hLJeVaAzqtkLhpJYEXrkHq7DI+TN8Q+FukzQdDw60cBVh0lfhnU6dcCDQeI3omvAODt9XmnJlyrogeRRM0l8LAlR2/u4XdqKDBubFWNkoidur414e3BEGQZZe+TI/SSM+I2vk8FoSfDtuwS1nE1ffMCnpyh71tuQ7WI2fKTafRoqAc0siF6d8QbhWKaLvklP677OjZf+eG/U51J1spGAvCJxzGN1rJE6+pyQvZPJwvNLzVO2yYfELE/ACGKAKL1KGa7ApOagcRAm9Qtcml1fFs9qfEonD1SmVWss0IcsQJ5LZlHibTraZfgygqw7sO1JdZJCP/G4Ja1QkvAgMBAAECggEAbNe7YyVuleUOieq+cA8YaeK/2gRgNReRHiYMLqbPrDlzkKBFpa0yW1KxhIAbmlhnxqoLr5sWkPgEn4rc6g9cd828eqpVYCDX/7dnT6f7HYxDreOw3sp8lvhj8oyK4nOgK6c7u9W8VA0/jK4tHL0NgzE0RV7HtTuI0yWuNXmmeeg8Yu9jt4XvB4wY2cmFehNwcCqjQgF/W/qWQzPlVEWEus5BK5mgVv/uFA8dCsjUiHzWsR9UL9v7FXG1Vw8v1cCwu5yu+6AxwbDX65JVceS4+7EEwUXjvFDlj/IYjIZ+36pbaa/WCqa2kKMvkYbC32t0+E8MyfP+ijvnyQCAVv6ryQKBgQDJwNeiTSrhWsQC974k3y98L+OIkNIuvCs3m5X/UIK7atDieLxDYnZGh2xjVrMvT5dVvW1BZCxALbQl8jU0y/VXKB+8PHEw35159MuDWjsAireyKL+9q/IhxjsgR3MHiygUHoz6SU8RKvy2fzRZFWQPt+2LI8Dg0Au8ze6AcqUEGwKBgQCjfOagoX6QIul3xBEWSnzLYM3T//mJQiv4SU+qY4gKHxcp++glM+6u0iVUL84w6ila27db8ZB8BNn7eSF4khtL32pQrP3mSyAOpOppyAj+I7LNINf6AhfigLXR0OaDSmMacYToIBE1KYNVRrAmeFGPccJZgumdFXXRTgAr2paYfQKBgH4Koux7hzF4kgJEoh7I7BKKnHyh9R1LYzh/U+tIchQCPHNv+DWk2r1fN2CsNo91Ctd+2zXCDj8eDnCya3kLfr+vinv/yrs8yFX7j/UEtt20y+RavscRF9jvzdpxqLRqxGQlXe+zQtk34N9mgt6wlW2Ye6e0c3g/oUWX5cuLU/pHAoGAJfuMsWETwHT7/OFGfwFLQQz6hPiBhwwinwqZQaLiJkfW3+AwrQQFvgQAvDBnHSIp/Xvye6ABy25tHmmkBxka0mPTlW67hleJ2R0jgPQCctjkX5fz1ZfK6Mqyc06vX21W3Pvmvu9EUC+W3acQob9uY38wgg9SKzajKr+OdqHQPxkCgYALEkTUXnK1th89YdxsqeqrW8dNU7ZXdfZ1/qktcXO0pgFjTzpPZsRXInn8JblwfbEjmOREiZZ7jIw8kmtYXhc8PVwt/CduQ4UAt6osWg1GLjuNfX5ohY3xiK7KxPDwCDuA8IvOpWD/5LiMJ/Rgmte47ziVUUk2OBMstD48KbYTeQ==",
                "json",
                "UTF-8",
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtNxCz/WYx8vXKjNQPwFK/Is1RqUGgQ048GcVecQ6jS5YApn83vjVppAjWg85gK8cFG/7qQfqlZ25McVzrfJn9N2jqKeFxfbAYH6LH2eEYAwMJzvVAAGz9z8yLpkHsj4G/hqvcd/DBnA12JVKV/ptdHa94w3NV1b7ekavj3WnCE5k7G1MP/6//thAVIHiAo3/aC8lYXwoj016vTmxC9Xi/A4IkIAQZrI+oLhfmqhK5dd8TKKnsNxByx9987b04PajwZtEPq7wj30U2ThSI9NCDiUpU0xoJKKTybHcK1HxHahxKCRUGjzVYKY65W4Zrpzws5CeNDRKh5db6ZfY4k0RHQIDAQAB",
                "RSA2");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
//异步接收地址，仅支持http/https，公网可访问
        request.setNotifyUrl("http://3kp88bn44724.vicp.fun/api/alipay/notify");
//同步跳转地址，仅支持http/https
        String url= "http://localhost:8082/payback/?orderId="+aliPay.getTraceNo();
        request.setReturnUrl(url);
/******必传参数******/
        JSONObject bizContent = new JSONObject();
//商户订单号，商家自定义，保持唯一性
        bizContent.put("out_trade_no", aliPay.getTraceNo());
//支付金额，最小值0.01元
        bizContent.put("total_amount", aliPay.getTotalAmount());
//订单标题，不可使用特殊符号
        bizContent.put("subject", aliPay.getSubject());
//电脑网站支付场景固定传值FAST_INSTANT_TRADE_PAY
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");

/******可选参数******/
//bizContent.put("time_expire", "2022-08-01 22:00:00");

//// 商品明细信息，按需传入
//JSONArray goodsDetail = new JSONArray();
//JSONObject goods1 = new JSONObject();
//goods1.put("goods_id", "goodsNo1");
//goods1.put("goods_name", "子商品1");
//goods1.put("quantity", 1);
//goods1.put("price", 0.01);
//goodsDetail.add(goods1);
//bizContent.put("goods_detail", goodsDetail);

//// 扩展信息，按需传入
//JSONObject extendParams = new JSONObject();
//extendParams.put("sys_service_provider_id", "2088511833207846");
//bizContent.put("extend_params", extendParams);

        request.setBizContent(bizContent.toString());
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
// 如果需要返回GET请求，请使用
// AlipayTradePagePayResponse response = alipayClient.pageExecute(request,"GET");
        String pageRedirectionData = response.getBody();
        System.out.println(pageRedirectionData);

        if (response.isSuccess()) {
            System.out.println(response.getBody());
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        String form = "";
        try {
            form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }

    @PostMapping("/notify")  // 注意这里必须是POST接口
    public String payNotify(HttpServletRequest request) throws Exception {
        if (request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            System.out.println("=========支付宝异步回调========");

            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (String name : requestParams.keySet()) {
                params.put(name, request.getParameter(name));
            }

            String tradeNo = params.get("out_trade_no");
            String gmtPayment = params.get("gmt_payment");
            String alipayTradeNo = params.get("trade_no");

            //更新订单信息
            LambdaUpdateWrapper<GoodsOrder> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(GoodsOrder::getOrderPay,"1")
                    .eq(GoodsOrder::getOrderId,tradeNo);
            goodsOrderService.update(updateWrapper);


            // 支付宝验签
            if (Factory.Payment.Common().verifyNotify(params)) {
                // 验签通过
                System.out.println("交易名称: " + params.get("subject"));
                System.out.println("交易状态: " + params.get("trade_status"));
                System.out.println("支付宝交易凭证号: " + params.get("trade_no"));
                System.out.println("商户订单号: " + params.get("out_trade_no"));
                System.out.println("交易金额: " + params.get("total_amount"));
                System.out.println("买家在支付宝唯一id: " + params.get("buyer_id"));
                System.out.println("买家付款时间: " + params.get("gmt_payment"));
                System.out.println("买家付款金额: " + params.get("buyer_pay_amount"));


            }
            return "success";
        }
        return "success";
    }

}