import http from '@/http'
import type { LogParm } from './LogModel'


//日志列表
export let getLogListApi=(parm:LogParm)=>{
    return http.get("/api/log/getLog",parm)
}
//删除
export let deleteLogApi=(logId:string)=>{
    return http.delete(`/api/log/${logId}`)
}