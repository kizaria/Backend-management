import http from '@/http'
import type { friend } from './friendModel'


//查询好友
export let friendApi=(userId:any)=>{
    return http.get("/api/friend/get",userId)
}

//添加好友
export let friendSaveApi=(parm:friend)=>{
    return http.post("/api/friend/save",parm)
}

//查询好友未读消息
export let readApi=(userId:any)=>{
    return http.get("/api/friend/read",userId)
}