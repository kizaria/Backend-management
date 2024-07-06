import http from "@/http";


//支付
export let payApi=()=>{
    return http.get("/api/alipay/pay")
}