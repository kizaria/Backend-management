import http from "@/http"
import type { ListParm, Order } from "./orderModel"

//订单列表
export let getOrderListApi=(parm:ListParm)=>{
    return http.get("/api/goodsOrder/getList",parm)
}
//编辑
export let editOrderApi=(parm:Order)=>{
    return http.post("/api/goodsOrder/edit",parm)
}
//删除
export let deleteOrderApi=(parm:Order)=>{
    return http.post("/api/goodsOrder/delete",parm)
}

//订单交易
export let replaceOrderApi=(parm:Order)=>{
    return http.post("/api/goodsOrder/replaceOrder",parm)
}

//根据id查询订单
export let getOrderByIdApi=(orderId:any)=>{
    return http.get("/api/goodsOrder/getOrderById",orderId)
}

//闲置订单
export let getSellOrderApi=(parm:any)=>{
    return http.get("/api/goodsOrder/getSellOrder",parm)
}

//我的订单
export let getMyOrderApi=(parm:any)=>{
    return http.get("/api/goodsOrder/getMyOrder",parm)
}

//确认收货
export let confirmApi=(parm:any)=>{
    return http.post("/api/goodsOrder/confirm",parm)
}

//确认发货
export let confirmReceivingApi=(parm:any)=>{
    return http.post("/api/goodsOrder/confirmReceiving",parm)
}