import http from '@/http'
import type { WxUser, WxUserListParm } from './WxUserModel'

//查询列表
export let getListApi =(parm:WxUserListParm)=>{
    return http.get("/api/wxUser/getlist",parm)

}

//编辑
export let WxUserEditApi=(parm:WxUser)=>{
    return http.put("/api/wxUser/edit",parm)
}

//重置密码
export let restePasswordApi=(parm:WxUser)=>{
    return http.post("/api/wxUser/restepassword",parm)
}

//删除
export let deleteWxUserApi=(parm:WxUser)=>{
    return http.delete(`/api/wxUser/${parm.userId}`)
}

//获取验证码
export let sendMsg=(parm:WxUser)=>{
    return http.post("/api/wxUser/sendMsg",parm)
}

//登录
export let loginApi=(parm:WxUser)=>{
    return http.post("/api/wxUser/login",parm)
}

//注册
export let registerApi=(parm:WxUser)=>{
    return http.post("/api/wxUser/register",parm)
}
//忘记密码
export let forgotPasswordApi=(parm:WxUser)=>{
    return http.post("/api/wxUser/forgotPasword",parm)
}

//根据id查询
export let getInfoApi=(userId:any)=>{
    return http.get("/api/wxUser/getInfo",userId)
}

//修改密码
export let updatePaswordApi=(parm:any)=>{
    return http.post("/api/wxUser/updatePasword",parm)
}