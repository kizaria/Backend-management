import http from "@/http";
import {type Comment} from './commentModel'

//新增
export let addCommentApi=(parm:Comment)=>{
    return http.post("/api/comment/save",parm)
}

//查询全部
export let getCommentApi=(id:any)=>{
    return http.get("/api/comment/getComment",id)
}

