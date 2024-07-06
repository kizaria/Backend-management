import http from "@/http"

//订单列表
export let getNoticeApi=(userId:any)=>{
    return http.get("/api/notice/getNotice",userId)
}


//通知
export let getReadApi=(userId:any)=>{
    return http.get("/api/notice/getRead",userId)
}