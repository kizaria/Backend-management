import http from "@/http"
import type { AssignParm, Menu, SaveAssignParm } from "./MenuModel"

//获取上级菜单树数据
export let getParentApi=()=>{
    return http.get("/api/menu/getParent")
}

//新增
export let addMenuApi=(parm:Menu)=>{
    return http.post("/api/menu",parm)
}

//列表
export let listMenuApi=()=>{
    return http.get("/api/menu/list")
}

//编辑
export let editMenuApi=(parm:Menu)=>{
    return http.put("/api/menu",parm)
}

//删除
export let deleteMenuApi=(parm:Menu)=>{
    return http.delete(`/api/menu/${parm.menuId}`)
}

//获取树数据
export let getAssignTreeApi=(parm:AssignParm)=>{
    return http.get("/api/menu/getAssignTree",parm)
}

//保存分配的菜单
export let assignSaveApi=(parm:SaveAssignParm)=>{
    return http.post("/api/menu/assignSave",parm)
}