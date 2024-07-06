import http from "@/http"
import type { ListParm, ReportParm, report } from "./ReportModel"


export let getReportApi=(parm:ListParm)=>{
    return http.get("/api/report/getList",parm)
}

//投诉处理
export let doReportApi=(reportId:string)=>{
    return http.post("/api/report/doReport",{reportId:reportId})
}

//删除
export let deleteReportApi=(reportId:string)=>{
    return http.delete(`/api/report/${reportId}`)
}

//举报商品
export let reportApi=(parm:report)=>{
    return http.post("/api/report/report",parm)
}

//获取我的举报
export let getMyReportApi=(parm:ReportParm)=>{
    return http.get("/api/report/getMyReport",parm)
}

//取消举报
export let cancelReportApi=(parm:ReportParm)=>{
    return http.post("/api/report/cancelReport",parm)
}

//修改举报内容
export let editReportApi=(parm:ReportParm)=>{
    return http.post("/api/report/editReport",parm)
}

