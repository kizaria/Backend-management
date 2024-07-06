import http from "@/http";
import {type LoginParm, type UpdatePasswordParm, type User} from './UserModel'
import {type ListParm} from './UserModel'

//新增
export let addAdminUserApi=(parm:User)=>{
    return http.post("/api/sysUser",parm)
}


//列表
export let getListApi=(parm:ListParm)=>{
    return http.get("/api/sysUser/getlist",parm)
}

//编辑
export let editApi=(parm:User)=>{
    return http.put("/api/sysUser",parm)
}

//重置密码
export let restePasswordApi=(parm:User)=>{
    return http.post("/api/sysUser/restepassword",parm)
}

//删除
export let deleteApi=(userId:string)=>{
    return http.delete(`/api/sysUser/${userId}`)
}

//验证码
export let getCodeApi=()=>{
    return http.post("/api/sysUser/image")
}

//登录
export let LoginApi=(parm:LoginParm)=>{
    return http.post("/api/sysUser/login",parm)
}

//修改密码
export let UpdatePasswordApi=(parm:UpdatePasswordParm)=>{
    return http.put("/api/sysUser/updatePassword",parm)
}
