import http from "@/http";
import {type Help} from './helpModel'

//新增
export let addGoodsHelpApi=(parm:Help)=>{
    return http.post("/api/help/release",parm)
}

//查询全部
export let getGoodsHelpApi=()=>{
    return http.get("/api/help/getHelp")
}

//根据id查询
export let getGoodsHelpByIdApi=(id:any)=>{
    return http.get("/api/help/getHelpById",id)
}

//我的求购信息
export let getMyHelpApi=(userId:any)=>{
    return http.get("/api/help/getMyHelp",userId)
}
//删除我的求购信息
export let deleteMyHelpApi=(orderId:any)=>{
    return http.get("/api/help/deleteMyHelp",orderId)
}

//编辑求购信息
export let editApi=(parm:Help)=>{
    return http.post("/api/help/Myedit",parm)
}
//根据标题模糊搜索
export let searchHelpApi=(title:any)=>{
    return http.get("/api/help/searchHelp",title)
}
